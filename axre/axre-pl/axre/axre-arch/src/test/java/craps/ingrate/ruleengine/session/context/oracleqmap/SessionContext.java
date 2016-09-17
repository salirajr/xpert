package craps.ingrate.ruleengine.session.context.oracleqmap;

public class SessionContext {

	public static String base = "file:/home/salirajr/Projects/Axpert/AXRE/axre-pl/axre/axre-arch/src/main/resources/CONTEXT-INF";
	public static String wire = "file:/home/salirajr/Projects/Axpert/AXRE/axre-pl/axre/axre-arch/src/main/resources/CONTEXT-INF";

	public static void alterSession() {

		System.setProperty("axre.archcomp.basepath",
				"/home/salirajr/Projects/Axpert/AXRE/axre-pl/axre/axre-arch/src/main/resources/CONTEXT-INF");

		System.setProperty("axre.archcomp.wiring.basepath",
				"/home/salirajr/Projects/Axpert/AXRE/axre-pl/axre/axre-wire/src/main/resources/WIRING-INF");

		System.setProperty("axre.archcomp.dt.xml.path", "/assets/axre/apii/dtsource/xml/");
		System.setProperty("axre.archcomp.dt.xls.path", "/assets/axre/apii/dtsource/");
		System.setProperty("axre.archcomp.qm.xml.path", "/assets/axre/apii/queriesmap/xml/");
		System.setProperty("axre.archcomp.qm.xls.path", "/assets/axre/apii/queriesmap-oracle/");
	}

}
