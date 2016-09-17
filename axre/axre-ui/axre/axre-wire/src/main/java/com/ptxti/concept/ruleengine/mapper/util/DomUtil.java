package com.ptxti.concept.ruleengine.mapper.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;

public class DomUtil {

	public static Document documentBuilder(String xml) {
		Document document = null;
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xml));
			document = documentBuilder.parse(inputSource);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new ApplicationRuntimeException("", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ApplicationRuntimeException("", e);
		}

		return document;
	}
	
	public static String xmlBuilder(DOMSource domSource){
		String xmlSource;
		try {
			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			transformer.transform(domSource, streamResult);
			xmlSource = streamResult.getWriter().toString();
			stringWriter.flush();
		} catch (TransformerConfigurationException e) {
			throw new ApplicationRuntimeException("", e);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			throw new ApplicationRuntimeException("", e);
		}
		return xmlSource;
	}
	
	public static String xmlBuilder(Source domSource){
		String xmlSource;
		try {
			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			transformer.transform(domSource, streamResult);
			xmlSource = streamResult.getWriter().toString();
			stringWriter.flush();
		} catch (TransformerConfigurationException e) {
			throw new ApplicationRuntimeException("", e);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			throw new ApplicationRuntimeException("", e);
		}
		return xmlSource;
	}
	
}
