package craps.ingrate.ruleengine.session.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextCopl {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(
				new String[] {
						"sessionContext-locals.xml",
						SessionContext.base
								+ "/assets/session/sessionContext-copl.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("coplSession",
				KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("carousel-planning");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);

		EphemeralFact ef = new EphemeralFact();
//		ef.put("in0", "C1"); // _carouselCode
//		ef.put("in1", "2015-06-01T13:10:00.000Z"); // _eta 2015-06-01T13:10:00.000Z 
//		ef.put("in2", "149492"); // _rmsKey
//		ef.put("in3", "A");

		
		ef.put("in0", "2015-12-04T00:00:00.000Z"); // _eta 2015-06-01T13:10:00.000Z 
//		ef.put("in1", "C1"); // _reclaimCode
		ef.put("in2", "A"); // _type
//		ef.put("in3", "148184"); // _rmsKey
		ef.put("in4", "S32_1");
		
		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);

		System.out.println("return: " + emTrials.getFacts().get("return"));
		System.out.println(emTrials);
	}
}
