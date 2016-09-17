/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 21, 2015	10:26:15 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.error;

import com.xybase.ax.eai.archcomp.common.util.DateUtil;

/**
 * @note
 *
 */
public class AuditErrorLog {
	private String errorId;
	private String xeaiId;
	private String auditTime;
	private String endpoint;
	private String severity;
	private String eventName;
	private String cause;

	/**
	 * 
	 */
	public AuditErrorLog() {
		super();
		auditTime = DateUtil.getTimestamp();
	}

	public AuditErrorLog(String errorId, String xeaiId, String auditTime,
			String endpoint, String severity, String eventName, String cause) {
		super();
		this.errorId = errorId;
		this.xeaiId = xeaiId;
		this.auditTime = auditTime;
		this.endpoint = endpoint;
		this.severity = severity;
		this.eventName = eventName;
		this.cause = cause;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getXeaiId() {
		return xeaiId;
	}

	public void setXeaiId(String xeaiId) {
		this.xeaiId = xeaiId;
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

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

}
