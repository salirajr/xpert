/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 31, 2015	11:06:29 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xmb.message.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.xmb.XMBTextMessage;

/**
 * @note
 *
 */
public class XmbTextMessageConverter implements MessageConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#fromMessage
	 * (javax.jms.Message)
	 */
	@Override
	public Object fromMessage(Message arg0) throws JMSException,
			MessageConversionException {
		// TODO Auto-generated method stub
		TextMessage textMessage = (TextMessage) arg0;
		XMBTextMessage xmbTextMessage = new XMBTextMessage();
		xmbTextMessage.setMsgCode(arg0
				.getStringProperty(MessageConstants.Headers.XeaiEventID));
		xmbTextMessage.setMsgVersion(arg0
				.getIntProperty(MessageConstants.Version));
		xmbTextMessage.setOrigSystem(arg0
				.getStringProperty(MessageConstants.OriginSystem));
		xmbTextMessage.setPriority(textMessage.getJMSPriority());
		xmbTextMessage.setRefId(textMessage.getJMSCorrelationID());
		xmbTextMessage.setText(textMessage.getText());
		xmbTextMessage.setTimestamp(xmbTextMessage.getTimestamp());
		return xmbTextMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#toMessage(
	 * java.lang.Object, javax.jms.Session)
	 */
	@Override
	public Message toMessage(Object arg0, Session arg1) throws JMSException,
			MessageConversionException {
		// TODO Auto-generated method stub
		XMBTextMessage xmbTextMessage = (XMBTextMessage) arg0;
		TextMessage textMessage = arg1.createTextMessage();
		textMessage.setJMSCorrelationID(xmbTextMessage.getRefId());
		// USELESS, MessageID will be overridden by AMQ after injected.
		// textMessage.setJMSMessageID(xmbTextMessage.getMsgCode());
		textMessage.setJMSType("text/plain");
		textMessage.setJMSPriority(xmbTextMessage.getPriority());
		textMessage.setText(xmbTextMessage.getText());
		return textMessage;
	}

}
