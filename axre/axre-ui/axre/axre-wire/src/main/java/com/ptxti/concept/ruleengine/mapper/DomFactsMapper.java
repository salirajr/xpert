package com.ptxti.concept.ruleengine.mapper;

import java.util.HashMap;
import java.util.Set;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import com.concept.ruleengine.model.EphemeralFact;
import com.ptxti.concept.ruleengine.mapper.util.DOMFactory;
import com.ptxti.concept.ruleengine.mapper.util.DomUtil;
import com.ptxti.concept.ruleengine.mapper.util.XmlUtil;

public class DomFactsMapper implements FactsMapper {

	private static Logger logger = LogManager.getLogger(DomFactsMapper.class);

	// set as Key, Source-path!!
	private HashMap<String, String> facts;
	// set as Key, Source-path!!
	private HashMap<String, String> resultMap;

	private JXPathContext context;
	private Document document;

	private String template;

	public EphemeralFact map(DOMSource request) {

		EphemeralFact result = new EphemeralFact();
		this.context = (JXPathContext) JXPathContext.newContext(DomUtil.documentBuilder(DomUtil.xmlBuilder(request)));
		Set<String> factsKey = facts.keySet();
		String factValue;
		for (String factKey : factsKey) {
			factValue = toString(this.context.getValue(facts.get(factKey)));
			result.put(factKey, factValue);
		}
		return result;
	}

	public String toString(Object object) {
		String result = null;
		return object == null ? result : object.toString();
	}

	public void setFacts(HashMap<String, String> facts) {
		this.facts = facts;
	}

	public void setResultMap(HashMap<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public void setContext(JXPathContext context) {
		this.context = context;
	}

	@Override
	public Source remap(EphemeralFact ephemeralFacts) {
		document = XmlUtil.documentBuilder(template);
		context = (JXPathContext) JXPathContext.newContext(document);
		context.setFactory(new DOMFactory());

		logger.info("Facts: " + ephemeralFacts);

		Set<String> keySetResultMap = resultMap.keySet();
		boolean previousLenient = context.isLenient();
		context.setLenient(false);
		for (String key : keySetResultMap) {
			try {
				try {
					context.getValue(resultMap.get(key));
				} catch (JXPathException jex) {
					context.createPath(resultMap.get(key));
				}
				context.setValue(resultMap.get(key), ephemeralFacts.get(key));
				logger.info(resultMap.get(key) + ":" + ephemeralFacts.get(key));
			} finally {
				context.setLenient(previousLenient);
			}

		}
		context.setLenient(previousLenient);
		logger.info("\n"+DomUtil.xmlBuilder(new DOMSource(document)));
		return new DOMSource(document);
	}

	public void setTemplate(String template) {
		this.template = "<" + template + "/>";
	}

}
