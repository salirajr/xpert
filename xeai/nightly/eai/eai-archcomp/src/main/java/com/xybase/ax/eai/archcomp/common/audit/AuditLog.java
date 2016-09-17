/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	10:55		Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit;

import java.io.Serializable;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;

public class AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3278180888316646902L;
	private String auditTime;
	private String endpoint;
	private String severity;
	private String eventName;
	private String payload;
	private String xeaiId;
	private String correlationId;
	private String auditParam;
	private String auditType;

	/**
	 * @param auditTime
	 * @param endpoint
	 *            where the data coming from, ip, host, proxy, etc.
	 * @param severity
	 * @param eventName
	 *            event name that registered base on arch-comp defintion.
	 * @param payload
	 * @param xeaiId
	 * @param correlationId
	 * @param auditParam
	 * @param auditType
	 *            eg: RQI:Request-In, RPO:Response-Out,
	 *            WSRQO:WebserviceRequest-Out, WSRPI:WebserviceResponse-Out
	 */
	public AuditLog(String auditTime, String endpoint, String severity,
			String eventName, String payload, String xeaiId,
			String correlationId, String auditParam, String auditType) {
		super();
		this.auditTime = auditTime;
		this.endpoint = endpoint;
		this.severity = severity;
		this.eventName = eventName;
		this.payload = payload;
		this.xeaiId = xeaiId;
		this.correlationId = correlationId;
		this.auditParam = auditParam;
		this.auditType = auditType;
	}

	public AuditLog() {
		super();
		this.auditTime = DateUtil.getTimestamp();
		this.endpoint = "";
		this.severity = "";
		this.eventName = "";
		this.payload = "";
		this.xeaiId = "";
		this.correlationId = "";
		this.auditParam = "";
		this.auditType = "";
	}

	public AuditLog(@SuppressWarnings("rawtypes") Message messageIn) {
		this();
		MessageHeaders headers = messageIn.getHeaders();
		this.auditTime = DateUtil.getTimestamp();
		this.payload = StringUtil.cast(messageIn.getPayload());
		this.xeaiId = StringUtil.cast(headers
				.get(MessageConstants.IntegrationID));
		this.eventName = StringUtil.cast(headers
				.get(MessageConstants.Headers.XeaiEventID));
		this.correlationId = StringUtil.cast(headers
				.get(MessageConstants.CorrelationId));
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Object endpoint) {
		this.endpoint = StringUtil.cast(endpoint);
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getXeaiId() {
		return xeaiId;
	}

	public void setXeaiId(String xeaiId) {
		this.xeaiId = xeaiId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
		this.correlationId = StringUtil.cast(correlationId);
	}

	public String getAuditParam() {
		return auditParam;
	}

	public void setAuditParam(Object auditParam) {
		this.auditParam = StringUtil.cast(auditParam);
	}

	@Override
	public String toString() {
		return "AuditLog [auditTime=" + auditTime + ", endpoint=" + endpoint
				+ ", severity=" + severity + ", eventName=" + eventName
				+ ", payload=" + payload + ", xeaiId=" + xeaiId
				+ ", correlationId=" + correlationId + ", auditParam="
				+ auditParam + ", auditType=" + auditType + "]";
	}

}