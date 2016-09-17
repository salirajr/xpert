/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 02102014		Jovi Rengga Salira		Initial Creation
 * 23102014		Jovi Rengga Salira		Add out-arch-source logic,
 * 										privatized  use mantleURL( ... )
 * 11112014		Jovi Rengga Salira		Add NVL-ifnull, logic operation!,
 * 										Implied by the DT XML file!
 * 24112014		Jovi Rengga Salira		Add re-use Decision table, only refresh the artifact of Rule Key and Decision Key,
 * 										Current logic is expensive on Instantiation. 
 * 										ref: relace()
 * **************************************************************************************
 */
package com.concept.ruleengine.dt.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.Container;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.xml.DocumentContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concept.ruleengine.archcomp.ConfigComponent;
import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;
import com.concept.ruleengine.common.util.StringUtil;

public class DecisionTableXmlImpl implements DecisionTable {

	private static Logger logger = LogManager.getLogger(DecisionTableXmlImpl.class);

	public static final String nvlPrefKeyPattern = ConfigComponent.CFG_DT.getString("dt.nvl.keypattern");
	private JXPathContext context;
	private HashMap<String, String> rulesArtifact;
	private HashMap<String, String> decision;
	private List<String> ruleKey;
	private List<String> decisionKey;
	private String ruleName;

	private String ruleKeySchemeXpath;
	private String decisionKeySchemeXpath;
	private String schemeCheckXpath;

	private String suffix = ConstantComponent.SUFFIX_XML;
	private String baseLocation = ConstantComponent.DOMAIN_LOCATION_DT_XML;

	public DecisionTableXmlImpl() {
		super();
	}

	public DecisionTableXmlImpl(String referenceName) {
		this.ruleName = referenceName;
		ruleKeySchemeXpath = "/decision_table_scheme/decision_rules_tables/decision_rules_table[name='" + this.ruleName
				+ "']/rule_key/key";
		decisionKeySchemeXpath = "/decision_table_scheme/decision_rules_tables/decision_rules_table[name='"
				+ this.ruleName + "']/decision_key/key";
		schemeCheckXpath = "count(/decision_table_scheme/decision_rules_tables/decision_rules_table[name='"
				+ this.ruleName + "']/name)";
		String name = ConfigComponent.CFG_DT.getString("dt.name.scheme");
		URL url;
		Container container;

		// Get the URL the of XML document for Schema Definition
		url = mantledURL(name);

		// Construct document container from the URL to XML
		container = new DocumentContainer(url);
		context = JXPathContext.newContext(container);

		Iterator<?> schemeCheck = context.iterate(schemeCheckXpath);
		if (!schemeCheck.hasNext())
			throw new ApplicationRuntimeException(
					"XYBASE Airport Decision Table Exception: No Scheme Found, Check your decision_table_scheme.xml file");
		double schemeCheckValue = Double.valueOf(schemeCheck.next().toString());
		if (schemeCheckValue > 1)
			throw new ApplicationRuntimeException("XYBASE Airport Multiple Decision Table Exception: (" + this.ruleName
					+ ") Check your " + name + ".xml file");

		if (schemeCheckValue < 1)
			throw new ApplicationRuntimeException("XYBASE Airport No Decision Table Exception: (" + this.ruleName
					+ ") Check your " + name + ".xml file");

		Iterator<?> ruleKeyScheme = context.iterate(ruleKeySchemeXpath);
		ruleKey = new ArrayList<String>();
		while (ruleKeyScheme.hasNext()) {
			ruleKey.add(ruleKeyScheme.next().toString());
		}

		Iterator<?> decisionKeyScheme = context.iterate(decisionKeySchemeXpath);
		decisionKey = new ArrayList<String>();
		while (decisionKeyScheme.hasNext()) {
			decisionKey.add(decisionKeyScheme.next().toString());
		}
		name = ruleName;

		// Get the URL the of XML document for Decision Table Data
		url = mantledURL(name);

		// Construct document container from the URL to XML
		container = new DocumentContainer(url);
		context = JXPathContext.newContext(container);
		logger.debug("XYBASE Airport Decision Table Initiation: " + this.ruleName + " Key"
				+ Arrays.toString(ruleKey.toArray()) + ", Decision" + Arrays.toString(decisionKey.toArray()));

		rulesArtifact = new HashMap<String, String>();
		decision = new HashMap<String, String>();
	}

	public void set(String key, Object value) {
		if (!ruleKey.contains(key))
			throw new ApplicationRuntimeException("XYBASE Airport Definition Decision Table Exception: No Key[" + key
					+ "] Description on " + ruleName);
		rulesArtifact.put(key, StringUtil.toString(value));
	}

	public DecisionTableXmlImpl laced() {

		if (ruleKey.size() != rulesArtifact.size())
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: Rule Artifact is not ready!");

		String decisionXpath = "/decision_table/decision_rule/rule_sets/rule_set";
		String nvlXpath = "/decision_table/decision_rule/ifnull";
		int i = 0;
		while (i < ruleKey.size()) {
			if (i == 0)
				decisionXpath += "[";
			decisionXpath += "(rule/hash/key='" + ruleKey.get(i) + "' and rule/hash/value='"
					+ rulesArtifact.get(ruleKey.get(i)) + "')";
			if (i < ruleKey.size() - 1) {
				decisionXpath += " and ";
			}
			if (i >= ruleKey.size() - 1)
				decisionXpath += "]";
			i++;

		}

		i = 0;
		Iterator<?> result;
		while (i < decisionKey.size()) {
			result = context.iterate(decisionXpath + "/decision/hash[key='" + decisionKey.get(i) + "']/value");
			if (result.hasNext()) {
				decision.put(decisionKey.get(i), result.next().toString());
			}

			// JS, 11112014, simple concept, extract on the peculiar path
			// provide, by the decision table.
			result = context.iterate(nvlXpath + "/decision/hash[key='" + decisionKey.get(i) + "']/value");
			if (result.hasNext()) {
				decisionKey.add(nvlPrefKeyPattern + decisionKey.get(i));
				decision.put(nvlPrefKeyPattern + decisionKey.get(i), result.next().toString());
			}

			i++;
		}
		return this;
	}

	// JS, retrieve decision table result, after laced!
	public String get(String key) {
		if (decisionKey.contains(key)) {
			String result = decision.get(key);
			if (logger.isDebugEnabled())
				logger.debug("Decision Table " + this.ruleName + " [" + key + ":" + result + "]");
			return result;
		} else
			throw new ApplicationRuntimeException("XYBASE Airport Definition Decision Table Exception: No Key[" + key
					+ "] Description on " + ruleName);
	}

	public DecisionTableXmlImpl relace() {
		rulesArtifact = new HashMap<String, String>();
		decision = new HashMap<String, String>();
		return this;
	}

	// JS, 11112014
	public String getNVL(String key) {
		key = nvlPrefKeyPattern + key;
		try {
			return get(key);
		} catch (ApplicationRuntimeException are) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Definition Decision Table Exception: No Default ifnull(NVL)-Key["
							+ key.replaceAll(nvlPrefKeyPattern, "") + "] Description on " + ruleName);
		}
	}

	// JS, Only to add dynamically development for Rule Engine, Local!
	// Simple logic, set an alternate source use if the application host on
	// server, use out-source-arch
	private URL mantledURL(String referenceName) {
		baseLocation = ConstantComponent.BASEPTH;
		if (ConstantComponent.BASEPTH == null)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Data Source Exception, [System Properties: "
							+ ConstantComponent.SYSPROBASEPTH + " doesnot exist!! ]");
		baseLocation += ConstantComponent.DOMAIN_LOCATION_DT_XML;
		try {
			return new URL("file://" + baseLocation + referenceName + "" + suffix);
		} catch (MalformedURLException e) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Malformed URL Exception[format: " + baseLocation
							+ referenceName + ".xml" + "]");
		}
	}

	@Override
	public DecisionTable relace(String name) {
		return new DecisionTableXmlImpl(name);
	}
}
