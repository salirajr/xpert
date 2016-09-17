/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 28, 2015	9:50:43 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.selector;

/**
 * @note
 *
 */
public class Selector {
	private int id;
	private int sequence;
	private String expression;
	private String discardMessage;
	private int config;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDiscardMessage() {
		return discardMessage;
	}

	public void setDiscardMessage(String discardMessage) {
		this.discardMessage = discardMessage;
	}

	public int getConfig() {
		return config;
	}

	public void setConfig(int config) {
		this.config = config;
	}

}
