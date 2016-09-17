/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 14, 2015	11:10:51 AM			Jovi Rengga Salira		[0]Initial Creation
 * Mar 14, 2015	11:10:51 AM			Jovi Rengga Salira		Add method direct set & put to variables
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @note [0] function as Common model for EAI, cast as DataPipe!!
 *
 */
public class XMessage<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4738590011953480390L;
	private final T payload;
	private Map<String, String> variables;

	public XMessage(T payload) {
		this.payload = payload;
		variables = new HashMap<String, String>();
	}

	public XMessage(T payload, HashMap<String, String> map) {
		this.payload = payload;
		variables = map;
	}

	public XMessage(T payload, HashMap<String, String> header,
			HashMap<String, String> map) {
		this.payload = payload;
		this.variables = map;
	}

	public T getPayload() {
		return this.payload;
	}

	public void put(String key, String value) {
		this.variables.put(key, value);
	}

	public Object get(String key) {
		return this.variables.get(key);
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

}
