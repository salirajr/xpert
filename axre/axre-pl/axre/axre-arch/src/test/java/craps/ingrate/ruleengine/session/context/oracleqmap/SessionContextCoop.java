package craps.ingrate.ruleengine.session.context.oracleqmap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextCoop {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(
				new String[] {
						"sessionContext-locals.xml",
						SessionContext.base
								+ "/assets/session/sessionContext-coop.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("coopSession",
				KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("carousel-operation");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);

		EphemeralFact ef = new EphemeralFact();
//		 ef.put("in0", "2015-11-1T25:41:00.000Z"); // _eta
//		ef.put("in1", "A1"); // _reclaimCode
		ef.put("in2", "C"); // _type
		ef.put("in3", "111743"); // _rmsKey
		emTrials.setFacts(ef);
		knowledgeSession.inject(emTrials);
		System.out.println(emTrials.facts.get("return") + "|" + emTrials);

	}
}
