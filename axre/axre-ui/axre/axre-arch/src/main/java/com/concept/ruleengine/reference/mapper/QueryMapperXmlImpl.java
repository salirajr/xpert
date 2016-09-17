/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 02102014		Jovi Rengga Salira		Initial Creation - Based on Decision Table Logics
 * 25112014		Jovi Rengga Salira		Add Configurable Mechanism!
 * 26112014		Jovi Rengga Salira		Add re-use feature!, relace()
 * **************************************************************************************
 */
package com.concept.ruleengine.reference.mapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.Container;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.xml.DocumentContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.concept.ruleengine.archcomp.ConstantComponent;
import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;
import com.concept.ruleengine.common.util.StringUtil;

public class QueryMapperXmlImpl implements QueryMapper {

	private static Logger logger = LogManager.getLogger(QueryMapperXmlImpl.class);
	private JXPathContext context;

	private HashMap<String, String> queryParameters;
	private List<String> parameterKey;

	private String query, baseQuery, mapName, queryName;
	private String queryParameterXpath;
	private String queryXpath;

	private String suffix = ConstantComponent.SUFFIX_XML;

	private String baseLocation = ConstantComponent.DOMAIN_LOCATION_QUERY_XML;

	public QueryMapperXmlImpl() {
		super();
	}

	public QueryMapperXmlImpl(String mapNames, String queryNames) {
		mapName = mapNames;
		queryName = queryNames;
		queryXpath = "/sqlquery/sqlmaps/map[name='" + this.queryName + "']/query";
		queryParameterXpath = "/sqlquery/sqlmaps/map[name='" + this.queryName + "']/parameterized/key";

		URL url;
		Container container;

		url = mantledURL(mapName);

		container = new DocumentContainer(url);
		context = JXPathContext.newContext(container);

		Iterator<?> queryCounts = context.iterate("count(" + queryXpath + ")");
		double queryCountsValue = Double.valueOf(queryCounts.next().toString());
		if (queryCountsValue > 1)
			throw new ApplicationRuntimeException("XYBASE Airport Map Query: Multiple Query Exception: ("
					+ queryCountsValue + " . name:" + queryName + ") Check your " + mapName + ".xml file");

		if (queryCountsValue < 1)
			throw new ApplicationRuntimeException("XYBASE Airport Map Query: No Query Exception: (" + queryCountsValue
					+ " . name:" + queryName + ") Check your " + mapName + ".xml file");

		Iterator<?> queryXpathValue = context.iterate(queryXpath);
		baseQuery = queryXpathValue.next().toString();
		query = baseQuery;

		Iterator<?> queryParametersXpathValue = context.iterate(queryParameterXpath);

		queryParameters = new HashMap<String, String>();
		parameterKey = new ArrayList<String>();
		while (queryParametersXpathValue.hasNext()) {
			parameterKey.add(queryParametersXpathValue.next().toString());
		}
		if (logger.isDebugEnabled())
			logger.debug("Retrieved Query Mapper :" + mapName + ", named:" + queryName);
	}

	public QueryMapperXmlImpl parameterized(String parameter, Object value) {
		if (!parameterKey.contains(parameter))
			throw new ApplicationRuntimeException("XYBASE Airport Map Query Exception: No parameters[" + parameter
					+ "] Description on " + mapName);
		queryParameters.put(parameter, value.toString());
		query = query.replaceAll("\\$" + parameter + "\\$", value.toString());
		return this;
	}

	public QueryMapperXmlImpl relace() {
		if (logger.isDebugEnabled())
			logger.debug("Relaced Query Mapper :" + mapName + ", named:" + queryName);
		queryParameters = new HashMap<String, String>();
		query = baseQuery;
		return this;
	}

	public String getQuery() {
		if (queryParameters.size() != parameterKey.size())
			throw new ApplicationRuntimeException("XYBASE Airport Map Query Exception: Rendered Failed[ Expecting: "
					+ parameterKey.size() + ", Actual: " + queryParameters.size() + "]");
		return query.replaceAll(StringUtil.CustomRegex.WHITESPACE, " ");
	}

	// #25112014
	private URL mantledURL(String mapName) {
		baseLocation = ConstantComponent.BASEPTH;
		if (ConstantComponent.BASEPTH == null)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Data Source Exception, [System Properties: "
							+ ConstantComponent.SYSPROBASEPTH + " doesnot exist!! ]");
		baseLocation += ConstantComponent.DOMAIN_LOCATION_QUERY_XML;
		try {
			return new URL("file://" + baseLocation + mapName + "" + suffix);
		} catch (MalformedURLException e) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Malformed URL Exception[format: " + baseLocation + mapName
							+ ".xml" + "]");
		}
	}

	@Override
	public QueryMapper relace(String mapNames, String queryNames) {
		return new QueryMapperXmlImpl(mapNames, queryNames);
	}
}
