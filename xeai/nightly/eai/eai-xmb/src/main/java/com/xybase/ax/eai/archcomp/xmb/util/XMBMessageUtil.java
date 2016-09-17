/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 27, 2015	7:27:58 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.xmb.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

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
}
