package com.xybase.ax.eai.archcomp.message.bridge;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class MessageBridge<T> {

	private final static Logger log = LogManager.getLogger(MessageBridge.class);

	private String bridge;

	private static Map<String, MessageChannel> channels = null;

	public MessageBridge() {
	}

	public MessageBridge(String bridge) {
		this.bridge = bridge;
	}

	@SuppressWarnings("static-access")
	public void setChannels(Map<String, MessageChannel> channels) {
		if (channels != null) {
			this.channels = channels;
			log.info("Registred Channels: " + channels.keySet().toString());
		}
	}

	public void bridge(Message<T> message) {
		channels.get(bridge).send(message);
	}

}
