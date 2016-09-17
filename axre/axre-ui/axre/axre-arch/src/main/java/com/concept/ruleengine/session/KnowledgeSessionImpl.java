package com.concept.ruleengine.session;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.dt.model.DecisionTable;
import com.concept.ruleengine.reference.dao.ReferenceDAO;
import com.concept.ruleengine.reference.mapper.QueryMapper;

public class KnowledgeSessionImpl implements KnowledgeSession {

	private static Logger logger = LogManager.getLogger(KnowledgeSessionImpl.class);

	private ReferenceDAO reference;
	private QueryMapper mapper;
	private DecisionTable decisionTable;

	private KnowledgeBuilder knowledge;
	private KnowledgeBase base;
	private StatelessKnowledgeSession session;

	private List<String> lsDrools;

	public KnowledgeSessionImpl() {
		knowledge = KnowledgeBuilderFactory.newKnowledgeBuilder();
	}

	public void createSession() {
		try {

			for (String drool : lsDrools) {
				knowledge.add(ResourceFactory.newFileResource(ConstantComponent.BASEPTH + drool), ResourceType.DRL);
				logger.info("Add .drl: " + ConstantComponent.BASEPTH + drool);
			}
			base = knowledge.newKnowledgeBase();
			session = base.newStatelessKnowledgeSession();
			logger.info("Creat new Stateless Knowledge Session");

			session.setGlobal("reference", reference);
			session.setGlobal("mapper", mapper);
			session.setGlobal("decisionTable", decisionTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object inject(Object object) {
		session.execute(object);
		return object;
	}

	public void setReference(ReferenceDAO reference) {
		this.reference = reference;
	}

	public void setMapper(QueryMapper mapper) {
		this.mapper = mapper;
	}

	public void setSession(StatelessKnowledgeSession session) {
		this.session = session;
	}

	public void setLsDrools(List<String> lsDrools) {
		this.lsDrools = lsDrools;
	}

	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

}
