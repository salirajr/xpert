/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 30, 2015	9:30:36 AM			Jovi Rengga Salira		Initial Creation
 * Oct 10, 2015	13:30:36 AM			Jovi Rengga Salira		The name should be refactored!!
 * **************************************************************************************
 * 
 */
package com.xybase.ax.eai.archcomp.xmb.receiver;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.dao.SoleRetrieval;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.common.util.springframework.ApplicationContextAwareImpl;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.receiver.Receiver;
import com.xybase.ax.eai.archcomp.xmb.common.UUIDGenerator;
import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.XMBTextMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;

/**
 * @note
 *
 */
public class XmbEjbIReceiver implements SessionBean, Receiver<XMBTextMessage> {

	private final String beanName = "EjbXmbSubDataPool";
	private final static Logger log = LogManager
			.getLogger(XmbEjbIReceiver.class);

	private SessionContext sessionContext;
	private MessageChannel defaultChannelOut;
	private MessageChannel defaultChannelLog;
	private SoleRetrieval xeaiSequence;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8077349965903634922L;

	public XmbEjbIReceiver() {
		// super();
		// TODO Auto-generated constructor stub
		this.defaultChannelOut = ApplicationContextAwareImpl
				.getApplicationContext().getBean("xmbChannelOut",
						MessageChannel.class);
		this.defaultChannelLog = ApplicationContextAwareImpl
				.getApplicationContext().getBean("xmbChannelLog",
						MessageChannel.class);
		this.xeaiSequence = ApplicationContextAwareImpl.getApplicationContext()
				.getBean("xeaiSequence", SoleRetrieval.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	@Override
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.info(beanName + " Activated!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.info(beanName + " Passivated!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	@Override
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.info(beanName + " Removed!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	@Override
	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		this.sessionContext = arg0;
	}

	public SessionContext getSessionContext() throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		return this.sessionContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.receiver.IReceiver#invoke(java.lang.Object)
	 */
	@Override
	public XMBTextMessage invoke(XMBTextMessage payload) {

		payload.setRefId(UUIDGenerator.generate());
		
		Map<String, Object> header = new HashMap<String, Object>();
		header.put(MessageConstants.Timestamp, DateUtil.getTimestamp());
		header.put(MessageConstants.IntegrationID, xeaiSequence.retrieve());
		header.put(MessageConstants.Headers.XeaiEventID, payload.getMsgCode());
		header.put(MessageConstants.UniqueKey, payload.getRefId());
		GenericMessage<XMBTextMessage> messageOut = new GenericMessage<XMBTextMessage>(
				payload, header);

		try {
			this.defaultChannelOut.send(messageOut);
		} catch (Exception e) {
			payload.setRefId(StringUtil.emptyChar);
			header.put(MessageConstants.Serverity, e.getCause().toString());
		} finally {
			header.put(MessageConstants.Endpoint, "3.2.2.XEAI.1|" + beanName);
			messageOut = new GenericMessage<XMBTextMessage>(payload, header);
			defaultChannelLog.send(messageOut);
		}
		return messageOut.getPayload();
	}

	public String dispatch(XMBMessage requestIn)
			throws InvalidRequestException, ExecutionFailedException {
		XMBTextMessage responseOut = (XMBTextMessage) invoke((XMBTextMessage) requestIn);
		return responseOut.getRefId();
	}

	public void ping() {
		log.info(beanName + " Pinged!");
	}

	public String getBeanName() {
		return this.beanName;
	}

}
