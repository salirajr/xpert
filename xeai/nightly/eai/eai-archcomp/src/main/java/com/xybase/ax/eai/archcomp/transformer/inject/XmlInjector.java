/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.inject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.w3c.dom.Document;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.message.converter.util.XmlConverter;
import com.xybase.ax.eai.archcomp.transformer.util.DOMFactory;

public class XmlInjector implements Injector<Document> {

	private JXPathContext context;
	private Document document;

	private Map<String, String> variables;

	private final int indexStart = 1;

	/**
	 * 
	 */

	public XmlInjector() {
		super();
		variables = new HashMap<String, String>();
	}

	public XmlInjector(Map<String, String> variable) {
		this.variables = variable;
	}

	public XmlInjector(Document document) {
		// TODO Auto-generated constructor stub
		variables = new HashMap<String, String>();
		this.document = document;
		context = JXPathContext.newContext(document);
		context.setFactory(new DOMFactory());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.inject.Injector#inject(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void inject(Object value, String rule) {
		if (!isVariableAssignation(rule)) {
			rule = norm(rule);
			boolean previousLenient = context.isLenient();
			context.setLenient(false);
			try {
				try {
					context.getValue(rule);
				} catch (JXPathException jex) {
					context.createPath(rule);
				}
				context.setValue(rule, value);
			} finally {
				context.setLenient(previousLenient);
			}

		} else {
			variables.put(rule.replace(
					InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER,
					StringUtil.emptyChar), value + StringUtil.emptyChar);
		}

	}

	private boolean isVariableAssignation(String rule) {
		if (!rule
				.contains(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER))
			return false;
		String[] pRules = rule
				.split(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER);
		if (pRules.length == 2 && pRules[0].trim().equals(StringUtil.emptyChar))
			return true;

		return false;
	}

	private String norm(String rule) {
		if (variables == null
				|| variables.size() == 0
				|| !rule.contains(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER))
			return rule;

		String[] prules = rule
				.split(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER);
		String result = rule
				.startsWith(InternalConstant.XEAI_TRANSFORM_VARIABLES_IDENTIFIER) ? StringUtil.emptyChar
				: prules[0];
		Set<String> vars = variables.keySet();
		Iterator<String> iVar;
		String var;
		for (String prule : prules) {
			if (!prule.trim().equals(StringUtil.emptyChar)) {
				iVar = vars.iterator();
				while (iVar.hasNext()) {
					var = iVar.next();
					if (prule.startsWith(var)) {
						// Changes for index Variables!!!
						if (variables
								.containsKey(InternalConstant.XEAI_TRANSFORM_INDEX_IDENTIFIER
										+ var))
							prule = prule.replaceFirst(
									var,
									StringUtil.emptyChar
											+ (Integer.parseInt(variables
													.get(var)) + indexStart));
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
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#finalized()
	 */
	@Override
	public Document finalized() {
		// TODO Auto-generated method stub
		return this.document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.inject.Injector#setTemplate(java
	 * .lang.Object)
	 */
	@Override
	public void setTemplate(String template) {
		this.document = new XmlConverter().toContext(template);
		context = JXPathContext.newContext(document);
		context.setFactory(new DOMFactory());
		
	}

	@Override
	public void setTemplate(Document cts) {
		// TODO Auto-generated method stub
		this.document = cts;
		context = JXPathContext.newContext(document);
		context.setFactory(new DOMFactory());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#getVariables()
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
	 * com.xybase.ax.eai.archcomp.transform.inject.Injector#setVariables(java
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
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#getConverter()
	 */
	@Override
	public XmlConverter getConverter() {
		// TODO Auto-generated method stub
		return new XmlConverter();
	}

	@Override
	public Injector<Document> clones() {
		// TODO Auto-generated method stub
		return new XmlInjector(this.variables);
	}

}
