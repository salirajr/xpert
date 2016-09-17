/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	10:18:43 AM			Jovi Rengga Salira		Initial Creation
 * Mar 13, 2015	10:18:43 AM			Jovi Rengga Salira		[1]Support both single-multi result with one method.
 * Mar 13, 2015	10:18:43 AM			Jovi Rengga Salira		Rule:
 * 															- All assigned value will be set and create the path.
 * Nov 17, 2015	4:37:19 PM			Abdul Azis Nur			extract :
 * 															- add logic startsWith in extract method (asInteger)
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Iterators;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.common.util.TransformUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.message.converter.util.Converter;
import com.xybase.ax.eai.archcomp.message.converter.util.JsonConverter;

/**
 * @note
 *
 */
public class JsonExtractor implements Extractor<DocumentContext> {

	private DocumentContext document;

	private Map<String, String> variables;

	/**
	 * 
 	 */
	public JsonExtractor() {
		// TODO Auto-generated constructor stub
		super();
		variables = new HashMap<String, String>();
	}

	public JsonExtractor(DocumentContext document) {
		// TODO Auto-generated constructor stub
		this.document = document;
		// JsonReader jr = new JsonReader();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.extract.Extractor#extract(java.lang
	 * .String)
	 */

	/**
	 * @param variables2
	 */
	public JsonExtractor(Map<String, String> variable, DocumentContext document) {
		// TODO Auto-generated constructor stub
		this.variables = variable;
		this.document = document;
	}

	@Override
	public Object extract(String rule) {
		List<Object> result = null;
		try {
			if (StringUtil.isNullOrBlank(rule))
				return "";
			rule = norm(rule);
			
			if ((rule.startsWith("\"") && rule.endsWith("\""))){
				return rule.substring(1, rule.length() - 1);
			} else if (!rule.startsWith("$.")) {

				try {
					return TransformUtil.asInteger(rule);
				} catch (NumberFormatException e) {
					return null;
				}

			} else {
				result = document.read(rule);
			}
			
			if (result.get(0).toString().equalsIgnoreCase("{}")) {
				return rule;
			} else {
				return StringUtil.isNullOrBlank(result.get(0)) ? StringUtil.emptyChar
						: (result.get(0));
			}

		} catch (PathNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * [1] Careful with below logic, current technology developed with no
	 * iteration support for non-singleton result.
	 * 
	 * @see com.xybase.ax.eai.archcomp.transformer.extract.Extractor#iterate(java.lang
	 *      .String)
	 */
	@Override
	public Iterator<String> iterate(String rule) {
		List<String> result = null;
		try {
			rule = norm(rule);
			result = document.read(rule);
			return result.iterator();
		} catch (PathNotFoundException e) {
			return new ArrayList<String>().iterator();
		}
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
	public void setContext(DocumentContext context) {
		this.document = context;

	}

	public DocumentContext getContext() {
		// TODO Auto-generated method stub
		return this.document;
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
		return Iterators.size(iterate(rule));
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
		return new JsonConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.extract.Extractor#clones()
	 */
	@Override
	public Extractor<DocumentContext> clones() {
		// TODO Auto-generated method stub
		return new JsonExtractor(this.variables, this.document);
	}

	

}
