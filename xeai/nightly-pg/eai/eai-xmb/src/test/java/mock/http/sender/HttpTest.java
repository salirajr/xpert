/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Mar 10, 2016		2:50:17 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package mock.http.sender;

import mock.Util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author salirajr
 *
 */
public class HttpTest {
	private static ApplicationContext context;

	public static void main(String[] args) {

		context = new ClassPathXmlApplicationContext(
				new String[] { "mock/http/sender/contextTest.xml" });

		String path = "/home/salirajr/Projects/Axpert/XEAI/nightly-pg/workspace/eai/eai-xmb/src/test/java/mock/http/sender/AODBAFSADHOC.xml";
		MessageChannel channel = context.getBean("defaultChannelIn",
				MessageChannel.class);

		channel.send(new GenericMessage<String>(Util.read(path)));

		PollableChannel channelOut = context.getBean("defaultChannelOut",
				PollableChannel.class);

		System.out.println(channelOut.receive());
	}
}
