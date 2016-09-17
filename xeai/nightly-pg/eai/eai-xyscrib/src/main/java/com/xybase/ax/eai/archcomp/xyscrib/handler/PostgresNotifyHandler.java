package com.xybase.ax.eai.archcomp.xyscrib.handler;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.springframework.messaging.support.GenericMessage;

import com.google.common.base.Objects;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.xybase.ax.eai.archcomp.common.audit.AuditLog;
import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.handler.Handler;
import com.xybase.ax.eai.archcomp.lookup.Lookup;
import com.xybase.ax.eai.archcomp.message.converter.util.JsonConverter;
import com.xybase.ax.eai.archcomp.xyscrib.constant.MessageConstant;
import com.xybase.ax.eai.archcomp.xyscrib.listener.pg.model.PostgresNotification;

public class PostgresNotifyHandler implements
		Handler<PostgresNotification, PostgresNotification> {

	private JsonConverter converter;
	private Lookup payloadKey;
	private String tnameExp = "$.table_name";
	private String oldDtExp = "$.payload.old_data";
	private String newDtExp = "$.payload.new_data";

	private JSONObject jsonObject;

	private AuditLogConfig auditLogConfigOut;
	private AuditLogDao auditLogDao;

	public PostgresNotifyHandler() {
		// TODO Auto-generated constructor stub
		converter = new JsonConverter();
		jsonObject = new JSONObject();
		auditLogConfigOut = new AuditLogConfig();
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CDESTROYOFF,
				CrowbarConstant.state.CDESTROYOFF,
				CrowbarConstant.message.CDESTROYOFF);
	}

	@Override
	public String destroy(String arg0) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CDESTROYOFF,
				CrowbarConstant.state.CDESTROYOFF,
				CrowbarConstant.message.CDESTROYOFF);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asInfo(this.getClass().getName(), "Payload-Key",
			StringUtil.toString(payloadKey));
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		if (auditLogConfigOut != null) {
			auditLogConfigOut.reinitialized();
		}
		payloadKey.reinitialized();
		return BusRspUtil.asResponse(CrowbarConstant.state.INITIALIZATION_SUCCEED,
				toString());
	}

	@Override
	public String reinitialized(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPayloadKey(Lookup payloadKey) {
		this.payloadKey = payloadKey;
	}

	public void setAuditLogConfigOut(AuditLogConfig auditLogConfigOut) {
		this.auditLogConfigOut = auditLogConfigOut;
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

	@Override
	public GenericMessage<PostgresNotification> handle(
			GenericMessage<PostgresNotification> arg0) {
		// TODO Auto-generated method stub
		PostgresNotification result = arg0.getPayload();
		String auditPayload = result.toString();
		String payload = result.getPayload();
		DocumentContext context = JsonPath.parse(payload);
		result.setTableName((String) converter.express(context, tnameExp));

		if (StringUtil.isNullOrBlank(result.getTableName()))
			throw new InternalErrorRuntimeException(
					"INVALID MESSAGE, There's no " + tnameExp + " found on "
							+ PostgresNotification.class.getName()
							+ ".payload()");

		if (!payloadKey.containsKey(result.getTableName()))
			throw new InternalErrorRuntimeException("UNWANTED TABLE WATCH['"
					+ result.getTableName()
					+ "'], PLEASE CONTACT YOUR ADMINISTRATOR");

		String[] processed = new String[] { "", "" };

		if (arg0.getPayload().getChannelName()
				.equals(MessageConstant.Channel.ATERUPDATE))
			processed = afterUpdate(context, result.getTableName());
		else if (arg0.getPayload().getChannelName()
				.equals(MessageConstant.Channel.AFTERDELETE))
			processed = afterDelete(context, result.getTableName());
		else if (arg0.getPayload().getChannelName()
				.equals(MessageConstant.Channel.AFTERINSERT))
			processed = afterInsert(context, result.getTableName());

		result.setPayload(processed[0]);

		if (auditLogConfigOut.getConfig() == 1) {
			AuditLog auditLog = new AuditLog(arg0);
			auditLog.setPayload(auditPayload);
			auditLog.setAuditType(auditLogConfigOut.getType());

			auditLog.setAuditParam(result.getChannelName() + "_on_"
					+ result.getTableName());
			auditLog.setCorrelationId(processed[1]);

			auditLog.setEndpoint((arg0.getHeaders()
					.get(MessageConstants.Headers.XeaiCores)));
			auditLogDao.audit(auditLog);
		}
		return new GenericMessage<PostgresNotification>(result,
				arg0.getHeaders());
	}

	@SuppressWarnings("unchecked")
	private String[] afterUpdate(DocumentContext updateDt, String tableName) {

		Map<String, Object> oldData = (Map<String, Object>) converter.express(
				updateDt, oldDtExp);
		Map<String, Object> newData = (Map<String, Object>) converter.express(
				updateDt, newDtExp);
		for (String key : oldData.keySet()) {
			if (Objects.equal(oldData.get(key), newData.get(key))) {
				newData.remove(key);
			}
		}

		String[] keys = StringUtil.toArray(payloadKey.get(tableName),
				StringUtil.RegX.comma);
		String corrId = "";
		for (String key : keys) {
			newData.put(key, oldData.get(key));
			corrId += oldData.get(key) + " ";
		}
		corrId = corrId.trim().replace(" ", "-");
		jsonObject = new JSONObject();
		jsonObject.putAll(newData);
		return new String[] { jsonObject.toJSONString(), corrId };
	}

	@SuppressWarnings("unchecked")
	private String[] afterDelete(DocumentContext deleteDt, String tableName) {
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		String[] keys = StringUtil.toArray(payloadKey.get(tableName),
				StringUtil.RegX.comma);
		Map<String, Object> oldData = (Map<String, Object>) converter.express(
				deleteDt, oldDtExp);
		String corrId = "";
		for (String key : keys) {
			newMap.put(key, oldData.get(key));
			corrId += oldData.get(key) + " ";
		}
		corrId = corrId.trim().replace(" ", "-");
		jsonObject = new JSONObject();
		jsonObject.putAll(oldData);
		return new String[] { jsonObject.toJSONString(), corrId };
	}

	@SuppressWarnings("unchecked")
	private String[] afterInsert(DocumentContext deleteDt, String tableName) {
		Map<String, Object> newData = (Map<String, Object>) converter.express(
				deleteDt, newDtExp);
		String[] keys = StringUtil.toArray(payloadKey.get(tableName),
				StringUtil.RegX.comma);
		String corrId = "";
		for (String key : keys) {
			corrId += newData.get(key) + " ";
		}
		corrId = corrId.trim().replace(" ", "-");
		jsonObject = new JSONObject();
		jsonObject.putAll(newData);
		return new String[] { jsonObject.toJSONString(), corrId };
	}
}
