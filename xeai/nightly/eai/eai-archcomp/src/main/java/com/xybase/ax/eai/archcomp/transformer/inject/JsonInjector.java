/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Nov 17, 2015	4:37:19 PM			Abdul Azis Nur			update  :
 * 															- update method inject(Object value, String rule) , change String 'value' to Object 'value'
 * **************************************************************************************
 */

package com.xybase.ax.eai.archcomp.transformer.inject;

import static com.jayway.jsonpath.JsonPath.parse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.message.converter.util.JsonConverter;

public class JsonInjector implements Injector<DocumentContext> {

	private Object document;

	@SuppressWarnings({})
	private Map<String, String> variables;
	private Map<String, String> indexObject;

	public JsonInjector() {
		variables = new HashMap<String, String>();
		indexObject = new HashMap<String, String>();
	}

	/**
	 * @param variables2
	 */
	public JsonInjector(Map<String, String> variable) {
		// TODO Auto-generated constructor stub
		this.variables = variable;
		indexObject = new HashMap<String, String>();
	}

	@Override
	public void inject(Object value, String rule) {

		// TODO Auto-generated method stub
		if (!isVariableAssignation(rule)) {

			rule = norm(rule);
			
			
			Map<Integer, String> tmpMapRule = new HashMap<Integer, String>();
			int gap = 0;
			String[] rules = rule.split("\\.");

			for (int i = rules.length; i > 0; i--) {
				gap++;
				tmpMapRule.put(gap, getOriginalJasonPath(rule, i));
			}

			int revert = gap;
			int indexArray;
			
			if (!checkNodeJsonParentArray(rules[0] + "." + rules[1]))
				return;

			for (int j = 0; j < gap; j++) {

				indexArray = getIndex(tmpMapRule.get(revert));

				if (tmpMapRule.get(revert).endsWith("[" + indexArray + "]")) {

					if (!checkNodeJsonParentArray(removeLastArrayKeyArray(
							tmpMapRule.get(revert), indexArray)))
						createParentArray(tmpMapRule.get(revert),
								getKey(tmpMapRule.get(revert), indexArray),
								indexArray);

					if (checkExistingIndex(tmpMapRule.get(revert)))
						createParentObjectArray(removeLastArrayKeyArray(
								tmpMapRule.get(revert), indexArray));

				} else if (tmpMapRule.get(revert).endsWith("[]")) {

					if (!checkNodeJsonParentArray(removeLastArrayKeyArray(
							tmpMapRule.get(revert), indexArray)))
						createParentArray(tmpMapRule.get(revert),
								getKey(tmpMapRule.get(revert), indexArray),
								indexArray);

				} else {

					if (!checkNodeJsonParentArray(removeLastArrayKeyArray(
							tmpMapRule.get(revert), indexArray)))
						createParentObject(
								removeLastArrayKeyArray(tmpMapRule.get(revert),
										indexArray),
								getKey(tmpMapRule.get(revert), indexArray));
				}
				revert--;
			}

			if (tmpMapRule.get(1).endsWith("[]")) {
				indexArray = getIndex(tmpMapRule.get(1).toString());
				craeteNodeJsonChildArray(
						removeLastArrayKeyArray(tmpMapRule.get(1), indexArray),
						value);
			} else {
				indexArray = getIndex(tmpMapRule.get(1).toString());
				craeteNodeJsonChildObject(tmpMapRule.get(1).toString(),
						getKey(tmpMapRule.get(1), indexArray), value);
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

	private String getKey(String rule, int indexArray) {

		String[] regex = null;
		int lastIndex = 0;
		String value = null;
		try {
			regex = rule
					.toString()
					.trim()
					.replace(InternalConstant.XEAI_JSON_ARRAY_PATH,
							StringUtil.emptyChar).split("\\.");
			lastIndex = regex.length;
			if (lastIndex > 0) {
				value = regex[lastIndex - 1].toString().trim();
			} else {
				value = rule;

			}
		} catch (Exception ioob) {

		}

		if (value.endsWith("[" + indexArray + "]"))
			value = value.replace("[" + indexArray + "]", StringUtil.emptyChar);

		return removeLastArrayKeyArray(value, indexArray);
	}

	@Override
	public void setTemplate(String template) {
		// TODO Auto-generated method stub
		document = Configuration.defaultConfiguration().jsonProvider()
				.parse(template);
	}

	@Override
	public void setTemplate(DocumentContext context) {
		// TODO Auto-generated method stub
		document = context;
	}

	@Override
	public DocumentContext finalized() {
		// TODO Auto-generated method stub
		return toContext(this.document);
	}

	public DocumentContext toContext(Object payload) {
		try {
			DocumentContext context = JsonPath.using(
					Configuration.builder().options(Option.ALWAYS_RETURN_LIST)
							.build()).parse(payload);
			return context;
		} catch (Exception e) {
			throw new InternalErrorRuntimeException(e);
		}
	}

	@Override
	public Map<String, String> getVariables() {
		// TODO Auto-generated method stub
		return variables;
	}

	@Override
	public void setVariables(Map<String, String> variables) {
		// TODO Auto-generated method stub
		this.variables = variables;

	}

	private void createParentObject(String rule, String key) {

		try {
			parse(document).put(getJsonPath(rule), key, parse("{}").json());

		} catch (PathNotFoundException pnf) {
			pnf.printStackTrace();

		}

	}

	private void createParentObjectArray(String rule) {

		try {

			parse(document).add(rule, parse("{}").json());

		} catch (PathNotFoundException pnf) {
			pnf.printStackTrace();
		}

	}

	private void createParentArray(String rule, String key, int indexArray) {

		try {
			parse(document).put(getPathParentArray(rule, indexArray), key,
					parse("[]").json());

		} catch (PathNotFoundException pnf) {
			pnf.printStackTrace();
		}

	}

	private void craeteNodeJsonChildObject(String rule, String key, Object value) {

		try {
			parse(document).put(getJsonPath(rule), key, value);

		} catch (PathNotFoundException pnf) {
			pnf.printStackTrace();
		}

	}

	private void craeteNodeJsonChildArray(String rules, Object value) {

		try {
			parse(document).add((rules), value);

		} catch (PathNotFoundException pnf) {
			pnf.printStackTrace();
		}

	}

	private boolean checkNodeJsonParentArray(String jsonpath) {
		try {
			JsonPath.read(document, jsonpath);
			return true;
		} catch (PathNotFoundException pnf) {
			return false;
		} catch (InvalidPathException ipe) {
			return false;
		}
	}

	private String getOriginalJasonPath(String rule, int length) {

		String value = "";
		try {
			StringTokenizer st = new StringTokenizer(rule.toString().trim(),
					"\\.");
			st = new StringTokenizer(rule.toString().trim(), "\\.");

			for (int i = 1; i <= length; i++) {
				value += st.nextToken() + ".";

			}

		} catch (Exception ioob) {
			return value;

		}

		if (value.endsWith("."))
			value = value.substring(0, value.length() - 1);
		value.replace("\\..", "\\.").toString();
		if (value.contains("#{}"))
			value = value.replace("#{}", StringUtil.emptyChar);

		return value;
	}

	private String getJsonPath(String rule) {

		String value = "";
		try {
			StringTokenizer st = new StringTokenizer(rule.toString().trim(),
					"\\.");
			int length;
			for (length = 0; st.hasMoreTokens(); length++) {
				st.nextToken();
			}

			st = new StringTokenizer(rule.toString().trim(), "\\.");
			length = length - 1;

			for (int i = 1; i <= length; i++) {
				value += st.nextToken() + ".";

			}
		} catch (Exception ioob) {

			return value;

		}

		if (value.endsWith("."))
			value = value.substring(0, value.length() - 1);
		value.replace("\\..", "\\.").toString();

		return value;
	}

	private String getPathParentArray(String rule, int indexArray) {

		String value = "";
		try {
			StringTokenizer st = new StringTokenizer(rule.toString().trim(),
					"\\.");
			int length;
			for (length = 0; st.hasMoreTokens(); length++) {
				st.nextToken();
			}

			st = new StringTokenizer(rule.toString().trim(), "\\.");
			length = length - 1;

			for (int i = 1; i <= length; i++) {
				if (value.endsWith("[" + indexArray + "]"))
					break;
				value += st.nextToken() + ".";

			}
		} catch (Exception ioob) {

			return value;

		}

		if (value.endsWith("."))
			value = value.substring(0, value.length() - 1);
		value.replace("\\..", "\\.").toString();

		return value;
	}

	private boolean checkExistingIndex(String rule) {

		boolean flag = false;
		String value = "";
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(rule);

		while (m.find()) {
			value += Integer.parseInt(m.group(1)) + "-";
		}
		if (indexObject.get(value) == null) {
			flag = true;
			indexObject.put(value, value);
		}

		return flag;
	}

	private int getIndex(String rule) {

		int value = -99;
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(rule);

		try {
			while (m.find()) {

				value = Integer.parseInt(m.group(1));

			}
		} catch (Exception e) {
			value = -99;
		}

		return value;
	}

	private String removeLastArrayKeyArray(String key, int indexArray) {
		String value = key;

		if (value.endsWith("[" + indexArray + "]")) {
			for (int i = key.length(); i > 0; i--) {
				if (key.substring(0, i).endsWith("[")) {
					value = key.substring(0, i - 1);
					break;
				}
			}
		}

		if (indexArray == -99) {
			value = value.replace("[]", StringUtil.emptyChar);
		}

		return value;
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
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#getConverter()
	 */
	@Override
	public JsonConverter getConverter() {
		// TODO Auto-generated method stub
		return new JsonConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.inject.Injector#clones()
	 */
	@Override
	public Injector<DocumentContext> clones() {
		// TODO Auto-generated method stub
		return new JsonInjector(this.variables);
	}

}
