/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	7:15:47 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.converter.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.xybase.ax.eai.archcomp.constant.ErrorConstants;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;

/**
 * @note
 *
 */
public class XmlConverter implements Converter<Document, Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.integration.xml.XmlPayloadConverter#convertToDocument
	 * (java.lang.Object)
	 */
	@Override
	public Document toContext(Object arg0) {
		String input = arg0.toString();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(input)));

			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(
					"Invalid xml format source", e,
					ErrorConstants.Code.MESSAGEHANDLER_EXCEPTION);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(e);
		} catch (NullPointerException e) {
			throw new InternalErrorRuntimeException(
					"Null source parameterized !!", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.integration.xml.XmlPayloadConverter#convertToNode
	 * (java.lang.Object)
	 */
	public Node toNode(String arg0) {

		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(arg0)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(
					"Invalid xml format source", e,
					ErrorConstants.Code.MESSAGEHANDLER_EXCEPTION);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new InternalErrorRuntimeException(e);
		} catch (NullPointerException e) {
			throw new InternalErrorRuntimeException(
					"Null source parameterized !!", e);
		}

	}

	public String toString(Document document) {
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			return sw.toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object express(Object payload, String expression) {
		// TODO Auto-generated method stub
		return null;
		
	}

}
