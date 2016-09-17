package craps.ingrate.ruleengine.session.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextSdpl {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] { "sessionContext-locals.xml",
				SessionContext.base + "/assets/session/sessionContext-sdpl.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("sdplSession", KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("stand-planning");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);
		
		EphemeralFact ef = new EphemeralFact();
		 ef.put("in0", "A41"); // _standCode
		 ef.put("in1", "2015-10-12T00:00:00.000Z"); // _eta 2015-06-01T13:10:00.000Z
		// ef.put("in2", ""); // _etd
		ef.put("in3", "100006603");
		ef.put("in4", "C");
		ef.put("in5", "S15_a");
		
		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);

		System.out.println("return: " + emTrials.getFacts().get("return"));
		System.out.println(emTrials);
	}
}
