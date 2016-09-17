/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.rule;

public class TransformerRule {

	private int transformId;
	private int transformRuleId;

	private String transformRuleSource;
	private String transformRuleTarget;

	private int transformRuleConfig;
	private String transformRuleMatrix;

	public TransformerRule() {

	}

	public int getTransformId() {
		return transformId;
	}

	public void setTransformId(int transformId) {
		this.transformId = transformId;
	}

	public int getTransformRuleId() {
		return transformRuleId;
	}

	public void setTransformRuleId(int transformRuleId) {
		this.transformRuleId = transformRuleId;
	}

	public String getTransformRuleSource() {
		return transformRuleSource;
	}

	public void setTransformRuleSource(String transfromRuleSource) {
		this.transformRuleSource = transfromRuleSource;
	}

	public String getTransformRuleTarget() {
		return transformRuleTarget;
	}

	public void setTransformRuleTarget(String transformRuleTarget) {
		this.transformRuleTarget = transformRuleTarget;
	}

	public int getTransformRuleConfig() {
		return transformRuleConfig;
	}

	public void setTransformRuleConfig(int transformRuleConfig) {
		this.transformRuleConfig = transformRuleConfig;
	}

	public String getTransformRuleMatrix() {
		return transformRuleMatrix;
	}

	public void setTransformRuleMatrix(String transformRuleMatrix) {
		this.transformRuleMatrix = transformRuleMatrix;
	}

	@Override
	public String toString() {
		return "TransformRule [transformId=" + transformId
				+ ", transformRuleId=" + transformRuleId
				+ ", transformRuleSource=" + transformRuleSource
				+ ", transformRuleTarget=" + transformRuleTarget
				+ ", transformRuleConfig=" + transformRuleConfig
				+ ", transformRuleMatrix=" + transformRuleMatrix + "]";
	}

}
