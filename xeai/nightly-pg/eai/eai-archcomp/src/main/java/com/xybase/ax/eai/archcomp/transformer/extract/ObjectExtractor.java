/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 1, 2015	5:04:36 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.message.converter.util.Converter;
import com.xybase.ax.eai.archcomp.message.converter.util.ObjectConverter;

/**
 * @note
 *
 */
public class ObjectExtractor implements Extractor<Object> {

	public class Type {
		public final static String EXTRACTION_FORCED_ON_LIST_TYPE = "EXTRACTION_FORCED_ON_LIST_TYPE";
	}

	private ExpressionParser parser;
	private StandardEvaluationContext context;

	private Object rootObject;

	private Map<String, String> variables;

	private final int indexStart = 0;

	public ObjectExtractor() {
		
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
		variables = new HashMap<String, String>();
	}

	/**
	 * @param variables2
	 */
	public ObjectExtractor(Map<String, String> variable, Object root) {
		// TODO Auto-generated constructor stub
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
		this.rootObject = root;
		context.setRootObject(this.rootObject);
		this.variables = variable;
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#extract(java.lang
	 * .String)
	 */
	@Override
	public Object extract(String rule) {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBlank(rule))
			return StringUtil.nullValue;
		if (!CustomUtil.isAListClass(rootObject)
				&& variables
						.containsKey(ObjectExtractor.Type.EXTRACTION_FORCED_ON_LIST_TYPE)
				&& variables.get(
						ObjectExtractor.Type.EXTRACTION_FORCED_ON_LIST_TYPE)
						.equalsIgnoreCase(StringUtil.trueValue)) {
			List<Object> lObject = new ArrayList<Object>();
			lObject.add(rootObject);
			setContext(lObject);
			rootObject = lObject;
		}
		rule = norm(rule);
		return parser.parseExpression(rule).getValue(context, Object.class);
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
		List<String> result = new ArrayList<String>();
		if (StringUtil.isNullOrBlank(rule))
			return result.iterator();

		rule = norm(rule);
		result = parser.parseExpression(rule).getValue(context, List.class);
		if (result == null)
			return new ArrayList<String>().iterator();
		return result.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#setContext(java
	 * .lang.Object)
	 */
	@Override
	public void setContext(Object arg0) {
		rootObject = arg0;
		context.setRootObject(rootObject);
	}
	
	@Override
	public Object getContext() {
		// TODO Auto-generated method stub
		return this.context;
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
		return variables;
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
		this.variables = variables;
		context.setVariable("var", this.variables);
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
		if (rule == null)
			rule = StringUtil.emptyChar;
		if (!rule.trim().equals(StringUtil.emptyChar)) {
			rule = norm(rule);
			rule += ".";
		}
		rule += "size()";
		Object result;
		result = extract(rule);

		
		
		if (result instanceof Integer)
			return (Integer) result;
		return -1;
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
			if (!prule.trim().equals("")) {
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
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#getConverter()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Converter getConverter() {
		// TODO Auto-generated method stub
		return new ObjectConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.extract.Extractor#clones()
	 */
	@Override
	public Extractor<Object> clones() {
		// TODO Auto-generated method stub
		
		return new ObjectExtractor(this.variables, this.rootObject);
	}



}
