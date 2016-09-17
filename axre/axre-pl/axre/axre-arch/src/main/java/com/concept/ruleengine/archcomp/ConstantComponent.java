package com.concept.ruleengine.archcomp;

public class ConstantComponent {

	public static String SYSPROBASEPTH = "axre.archcomp.basepath";
	
	public static String BASEPTH = System.getProperty(SYSPROBASEPTH);

	/*
	 * ::System.getProperty("jboss.application.ext.data"); :Base-location of
	 * rule/decision-table file[dir content, /dt-source;/rules] Local Test, no
	 * required domain
	 */

	/*
	 * ::System.getProperty("jboss.application.ext.data.rule.pttrn"); :"rules/"
	 * .. Local test, assigned the value from system properties
	 */

	/*
	 * ::System.getProperty("jboss.application.ext.data.dt.pttrn");
	 * :"dt-source/" .. Local test, assigned the value from system properties
	 */
	public static String DOMAIN_LOCATION_DT_XML = System.getProperty("axre.archcomp.dt.xml.path");

	public static String DOMAIN_LOCATION_DT_XLS = System.getProperty("axre.archcomp.dt.xls.path");

	public static String DOMAIN_LOCATION_QUERY_XML = System.getProperty("axre.archcomp.qm.xml.path");

	public static String DOMAIN_LOCATION_QUERY_XLS = System.getProperty("axre.archcomp.qm.xls.path");

	public static String SUFFIX_XLS = ConfigComponent.CST.getString("app.assets.suffix.xls");

	public static String SUFFIX_XML = ConfigComponent.CST.getString("app.assets.suffix.xml");

}
