/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 2, 2015	 4:24:25 PM			Jovi Rengga Salira		Initial Creation
 * Nov 5, 2015	10:44:25 AM			Abdul Azis Nur			Change Reconect EJB to target system
 * 			- Message In -> ping()	: identify if the proxy session was attached.
 * 				-> Success	-> dispatch()
 * 				-> Failed* 	-> Kill the listener
 * 							-> re-create
 * 								-> Success 	-> Send Message
 * 											-> Start the listener
 * 								-> Failed : Iterate this till the assigned tryOut,
 * 											return to step [*]  
 * 									
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.xmb.sender;

import java.rmi.RemoteException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.sasl.JBossSaslProvider;
import org.joda.time.DateTime;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;
import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.context.lookup.javax.ContextLookup;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.lookup.Lookup;
import com.xybase.ax.eai.archcomp.lookup.util.LookupUtil;
import com.xybase.ax.eai.archcomp.sender.ISender;
import com.xybase.ax.eai.archcomp.transformer.extract.ObjectExtractor;
import com.xybase.ax.eai.archcomp.xmb.constant.ErrorCodeXmb;
import com.xybase.ax.eai.archcomp.xmb.constant.XMBConstants;
import com.xybase.ax.eai.archcomp.xmb.exception.InternalErrorRuntimeXmbException;
import com.xybase.ax.eai.archcomp.xmb.util.XMBMessageUtil;
import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;
import com.xybase.xmb.xss.XMBSystemServer;
import com.xybase.xmb.xss.XMBSystemServerHome;

/**
 * @note
 *
 */
public class XmbEjbISender implements ISender<GenericMessage<XMBMessage>>,
		CrowbarBus, LevergearBus {

	private final static Logger logs = LogManager
			.getLogger(XmbEjbISender.class);

	private XMBSystemServerHome home;
	private XMBSystemServer bean;

	private ContextLookup contextLookup;
	private Lookup lookup;
	private String ejbHomeAddressKey = "ejb.home.address";

	private AuditLogDao auditDao;
	// Independent Crow-bar
	private AuditLogConfig auditConfig;
	private ObjectExtractor extractors;

	private boolean isReady = false;
	private boolean isActivate = true;

	private DateTime lastActivity;

	private String auditEndpoint = "#var.ejb.home.address+'XmbEjbASender'";

	// add by azis
	private int tryOut = 5;

	private int tryTimeout = 2000;

	private HashMap<String, String> failedOvers;

	// stop the JMSListener
	private MessageChannel controlOut;

	/**
	 * @param controlOut
	 *            the controlOut to set
	 */
	public void setControlOut(MessageChannel controlOut) {
		this.controlOut = controlOut;
	}

	/**
	 * @param loop
	 *            the loop to set
	 */
	@ManagedOperation
	public void setTryOut(int loop) {
		if (loop > 0)
			this.tryOut = loop;
	}

	public void setTryTimeout(int tryTimeout) {
		this.tryTimeout = tryTimeout;
	}

	static {
		Security.addProvider(new JBossSaslProvider());
	}

	public XmbEjbISender(Lookup lookup) {
		logs.info("XmbEjbISender initated lookup: "
				+ StringUtil.toString(lookup));

		this.lookup = lookup;
		extractors = new ObjectExtractor();
		extractors.setVariables(this.lookup);

		try {
			creates(Integer.MIN_VALUE);
		} catch (Exception e) {
			logs.error("XmbEjbISender : " + StringUtil.toString(lookup)
					+ " failed to initiate");
		}

	}

	private boolean preprocessor() {
		String ejbHomeAddress = lookup.get(ejbHomeAddressKey);
		try {
			contextLookup = new ContextLookup(ejbHomeAddress,
					LookupUtil.toHashtable(lookup));
			logs.info("preprocessor(" + ejbHomeAddress + ") is : TRUE");
			return true;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logs.info("preprocessor(" + ejbHomeAddress
					+ ") is : ERROR NamingException ========================> "
					+ e.getMessage());
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logs.info("preprocessor(" + ejbHomeAddress
					+ ") is : ERROR Exception ========================> "
					+ e.getMessage());
			return false;
		}

	}

	/**
	 * @throws NamingException
	 * @throws CreateException
	 * @throws RemoteException
	 * 
	 */
	private boolean initiate() {
		Object rawsLookup = null;
		try {
			rawsLookup = this.contextLookup.lookup();
			logs.info("initiate ===================================> "
					+ rawsLookup);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logs.info("initiate Error NamingException ===================================> "
					+ e.getMessage());
			return false;
		}

		try {
			home = (XMBSystemServerHome) rawsLookup;
			bean = home.create();
			logs.info("initiate(" + lookup.get(ejbHomeAddressKey) + ") Success");
			return true;
		} catch (RemoteException | CreateException e) {
			// TODO Auto-generated catch block
			logs.info("initiate Error (RemoteException | CreateException ===================================> "
					+ e.getMessage());
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logs.info("initiate Error (Exception ===================================> "
					+ e.getMessage());
			return false;
		}

	}

	private void creates(int i) {
		if (bean == null || lastActivity == null
				|| DateUtil.getMinuteGap(lastActivity) >= 5) {

			try {
				logs.info("Enter Recreate XmbEJBIsender['"
						+ lookup.get(ejbHomeAddressKey) + "']");
				isReady = preprocessor() ? initiate() : false;
				logs.info("Recreate XmbEJBIsender['"
						+ lookup.get(ejbHomeAddressKey) + "'] Success");
			} catch (Exception e) {
				// TODO Auto-generated catch block

				logs.error("Recreate XmbEJBIsender['"
						+ lookup.get(ejbHomeAddressKey)
						+ "'] Error cause ===========> ");
				// e.printStackTrace();
				if (i != Integer.MIN_VALUE) {
					try {
						logs.info("Recreate XmbEJBIsender['"
								+ lookup.get(ejbHomeAddressKey) + "'] in "
								+ (i * tryTimeout) + "ms");
						Thread.sleep(i * tryTimeout);
					} catch (InterruptedException ie) {
						// TODO Auto-generated catch block
						logs.error(ie.getMessage());
					}
				}
				throw new InternalErrorRuntimeXmbException(
						"Invalid Lookup Definition!!, check your CDI, Make sure the system is UP and READY!!",
						e, ErrorCodeXmb.FATAL_ERROR);
			}
		}
	}

	@Override
	public void dispatch(GenericMessage<XMBMessage> payload) {

		int iTryOut = 0;
		extractors.setContext(payload);
		AuditLog log = new AuditLog(payload);
		log.setPayload(XMBMessageUtil.castString(payload.getPayload()));
		try {
			log.setCorrelationId(StringUtil.isNullOrBlank(auditConfig
					.getCorrelation()) ? null : StringUtil.cast(extractors
					.extract(auditConfig.getCorrelation())));
		} catch (Exception e) {
			log.setCorrelationId("Failed set correlation-id on Ex:"
					+ e.getMessage());
		}
		try {
			log.setAuditParam(StringUtil.isNullOrBlank(auditConfig
					.getParameterized()) ? null : StringUtil.cast(extractors
					.extract(auditConfig.getParameterized())));
		} catch (Exception e) {
			log.setAuditParam("Failed set audit-param on Ex:" + e.getMessage());
		}
		log.setAuditType(auditConfig.getType());
		log.setEndpoint((String) extractors.extract(auditEndpoint));

		if (!isActivate) {
			logs.info(InternalConstant.XEAI_SERVERITY_CONTROLLEDBYPASS
					+ (String) extractors.extract(auditEndpoint));
			if (auditConfig.getConfig() == 1) {
				log.setSeverity(InternalConstant.XEAI_SERVERITY_CONTROLLEDBYPASS);
				auditDao.audit(log);
			}
			return;

		}

		if (ping()) {
			try {
				if (isReady) {
					bean.dispatch(payload.getPayload());
					lastActivity = DateUtil.getNow();
					if (auditConfig.getConfig() == 1) {
						auditDao.audit(log);
					}
				} else
					throw new InternalErrorRuntimeXmbException(
							"Failure Creating Exception, Ejb-Dispatcher is enabled!!",
							new CreateException(), ErrorCodeXmb.FATAL_ERROR);

			} catch (InvalidRequestException | ExecutionFailedException
					| RemoteException e) {
				// TODO Auto-generated catch block

				throw new InternalErrorRuntimeXmbException(
						"Message sent with exception!!!, caused of internal execution failure!!",
						e, ErrorCodeXmb.PROCESSING_ERRROR);
			}
		} else {

			do {
				logs.info("Failed Over Tryout['"
						+ lookup.get(ejbHomeAddressKey) + "']@ " + iTryOut);
				try {
					try {
						bean.remove();
					} catch (Exception e) {
						// e.printStackTrace();
					}
					bean = null;
					creates(iTryOut);
					if (isReady) {
						bean.dispatch(payload.getPayload());
						lastActivity = DateUtil.getNow();
						if (auditConfig.getConfig() == 1) {
							auditDao.audit(log);
						}
						iTryOut = Integer.MAX_VALUE;

						controlOut.send(new GenericMessage<String>(
								this.failedOvers
										.get(XMBConstants.Sender.STARTJMS)));

						logs.info("START listener on: "
								+ lookup.get(ejbHomeAddressKey));

					} else {
						throw new InternalErrorRuntimeXmbException(
								"Failure Creating Exception, Ejb-Dispatcher is enabled!!",
								new CreateException(), ErrorCodeXmb.FATAL_ERROR);
					}

				} catch (Exception e) {
					iTryOut++;
					if (iTryOut >= this.tryOut)
						throw new InternalErrorRuntimeXmbException(
								"Try-out failed over is done!, contact your administrator, this may cause, Queue stop and message throws to error logs",
								e, ErrorCodeXmb.PROCESSING_ERRROR);
				}
			} while (iTryOut < this.tryOut);
		}
	}

	public boolean ping() {
		try {
			bean.ping();
			logs.info("Pinged to target System "
					+ lookup.get(ejbHomeAddressKey) + " acheived!");
			return true;
		} catch (Exception e) {

			controlOut.send(new GenericMessage<String>(this.failedOvers
					.get(XMBConstants.Sender.STOPJMS)));
			logs.info("STOP listener on:" + lookup.get(ejbHomeAddressKey));

			logs.error("Pinged Failed!! on ['" + lookup.get(ejbHomeAddressKey)
					+ "'], caused :" + e.getMessage());
			return false;
		}

	}

	public void setAuditDao(AuditLogDao auditDao) {
		this.auditDao = auditDao;
	}

	public void setAuditConfig(AuditLogConfig auditConfig) {
		this.auditConfig = auditConfig;
	}

	public AuditLogConfig getAuditConfig(AuditLogConfig auditConfig) {
		return this.auditConfig;
	}

	public void setAuditEndpoint(String auditEndpoint) {
		this.auditEndpoint = auditEndpoint;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return isReady;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		String state = CrowbarConstant.state.INITIALIZATION_SUCCEED;
		String code = CrowbarConstant.code.INITIALIZATION_SUCCEED;
		String message = this.getClass().getName() + " is refreshed!";
		this.destroy();
		lookup.reinitialized();
		extractors = new ObjectExtractor();
		extractors.setVariables(this.lookup);
		bean = null;
		try {
			creates(Integer.MIN_VALUE);
		} catch (Exception e) {
			state = CrowbarConstant.state.INITIALIZATION_FAILED;
			code = CrowbarConstant.code.INITIALIZATION_FAILED;
			message = e.getMessage();
		}

		return BusRspUtil.asResponse(code, state, message);
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATIONP,
				CrowbarConstant.state.CREINITIALIZATIONP,
				CrowbarConstant.message.CREINITIALIZATIONP);
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		try {
			this.contextLookup.close();
			logs.info("CLOSED contextLookup['" + lookup.get(ejbHomeAddressKey)
					+ "']");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logs.info("FAILED CLOSED contextLookup['"
					+ lookup.get(ejbHomeAddressKey) + "']");
		}
		this.contextLookup = null;
		this.home = null;
		try {
			this.bean.remove();
			logs.error("REMOVED contextLookup['"
					+ lookup.get(ejbHomeAddressKey) + "']");
		} catch (RemoteException | RemoveException e) {
			// TODO Auto-generated catch block
			logs.error("FAILED contextLookup['" + lookup.get(ejbHomeAddressKey)
					+ "']");
		}
		this.bean = null;
		return BusRspUtil.asResponse(CrowbarConstant.state.DESTROYED,
				CrowbarConstant.message.DESTROYED);
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREDESTROYINGP,
				CrowbarConstant.state.CREDESTROYINGP,
				CrowbarConstant.message.CREDESTROYINGP);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		Map<String, String> map = this.lookup;

		map.put("isReady", isReady ? "true" : "false");
		map.put("isActivate", isActivate ? "true" : "false");

		return BusRspUtil.asInfo(this.getClass().getName(),
				"Lookup Content Definition", StringUtil.toString(map));
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		isActivate = false;
		return BusRspUtil.asResponse(LevergearConstant.state.STOP,
				LevergearConstant.info.PASSES_STOP);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		isActivate = true;
		return BusRspUtil.asResponse(LevergearConstant.state.START,
				LevergearConstant.info.STARTED);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return isActivate;
	}

	/**
	 * @param failOverWorkAround
	 *            the failOverWorkAround to set
	 */
	public void setFailedOvers(HashMap<String, String> failOverWorkAround) {
		this.failedOvers = failOverWorkAround;
	}

}
