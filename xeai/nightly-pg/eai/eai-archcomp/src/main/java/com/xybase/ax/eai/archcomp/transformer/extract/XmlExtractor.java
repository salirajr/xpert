/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 3, 2015	1:37:19 PM			Jovi Rengga Salira		Initial Creation
 * Mar 21, 2015	1:37:19 PM			Jovi Rengga Salira		Add variables logics
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.extract;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.w3c.dom.Document;

import com.google.common.collect.Iterators;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.message.converter.util.XmlConverter;

public class XmlExtractor implements Extractor<Document> {

	private JXPathContext context;
	private Document contextDocument;
	private Map<String, String> variables;

	/**
	 * 
	 */
	public XmlExtractor() {
		// TODO Auto-generated constructor stub
		super();
		variables = new HashMap<String, String>();
	}

	// JXPathContext.newContext(document);
	public XmlExtractor(Document context) {
		// TODO Auto-generated constructor stub
		this.contextDocument = context;
		this.context = JXPathContext.newContext(context);
		variables = new HashMap<String, String>();
	}

	/**
	 * @param variables2
	 */
	public XmlExtractor(Map<String, String> variable, JXPathContext context) {
		// TODO Auto-generated constructor stub
		this.variables = variable;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#extract(java.lang
	 * .String)
	 */
	@Override
	public String extract(String rule) {

		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBlank(rule))
			return StringUtil.emptyChar;
		try {
			rule = norm(rule);
			Object result = context.getValue(rule);
			return StringUtil.isNullOrBlank(result) ? StringUtil.emptyChar
					: StringUtil.cast(result);
		} catch (JXPathNotFoundException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#iterate(java.lang
	 * .String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<String> iterate(String rule) {
		// TODO Auto-generated method stub
		rule = norm(rule);
		return this.context.iterate(rule);
	}

	private String norm(String rule) {
		if (variables == null
				|| variables.size() == 0
				|| !rule.contains(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER))
			return rule;

		String[] prules = rule
				.split(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER);
		String result = rule
				.startsWith(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER) ? ""
				: prules[0];
		Set<String> vars = variables.keySet();
		Iterator<String> iVar;
		String var;
		for (String prule : prules) {
			if (!prule.trim().equals("")) {
				iVar = vars.iterator();
				while (iVar.hasNext()) {
					var = iVar.next();
					if (prule.startsWith(var)) {

						// Changes for index Variables!!!
						if (variables
								.containsKey(InternalConstant.XEAI_TRANSFORM_INDEX_IDENTIFIER
										+ var))
							prule = prule.replaceFirst(var, variables.get(var));
						else
							prule = prule.replaceFirst(var,
									"\"" + variables.get(var) + "\"");
						result += prule;
						break;
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#setContext(java
	 * .lang.Object)
	 */
	@Override
	public void setContext(Document ctx) {
		this.contextDocument = ctx;
		this.context = JXPathContext.newContext(this.contextDocument);
	}

	@Override
	public Document getContext() {
		// TODO Auto-generated method stub
		return contextDocument;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#getVariables()
	 */
	@Override
	public Map<String, String> getVariables() {
		// TODO Auto-generated method stub
		return this.variables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#setVariables(java
	 * .util.Map)
	 */
	@Override
	public void setVariables(Map<String, String> variables) {
		// TODO Auto-generated method stub
		this.variables = variables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#count(java.lang
	 * .String)
	 */
	@Override
	public int count(String rule) {
		// TODO Auto-generated method stub
		return Iterators.size(iterate(rule));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#getConverter()
	 */
	@Override
	public XmlConverter getConverter() {
		// TODO Auto-generated method stub
		return new XmlConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.extract.Extractor#clones()
	 */
	@Override
	public Extractor<Document> clones() {
		// TODO Auto-generated method stub
		return new XmlExtractor(this.variables, this.context);
	}

}
