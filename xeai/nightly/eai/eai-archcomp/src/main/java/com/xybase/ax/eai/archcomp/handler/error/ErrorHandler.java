/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 20, 2015	8:12:17 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.handler.error;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

/**
 * @note
 *
 */
public interface ErrorHandler {

	public void audit(Message<MessagingException> messageIn);

	public void setChannelOut(MessageChannel out);

	public void setServerity(String serverity);
}
