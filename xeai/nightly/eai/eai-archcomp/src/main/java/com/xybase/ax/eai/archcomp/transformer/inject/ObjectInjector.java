/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Jun 16, 2015	10:01:38 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.inject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.message.converter.util.ObjectConverter;

/**
 * @note
 *
 */
public class ObjectInjector implements Injector<Object> {

	private Object rootObject;

	private int indexStart = 0;

	private ExpressionParser parser;

	private Map<String, String> variables;

	private boolean selfAssignment = false;

	private String template;

	/**
	 * 
	 */
	public ObjectInjector() {
		// TODO Auto-generated constructor stub
		rootObject = null;
		parser = new SpelExpressionParser();
		variables = new HashMap<String, String>();
	}

	/**
	 * @param variables2
	 */
	public ObjectInjector(Map<String, String> variable, boolean sAssignment) {
		// TODO Auto-generated constructor stub
		this.selfAssignment = sAssignment;
		parser = new SpelExpressionParser();
		this.variables = variable;
		rootObject = null;
		parser = new SpelExpressionParser();
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
		// TODO Auto-generated method stub
		if (!isVariableAssignation(rule)) {
			rule = norm(rule);
			
			if (value.equals(InternalConstant.XEAI_TRANSFORM_NO_EXTRACTION)) {
				if (selfAssignment)
					rootObject = parser.parseExpression(rule).getValue(
							rootObject, Object.class);
				else
					parser.parseExpression(rule).getValue(rootObject,
							Object.class);
			} else {
				parser.parseExpression(rule).setValue(rootObject, value);
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
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.inject.Injector#setTemplate(java
	 * .lang.String)
	 */
	@Override
	public void setTemplate(String template) {
		// TODO Auto-generated method stub
		this.template = template;
		try {
			rootObject = CustomUtil.asInstance(this.template);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			throw new InternalErrorRuntimeException(
					"Invalid Template Configuration, please check your template definition!!",
					e);
		}
	}

	@Override
	public void setTemplate(Object context) {
		// TODO Auto-generated method stub
		this.rootObject = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#finalized()
	 */
	@Override
	public Object finalized() {
		// TODO Auto-generated method stub
		return rootObject;
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

	public void setIndexStart(int indexStart) {
		this.indexStart = indexStart;
	}

	public void setSelfAssignment(boolean selfAssignment) {
		this.selfAssignment = selfAssignment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#getConverter()
	 */
	@Override
	public ObjectConverter getConverter() {
		// TODO Auto-generated method stub
		return new ObjectConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#clones()
	 */
	@Override
	public Injector<Object> clones() {
		// TODO Auto-generated method stub
		return new ObjectInjector(this.variables, this.selfAssignment);
	}

}
