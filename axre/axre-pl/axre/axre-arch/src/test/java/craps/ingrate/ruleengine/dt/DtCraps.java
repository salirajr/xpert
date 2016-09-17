package craps.ingrate.ruleengine.dt;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.concept.ruleengine.common.util.StringUtil;
import com.concept.ruleengine.dt.model.DecisionTable;
import com.concept.ruleengine.dt.model.DecisionTableXlsImpl;

import craps.ingrate.ruleengine.session.context.SessionContext;

public class DtCraps {
	private static ClassPathXmlApplicationContext ac;

	public static void main(String[] args) {
		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(
				new String[] { "sessionContext-locals.xml" });

		DecisionTable dt = ac.getBean("xlsDecisionTable",
				DecisionTableXlsImpl.class);

		dt.relace("_xdt_sdop_flightstand");

		dt.set("DAYOFWEEK", "1");
		dt.set("AIRLINECODE", "KK");
		dt.set("CATEGORYCODE", "I");
		dt.laced();
		System.out.println(dt.get("PREFEREDSTAND"));

	}

	public static void mainB(String[] args) {
		// _xdt_sdop_cargoflight

		SessionContext.alterSession();
		ac = new ClassPathXmlApplicationContext(
				new String[] { "sessionContext-locals.xml" });

		DecisionTable dt = ac.getBean("xlsDecisionTable",
				DecisionTableXlsImpl.class);

		dt.relace("_xdt_sdop_cargoflight");
		System.out.println(StringUtil.toList(dt.laced().get("LISTVALUE"),
				StringUtil.CustomRegex.COMMA).contains("J"));
	}
}
