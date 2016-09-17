/**
 * 
 */
package com.xybase.ax.eai.waconsole.appliance;

import java.util.Arrays;
import java.util.List;

import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;

/**
 * 			@author xybase-007
 *
 * =============================================================
 * =
 * =          Created By Abdul Azis Nur
 * =          On Mar 1, 2016 3:05:48 PM
 * =          TransformModal.java
 * =
 * =============================================================
 */
public class Transform {
	
	private String context;
	private String payload;
	private List<TransformerRule> transformData;
	private String from;
	private String to;
	private String transformTemplate;
	private String transformType;
	
	
	
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	/**
	 * @return the transformData
	 */
	public List<TransformerRule> getTransformData() {
		return transformData;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @return the transformTemplate
	 */
	public String getTransformTemplate() {
		return transformTemplate;
	}
	/**
	 * @return the transformType
	 */
	public String getTransformType() {
		return transformType;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}
	/**
	 * @param transformData the transformData to set
	 */
	public void setTransformData(List<TransformerRule> transformData) {
		this.transformData = transformData;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @param transformTemplate the transformTemplate to set
	 */
	public void setTransformTemplate(String transformTemplate) {
		this.transformTemplate = transformTemplate;
	}
	/**
	 * @param transformType the transformType to set
	 */
	public void setTransformType(String transformType) {
		this.transformType = transformType;
	}
	public Transform() {
		super();
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transform [context=" + context + ", payload=" + payload
				+ ", transform_data=" + Arrays.toString(transformData.toArray()) + ", from=" + from
				+ ", to=" + to + "]";
	}
	
	
	
	
	

}
