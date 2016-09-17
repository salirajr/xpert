package craps.ingrate.ruleengine.session.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.model.EphemeralFact;
import com.concept.ruleengine.model.EphemeralMantle;
import com.concept.ruleengine.session.KnowledgeSession;

public class SessionContextSdop {

	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(new String[] { "sessionContext-locals.xml",
				SessionContext.base + "/assets/session/sessionContext-sdop.xml" });

		KnowledgeSession knowledgeSession = ac.getBean("sdopSession", KnowledgeSession.class);

		knowledgeSession.createSession();

		EphemeralMantle emTrials = new EphemeralMantle();
		emTrials.setOperationName("stand-operation");
		emTrials.setPhase("0000000");
		emTrials.setRound(0);
		
		EphemeralFact ef = new EphemeralFact();
//		ef.put("in0", "A11");
//		ef.put("in1", "2015-10-29T22:25:00.000Z"); // _eta
//		ef.put("in2", "2015-10-07T23:15:00.000Z"); // _etd
//		ef.put("in3", "J"); // _serviceType
//		ef.put("in4", "744"); // _aircraftType
//		ef.put("in5", "1"); // _spSeq
		ef.put("in6", "100006603"); //_visitSeq
		ef.put("in7", "C"); // _type
//		ef.put("in8", "2F"); // _terminalCode
//		ef.put("in9", ""); // _gateCode
//		ef.put("in10", "D"); // _categoryCode
//		ef.put("in11", "GA"); // _airline\Code
		
		emTrials.setFacts(ef);

		knowledgeSession.inject(emTrials);

		System.out.println("return: " + emTrials.getFacts().get("return"));
		System.out.println(emTrials);
	}
}
