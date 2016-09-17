/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 22, 2015	4:21:51 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.xmb;

import javax.ejb.CreateException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;
import com.xybase.xmb.xss.XMBSystemServerBean;

/**
 * @note
 *
 */
public class XeaiMBSystemServer extends XMBSystemServerBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private final String name = "XeaiMBSystemServer";

	private final static Logger log = LogManager
			.getLogger(XeaiMBSystemServer.class);

	public void ejbCreate() throws CreateException {
		log.info("XeaiMBSystemServer None to be initiated!");
	}

	@Override
	public void dispatch(XMBMessage request) throws InvalidRequestException,
			ExecutionFailedException {

		if (request instanceof XMBMessage) {
			log.info("Message in, identification reference: "
					+ request.getRefId() + "[" + request.toString() + "]");
		} else {
			String errMsg = name
					+ " Only instances of XMBMessage are supported";
			throw new InvalidRequestException(errMsg);
		}

	}

	@Override
	public void ping() {
		log.info(name + " Pinged!");
	}

}
