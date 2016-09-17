/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 22, 2015	4:21:51 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.xmb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;

/**
 * @note
 *
 */
public class XeaiMBMessageServer implements SessionBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final String name = "XeaiMBMessageServer";
	private final static Logger log = LogManager
			.getLogger(XeaiMBMessageServer.class);

	private SessionContext mySessionCtx;

	public String getBeanName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setSessionContext(SessionContext ctx) {
		mySessionCtx = ctx;
	}

	public SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public void ejbCreate() throws CreateException {
		log.info("None to be initiated!");
	}

	public void ping() {
		log.info(name + " Pinged!");
	}

	public String dispatch(XMBMessage request) throws InvalidRequestException,
			ExecutionFailedException {

		if (request instanceof XMBMessage) {
			log.info("Message in, identification reference: "
					+ request.getRefId() + "[" + request.toString() + "]");
		} else {
			String errMsg = name
					+ " Only instances of XTBTextMessage are supported";
			throw new InvalidRequestException(errMsg);
		}

		return "EAIXBMSGSVR-" + String.valueOf(System.currentTimeMillis());
	}

	@Override
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}
}
