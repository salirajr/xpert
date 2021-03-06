package com.ptxti.concept.ruleengine.wire;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;
import com.ptxti.concept.ruleengine.mapper.FactsMapper;

public class AxreWireService {
	private static Logger logger = LogManager.getLogger(AxreWireService.class);
	private FactsMapper factsMapper;

	private KnowledgeSession knowledgeSession;
	private String operationName;

	public Source injects(DOMSource request) {
		EphemeralFact facts = factsMapper.map(request);
		EphemeralMantle mantle = new EphemeralMantle(operationName);
		mantle.setFacts(facts);
		mantle.setPhase("0000000");
		logger.info("\n\nInjected: " + mantle);
		EphemeralMantle result = (EphemeralMantle) knowledgeSession.inject(mantle);
		Source source = factsMapper.remap(result.getFacts());
		logger.info("Ends: " + mantle+"\n\n");
		return source;

	}

	public void setFactsMapper(FactsMapper factsMapper) {
		this.factsMapper = factsMapper;
	}

	public void setKnowledgeSession(KnowledgeSession knowledgeSession) {
		this.knowledgeSession = knowledgeSession;
		this.knowledgeSession.createSession();
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
