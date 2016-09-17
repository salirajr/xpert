package craps.ingrate.ruleengine.session.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextCupl {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] { "sessionContext-locals.xml",
				SessionContext.base + "/assets/session/sessionContext-cupl.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("cuplSession", KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("counter-planning");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);
		
		EphemeralFact ef = new EphemeralFact();
//		ef.put("in1", "2007-03-01T22:00:00.000Z"); // _eta 2007-03-01T22:00:00.000Z
//		ef.put("in2", "149345"); // _rmsKey
//		ef.put("in3", "A");
		
		ef.put("in0", "2015-10-27T00:00:00.000Z");
		ef.put("in1", "149345");
		ef.put("in2", "A");
		ef.put("in3", "S15_8");
		
		
		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);

		System.out.println("return: " + emTrials.getFacts().get("return"));
		System.out.println(emTrials);
	}
}
