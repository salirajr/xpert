package craps.ingrate.ruleengine.session.context.oracleqmap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextTrials {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] {
				"sessionContext-locals.xml",
				SessionContext.base
						+ "/assets/session/sessionContext-trials.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("trialsSession",
				KnowledgeSession.class);

		knowledgeSession.createSession();


		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("trials-operation");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);

		EphemeralFact ef = new EphemeralFact();
		ef.put("in0", "2015-06-07T10:10:00.000Z");
		ef.put("in1", "100002880"); // _rmsKey
		ef.put("in2", "C"); // _type

		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);

		// System.out.println("return: " + emTrials.getFacts().get("return"));
	}
}
