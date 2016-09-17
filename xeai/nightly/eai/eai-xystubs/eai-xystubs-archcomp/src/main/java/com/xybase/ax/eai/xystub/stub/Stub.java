/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	10:45:36 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.stub;

/**
 * @note
 *
 */
public class Stub {

	private String requestIn;
	private String responseOut;
	private String type;

	public Stub() {
		requestIn = null;
		responseOut = null;
		type = null;
	}

	public String getRequestIn() {
		return requestIn;
	}

	public void setRequestIn(String requestIn) {
		this.requestIn = requestIn;
	}

	public String getResponseOut() {
		return responseOut;
	}

	public void setResponseOut(String responseOut) {
		this.responseOut = responseOut;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
