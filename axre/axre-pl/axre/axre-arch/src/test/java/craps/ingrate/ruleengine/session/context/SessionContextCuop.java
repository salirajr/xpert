package craps.ingrate.ruleengine.session.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextCuop {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] { "sessionContext-locals.xml",
				SessionContext.base + "/assets/session/sessionContext-cuop.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("cuopSession", KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("counter-operation");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);

		EphemeralFact ef = new EphemeralFact();
		ef.put("in0", "2015-10-26T15:55:00.000Z");
		ef.put("in1", "100003559"); // _rmsKey
		ef.put("in2", "A"); // _type

		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);
		
		System.out.println("return: " + emTrials.getFacts().get("return"));
		System.out.println(emTrials);

	}
}
