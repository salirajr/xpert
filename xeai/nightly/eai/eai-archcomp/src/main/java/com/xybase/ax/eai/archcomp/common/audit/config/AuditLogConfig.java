/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 10, 2015	10:59:31 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.config;

import java.util.Map;

import com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDao;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;

/**
 * @note
 *
 */
public class AuditLogConfig implements CrowbarBus {

	private int key;
	private String event;
	private int level;
	private int config;
	private String correlation;
	private String parameterized;
	private String type;

	private AuditLogConfigDao dao;

	public AuditLogConfig() {
		this.config = 0;
		this.key = 0;
		this.event = null;
		this.level = 0;
		this.correlation = null;
		this.parameterized = null;
		this.type = null;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getConfig() {
		return config;
	}

	public void setConfig(int config) {
		this.config = config;
	}

	public String getCorrelation() {
		return correlation;
	}

	public void setCorrelation(String correlation) {
		this.correlation = correlation;
	}

	public String getParameterized() {
		return parameterized;
	}

	public void setParameterized(String parameterized) {
		this.parameterized = parameterized;
	}

	public void setAuditLogDao(AuditLogConfigDao dao) {
		this.dao = dao;
	}

	@Override
	public String toString() {
		return "AuditLogConfig [key=" + key + ", event=" + event + ", level="
				+ level + ", config=" + config + ", correlation=" + correlation
				+ ", parameterized=" + parameterized + ", type=" + type + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.control.bus.util.BusCrowbar#reinitialize()
	 */
	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		Map<String, String> reinit = dao.getRaw(this.event, this.key,
				this.level);
		this.parameterized = reinit.get("parameterized");
		this.correlation = reinit.get("correlation");
		this.type = reinit.get("type");
		this.config = Integer.valueOf(reinit.get("config"));
		return BusRspUtil.asResponse(CrowbarConstant.state.INITIALIZATION_SUCCEED,
				toString());
	}

	@Override
	public String reinitialized(String operand) {
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATIONP,
				CrowbarConstant.state.CREINITIALIZATIONP,
				CrowbarConstant.message.CREINITIALIZATIONP);
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CDESTROYOFF,
				CrowbarConstant.state.CDESTROYOFF,
				CrowbarConstant.message.CDESTROYOFF);
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return destroy();
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asInfo(this.getClass().getName(), "",
				CrowbarBus.class.getName());
	}

}
