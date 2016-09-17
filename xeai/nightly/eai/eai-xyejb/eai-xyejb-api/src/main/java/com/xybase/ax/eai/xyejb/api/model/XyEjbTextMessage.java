/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 22, 2015	2:52:29 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xyejb.api.model;

/**
 * @note
 *
 */
public class XyEjbTextMessage extends XyEjbMessage<String> {

	private String encodeType;

	public XyEjbTextMessage() {
		super();
	}

	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}

}
