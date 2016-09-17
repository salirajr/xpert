/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 23, 2015	10:11:12 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.handler.error;

import java.util.Arrays;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import com.xybase.ax.eai.archcomp.common.audit.error.AuditErrorLog;
import com.xybase.ax.eai.archcomp.common.audit.error.dao.AuditErrorLogDao;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;

/**
 * @note
 *
 */
public class DefaultErrorHandler implements ErrorHandler {

	private String serverity;
	private AuditErrorLogDao dao;
	private MessageChannel channelOut;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.handler.error.IErrorHandler#handle(org.
	 * springframework.messaging.support.GenericMessage)
	 */

	public DefaultErrorHandler() {
		// TODO Auto-generated constructor stub
		this.channelOut = null;
		this.serverity = null;
	}

	@Override
	public void audit(Message<MessagingException> messageIn) {
		// TODO Auto-generated method stub
		MessagingException exception = messageIn.getPayload();

		AuditErrorLog log = new AuditErrorLog();
		StringBuilder sb = new StringBuilder();
		sb.append("[Most Specific Cause: " + exception.getMostSpecificCause())
				.append(", Root Cause: " + exception.getRootCause())
				.append(", Cause: " + exception.getCause())
				.append(", Localized Message: "
						+ exception.getLocalizedMessage())
				.append(", Stack Trace: "
						+ Arrays.toString(exception.getStackTrace()) + "]");

		log.setCause(sb.toString());
		log.setAuditTime(DateUtil.getTimestamp());
		log.setXeaiId(exception.getFailedMessage().getHeaders()
				.containsKey(MessageConstants.IntegrationID) ? StringUtil
				.cast(exception.getFailedMessage().getHeaders()
						.get(MessageConstants.IntegrationID)) : null);

		log.setEventName(exception.getFailedMessage().getHeaders()
				.containsKey(MessageConstants.Headers.XeaiEventID) ? StringUtil
				.cast(exception.getFailedMessage().getHeaders()
						.get(MessageConstants.Headers.XeaiEventID)) : null);

		log.setEndpoint(exception.getFailedMessage().getHeaders()
				.containsKey(MessageConstants.Endpoint) ? StringUtil
				.cast(exception.getFailedMessage().getHeaders()
						.get(MessageConstants.Endpoint)) : null);

		log.setErrorId(messageIn.getHeaders().containsKey(
				MessageConstants.ErrorID) ? StringUtil.cast(messageIn
				.getHeaders().get(MessageConstants.ErrorID)) : null);

		log.setSeverity(this.serverity);

		dao.logError(log);

		if (channelOut != null)
			channelOut.send(exception.getFailedMessage());
	}

	public void setAuditErrorLogDao(AuditErrorLogDao dao) {
		this.dao = dao;
	}

	@Override
	public void setChannelOut(MessageChannel out) {
		// TODO Auto-generated method stub
		this.channelOut = out;
	}

	@Override
	public void setServerity(String serverity) {
		// TODO Auto-generated method stub
		this.serverity = serverity;
	}

}
