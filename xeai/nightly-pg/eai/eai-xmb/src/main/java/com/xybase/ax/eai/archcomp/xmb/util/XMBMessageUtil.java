/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 27, 2015	7:27:58 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.xmb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.xybase.ax.eai.archcomp.message.converter.util.Converter;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;
import com.xybase.ax.eai.archcomp.transformer.extract.ObjectExtractor;
import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.XMBTextMessage;
import com.xybase.xmb.helper.XMBTimestamp;

/**
 * @note
 *
 */
public class XMBMessageUtil implements Converter<XMBMessage, Object> {

	public ObjectExtractor extractor;

	/**
	 * 
	 */
	public XMBMessageUtil() {
		// TODO Auto-generated constructor stub
		extractor = new ObjectExtractor();
	}

	public static String castString(XMBMessage message) {
		if (message instanceof XMBTextMessage) {
			return message.toString() + ","
					+ ((XMBTextMessage) message).getText();
		}
		return message.toString();
	}

	@SuppressWarnings("rawtypes")
	public static XMBTextMessage fromSoap(String soap) throws IOException,
			SOAPException {
		InputStream is = new ByteArrayInputStream(soap.getBytes());
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(
				null, is);
		SOAPBody soapBody = soapMessage.getSOAPBody();
		Iterator iterator = soapBody.getChildElements();
		SOAPBodyElement soapBodyElement = (SOAPBodyElement) iterator.next();
		iterator = soapBodyElement.getChildElements();
		soapBodyElement = (SOAPBodyElement) iterator.next();
		iterator = soapBodyElement.getChildElements();
		Map<String, String> map = new HashMap<String, String>();
		while (iterator.hasNext()) {
			soapBodyElement = (SOAPBodyElement) iterator.next();
			map.put(soapBodyElement.getLocalName(), soapBodyElement.getValue());
		}
		XMBTextMessage result = new XMBTextMessage();
		result.setMsgCode(map.get("msgCode"));
		result.setMsgVersion(Integer.valueOf(map.get("msgVersion")));
		result.setOrigSystem(map.get("origSystem"));
		result.setPriority(Integer.valueOf(map.get("priority")));
		result.setText(map.get("text"));
		result.setTimestamp(new XMBTimestamp(Long.valueOf(map.get("timestamp"))));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.Converter#toContext
	 * (java.lang.Object)
	 */
	@Override
	public XMBMessage toContext(Object payload) {
		// TODO Auto-generated method stub
		if (payload instanceof XMBTextMessage) {
			return (XMBTextMessage) payload;
		} else {
			return (XMBMessage) payload;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.Converter#toString(
	 * java.lang.Object)
	 */
	@Override
	public String toString(XMBMessage payload) {
		// TODO Auto-generated method stub
		return castString(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.Converter#express(java
	 * .lang.Object, java.lang.String)
	 */
	@Override
	public Object express(Object payload, String expression) {
		// TODO Auto-generated method stub
		Extractor<Object> etr = extractor.clones();
		etr.setContext(payload);
		return etr.extract(expression);
	}

	public static String toSoap(XMBMessage msg) {
		String result = new String();
		Date date = new Date();
		try {

			if (!(msg instanceof XMBTextMessage)) {
				return result;
			}

			XMBTextMessage message = (XMBTextMessage) msg;
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapMsg = factory.createMessage();
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			SOAPBody body = envelope.getBody();
			SOAPBodyElement element = body.addBodyElement(envelope
					.createName("dispatch"));
			SOAPElement messageElement = element.addChildElement("message");
			if (message.getMsgCode() == null) {
				messageElement.addChildElement("msgCode");
			} else {
				messageElement.addChildElement("msgCode").addTextNode(
						message.getMsgCode());
			}
			messageElement.addChildElement("msgVersion").addTextNode(
					Integer.toString(message.getMsgVersion()));
			if (message.getOrigSystem() == null) {
				messageElement.addChildElement("origSystem");
			} else {
				messageElement.addChildElement("origSystem").addTextNode(
						message.getOrigSystem());
			}
			messageElement.addChildElement("priority").addTextNode(
					Integer.toString(message.getPriority()));
			if (message.getText() == null) {
				messageElement.addChildElement("text");
			} else {
				messageElement.addChildElement("text").addTextNode(
						message.getText());
			}
			if (message.getTimestamp() == null) {

				long timeMilli = date.getTime();
				messageElement.addChildElement("timestamp").addTextNode(
						Long.toString(timeMilli));
			} else {
				long getTime = message.getTimestamp().getTime();
				messageElement.addChildElement("timestamp").addTextNode(
						Long.toString(getTime));
			}
			if (message.getRefId() == null) {
				messageElement.addChildElement("refId");
			} else {
				messageElement.addChildElement("refId").addTextNode(
						message.getRefId());
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapMsg.writeTo(out);
			result = new String(out.toByteArray());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
