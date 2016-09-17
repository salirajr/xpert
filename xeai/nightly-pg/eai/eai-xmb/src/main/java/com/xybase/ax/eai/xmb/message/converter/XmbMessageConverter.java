/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 6, 2015	1:26:49 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xmb.message.converter;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.xmb.XMBTextMessage;

/**
 * @note
 *
 */
public class XmbMessageConverter implements Transformer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.integration.transformer.Transformer#transform(org
	 * .springframework.messaging.Message)
	 */
	@Override
	public Message<?> transform(Message<?> message) {
		return new GenericMessage<XMBTextMessage>(
				(XMBTextMessage) message.getPayload(), message.getHeaders());
	}

}
