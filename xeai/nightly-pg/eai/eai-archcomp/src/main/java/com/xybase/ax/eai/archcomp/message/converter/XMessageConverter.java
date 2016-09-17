/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 14, 2015	11:51:54 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.message.XMessage;

/**
 * @param <T>
 * @note
 *
 */
public class XMessageConverter<T> implements MessageConverter {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XMessage fromMessage(Message<?> messageIn, Class<?> targetClass) {
		// TODO Auto-generated method stub
		XMessage result = (XMessage) messageIn.getPayload();
		if (messageIn.getHeaders().containsKey(
				MessageConstants.Headers.XeaiVariables)
				&& !StringUtil.isNullOrBlank(messageIn.getHeaders().get(
						MessageConstants.Headers.XeaiVariables))) {
			String temp = StringUtil.cast(messageIn.getHeaders().get(
					MessageConstants.Headers.XeaiVariables));
			result.setVariables(StringUtil.toMap(temp));
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Message<XMessage> toMessage(Object payload, MessageHeaders header) {
		// TODO Auto-generated method stub
		XMessage result = (XMessage) payload;
		Map<String, Object> XeaiVars = new HashMap<String, Object>();
		if (result.getVariables() != null)
			XeaiVars.put(MessageConstants.Headers.XeaiVariables,
					StringUtil.toString(result.getVariables()));

		return new GenericMessage<XMessage>(result, XeaiVars);
	}

}
