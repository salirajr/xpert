package com.xybase.ax.eai.waconsole.appliance.mock.model;

public class Mock {

	private String fileContext;
	private String context;
	private String integrationType;
	private String requestPayload;
	private String requestPayloadType;
	private String responsePayload;
	private String responsePayloadType;

	public Mock() {
		// TODO Auto-generated constructor stub
	}

	public String getFileContext() {
		return fileContext;
	}

	public void setFileContext(String fileContext) {
		this.fileContext = fileContext;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}

	public String getRequestPayloadType() {
		return requestPayloadType;
	}

	public void setRequestPayloadType(String requestPayloadType) {
		this.requestPayloadType = requestPayloadType;
	}

	public String getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(String responsePayload) {
		this.responsePayload = responsePayload;
	}

	public String getResponsePayloadType() {
		return responsePayloadType;
	}

	public void setResponsePayloadType(String responsePayloadType) {
		this.responsePayloadType = responsePayloadType;
	}

	@Override
	public String toString() {
		return "Mock [fileContext=" + fileContext + ", context=" + context
				+ ", requestPayload=" + requestPayload
				+ ", requestPayloadType=" + requestPayloadType
				+ ", responsePayload=" + responsePayload
				+ ", responsePayloadType=" + responsePayloadType + "]";
	}

	public String getIntegrationType() {
		return integrationType;
	}

	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}

}
