/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 14, 2015	11:28:04 AM			Jovi Rengga Salira		Initial Creation
 * Mar 20, 2015	12:28:04 AM			Jovi Rengga Salira		Add Transformation logic
 * 		- table: xeai_transform, xeai_transform_rule
 * 		- logic: implements by interface Extractor, Injector able to implement in any perusal.
 * 				 utilized JXPathContext, jayway.json library to create notation for transformation.
 * Mar 20, 2015	20:28:04 AM			Jovi Rengga Salira		Add Logging
 * 		- table: xeai_audit_log, xeai_audit_log_config
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;
import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.message.XMessage;
import com.xybase.ax.eai.archcomp.transformer.TransformerImpl;

/**
 * @note
 *
 */

@SuppressWarnings("rawtypes")
public class TransformHandler implements Handler {

	protected TransformerImpl transformer;

	private AuditLogDao auditLogDao;

	private AuditLogConfig auditLogConfigIn, auditLogConfigOut;

	private Map<String, String> referredConstant;

	@SuppressWarnings("unused")
	private GenericMessage messageOut;

	private AuditLog auditLog;

	private boolean isActivated = true;
	private boolean payloadCycles;

	public TransformHandler() {
		super();
		payloadCycles = false;
		auditLogConfigIn = new AuditLogConfig();
		auditLogConfigOut = new AuditLogConfig();
	}

	public void setTransformer(TransformerImpl itransform) {
		this.transformer = itransform;
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

	public void setAuditLogConfigIn(AuditLogConfig auditLogConfigIn) {
		this.auditLogConfigIn = auditLogConfigIn;
	}

	public void setAuditLogConfigOut(AuditLogConfig auditLogConfigOut) {
		this.auditLogConfigOut = auditLogConfigOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.handler.IHandler#handle(org.springframework
	 * .messaging.support.GenericMessage)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GenericMessage handle(GenericMessage messageIn) {
		// TODO Auto-generated method stub
		transformer.setContext(messageIn.getPayload());
		transformer.setPayloadCycles(this.payloadCycles);

		if (!isActivated)
			auditLog.setSeverity(InternalConstant.XEAI_SERVERITY_CONTROLLEDBYPASS);

		if (auditLogConfigIn.getConfig() == 1) {
			auditLog = new AuditLog(messageIn);
			auditLog.setAuditType(auditLogConfigIn.getType());
			auditLog.setAuditTime(DateUtil.getTimestamp());
			if (!StringUtil.isNullOrBlank(auditLogConfigOut.getParameterized()))
				auditLog.setAuditParam(transformer.getExtractor().extract(
						auditLogConfigIn.getParameterized()));
			if (!StringUtil.isNullOrBlank(auditLogConfigOut.getCorrelation()))
				auditLog.setCorrelationId(transformer.getExtractor().extract(
						auditLogConfigIn.getCorrelation()));
			if (messageIn.getHeaders().containsKey(
					MessageConstants.Headers.XeaiCores))
				auditLog.setEndpoint(messageIn.getHeaders().get(
						MessageConstants.Headers.XeaiCores));
			auditLog.setPayload(transformer.getExtractor().getConverter()
					.toString(messageIn.getPayload()));
			auditLogDao.audit(auditLog);
		}

		if (!isActivated)
			return messageIn;

		if (messageIn.getHeaders().containsKey(
				MessageConstants.Headers.XeaiVariables)
				&& !StringUtil.isNullOrBlank(messageIn.getHeaders().get(
						MessageConstants.Headers.XeaiVariables))) {
			String temp = StringUtil.cast(messageIn.getHeaders().get(
					MessageConstants.Headers.XeaiVariables));

			referredConstant = StringUtil.toMap(temp);
			for (String constant : MessageConstants.ReferredConstant) {
				if (messageIn.getHeaders().containsKey(constant)) {
					referredConstant.put(constant, StringUtil.cast(messageIn
							.getHeaders().get(constant)));
				}
			}
			transformer.setVariables(referredConstant);
		} else {
			referredConstant = new HashMap<String, String>();
			for (String constant : MessageConstants.ReferredConstant) {
				if (messageIn.getHeaders().containsKey(constant)) {
					referredConstant.put(constant, StringUtil.cast(messageIn
							.getHeaders().get(constant)));
				}
			}
			transformer.setVariables(referredConstant);
		}

		XMessage result = transformer.transform();

		Object payloads = result.getPayload();
		// Next to do, inject variables to this!!
		Map<String, String> XeaiVars = new HashMap<String, String>();
		if (result.getVariables() != null)
			XeaiVars.put(MessageConstants.Headers.XeaiVariables,
					StringUtil.toString(result.getVariables()));

		GenericMessage messageOut = new GenericMessage(payloads, XeaiVars);
		// Logging for OUT-message!!
		if (auditLogConfigOut.getConfig() == 1) {
			if (auditLogConfigIn.getConfig() == 0) {
				auditLog = new AuditLog(messageIn);
				if (messageIn.getHeaders().containsKey(
						MessageConstants.Headers.XeaiCores))
					auditLog.setEndpoint(messageIn.getHeaders().get(
							MessageConstants.Headers.XeaiCores));
			}
			auditLog.setAuditType(auditLogConfigOut.getType());
			auditLog.setAuditTime(DateUtil.getTimestamp());
			if (!StringUtil.isNullOrBlank(auditLogConfigOut.getParameterized()))
				auditLog.setAuditParam(transformer.getExtractor().extract(
						auditLogConfigOut.getParameterized()));
			if (!StringUtil.isNullOrBlank(auditLogConfigOut.getCorrelation()))
				auditLog.setCorrelationId(transformer.getExtractor().extract(
						auditLogConfigOut.getCorrelation()));
			auditLog.setPayload(transformer.getInjector().getConverter()
					.toString(messageOut.getPayload()));
			auditLogDao.audit(auditLog);
		}
		return messageOut;
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		isActivated = false;
		return BusRspUtil.asResponse(LevergearConstant.state.STOP,
				LevergearConstant.info.PASSES_STOP);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		isActivated = true;
		return BusRspUtil.asResponse(LevergearConstant.state.START,
				LevergearConstant.info.STARTED);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return isActivated;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		auditLogConfigIn.reinitialized();
		auditLogConfigOut.reinitialized();
		transformer.reinitialized();
		return BusRspUtil.asResponse(CrowbarConstant.code.INITIALIZATION_SUCCEED,
				CrowbarConstant.state.INITIALIZATION_SUCCEED, this.getClass()
						.getName() + " is refreshed!");
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		String response = null;

		switch (operand) {
		case "log-config:in":
			auditLogConfigIn.reinitialized();
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Log-config:in is refreshed!");
			break;
		case "log-config:out":
			auditLogConfigOut.reinitialized();
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Log-config:out is refreshed!");
			break;
		case "transformer":
			transformer.reinitialized();
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"ITransform is refreshed!");
			break;
		case "transformer:rules":
			transformer.reinitialized("rule");
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"ITransform:rule is refreshed!");
			break;
		case "transformer:template":
			transformer.reinitialized("template");
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Transformer:template is refreshed!");
			break;
		default:
			response = BusRspUtil.asResponse(CrowbarConstant.code.DESCRIBEINFO,
					CrowbarConstant.state.CREINITIALIZATIONP, StringUtil
							.constraints("log-config:in",
									StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("log-config:out",
									StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("itransform",
									StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("itransform:rules",
									StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("itransform:template",
									StringUtil.RegX.doubleQuotes));
			break;
		}
		return response;
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
		return BusRspUtil
				.asInfo(this.getClass().getName(),
						"Subtle as a transformation logics, combining of Rule notation of Xml Path, Json Path and SPel as transformation rule!",
						StringUtil.constraints(CrowbarBus.class.getName(),
								StringUtil.RegX.doubleQuotes));
	}

	public void setPayloadCycles(boolean payloadCycles) {
		this.payloadCycles = payloadCycles;
	}

}
