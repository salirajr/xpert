package com.xybase.ax.eai.xybatch.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQStatistics {

	private String queueName, brokerURL, userName, password;

	public ActiveMQStatistics(String queueName, String brokerURL,
			String userName, String password) {
		// TODO Auto-generated constructor stub
		this.queueName = queueName;
		this.brokerURL = brokerURL;
		this.userName = userName;
		this.password = password;

	}

	public Map<String, String> getStatistics() throws JMSException {

		Connection connection = new ActiveMQConnectionFactory(brokerURL)
				.createConnection(userName, password);
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Queue replyQueue = session.createTemporaryQueue();
		MessageConsumer consumer = session.createConsumer(replyQueue);
		Queue requestQueue = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(null);

		Queue destinationQueue = session
				.createQueue("ActiveMQ.Statistics.Destination"
						+ requestQueue.getQueueName());
		Message message = session.createMessage();
		producer.send(requestQueue, message);
		message.setJMSReplyTo(replyQueue);
		producer.send(destinationQueue, message);
		MapMessage responseMessage = (MapMessage) consumer.receive();

		Map<String, String> result = new HashMap<String, String>();
		String name;
		for (@SuppressWarnings("rawtypes")
		Enumeration e = responseMessage.getMapNames(); e.hasMoreElements();) {
			name = e.nextElement().toString();
			result.put(name, String.valueOf(responseMessage.getObject(name)));
		}
		session.close();
		connection.close();
		return result;
	}

	
}
