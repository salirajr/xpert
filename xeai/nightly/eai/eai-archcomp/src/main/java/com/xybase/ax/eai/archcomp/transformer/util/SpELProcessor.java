/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Jun 18, 2015	2:10:15 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @note
 *
 */
public class SpELProcessor {

	private ExpressionParser parser;
	private StandardEvaluationContext context;
	private Map<String, String> variables;

	private Object rootObject;

	public SpELProcessor() {
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
		context.setVariable("var", new HashMap<String, String>());
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
		context.setVariable("var", variables);
	}

	public void setRootObject(Object rootObject) {
		this.rootObject = rootObject;
		context.setRootObject(this.rootObject);
	}

	public Object getRootObject() {
		return rootObject;
	}

	public void setUtils(Object... objects) {
		for (Object object : objects) {
			context.setVariable(object.toString(), object);
		}

	}

	public Object express(String exppression) {
		Object result = null;
		try {
			result = parser.parseExpression(exppression).getValue(context,
					Object.class);
		} catch (SpelEvaluationException e) {
			result = e.getMessage();
		}
		return result;
	}
}
