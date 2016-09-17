package com.xybase.ax.eai.archcomp.handler.audit;

import java.util.HashMap;

import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;
import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.message.converter.util.Converter;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;
import com.xybase.ax.eai.archcomp.transformer.util.SpELProcessor;

public class DefaultAuditHandler implements AuditHandler {

	private AuditLogConfig auditLogConfig;
	private AuditLogDao auditLogDao;
	private MessageChannel channelOut;
	@SuppressWarnings("rawtypes")
	private Extractor extractor;
	@SuppressWarnings("rawtypes")
	private Converter converter;
	private SpELProcessor processor;

	public DefaultAuditHandler() {
		// TODO Auto-generated constructor stub
		channelOut = null;
		extractor = null;
		converter = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void audit(Message<?> messageIn) {
		// TODO Auto-generated method stub

		AuditLog log = new AuditLog(messageIn);
		log.setAuditType(auditLogConfig.getType());
		log.setEndpoint(messageIn.getHeaders().containsKey(
				MessageConstants.Endpoint) ? StringUtil.cast(messageIn
				.getHeaders().get(MessageConstants.Endpoint)) : null);

		log.setXeaiId(messageIn.getHeaders().containsKey(
				MessageConstants.IntegrationID) ? StringUtil.cast(messageIn
				.getHeaders().get(MessageConstants.IntegrationID)) : null);

		log.setEventName(messageIn.getHeaders().containsKey(
				MessageConstants.Headers.XeaiEventID) ? StringUtil
				.cast(messageIn.getHeaders().get(
						MessageConstants.Headers.XeaiEventID)) : null);

		log.setSeverity(messageIn.getHeaders().containsKey(
				MessageConstants.Serverity) ? StringUtil.cast(messageIn
				.getHeaders().get(MessageConstants.Serverity)) : null);

		log.setAuditTime(messageIn.getHeaders().containsKey(
				MessageConstants.Timestamp) ? StringUtil.cast(messageIn
				.getHeaders().get(MessageConstants.Timestamp)) : log
				.getAuditTime());

		if (auditLogConfig.getConfig() == 1) {
			if (converter != null) {
				log.setPayload(converter.toString(messageIn.getPayload()));
			} else {
				log.setPayload(StringUtil.asString(messageIn.getPayload()));
			}
			processor = new SpELProcessor();
			if (extractor != null) {
				Extractor extract = extractor.clones();
				if (messageIn.getHeaders().containsKey(
						MessageConstants.Headers.XeaiVariables)) {
					String temp = StringUtil.cast(messageIn.getHeaders().get(
							MessageConstants.Headers.XeaiVariables));
					extract.setVariables(StringUtil.toMap(temp));
				} else {
					extract.setVariables(new HashMap<String, String>());
				}
				if (converter == null)
					extract.setContext(extract.getConverter().toContext(
							messageIn.getPayload()));
				else
					extract.setContext(messageIn.getPayload());
				processor.setRootObject(new GenericMessage(extract, messageIn
						.getHeaders()));
			} else {
				processor.setRootObject(messageIn);
			}
			if (!StringUtil.isNullOrBlank(auditLogConfig.getParameterized())) {
				try {
					log.setAuditParam(processor.express(auditLogConfig
							.getParameterized()));
				} catch (SpelEvaluationException e) {
					log.setAuditParam(e.getMessage());
				}

			}
			if (!StringUtil.isNullOrBlank(auditLogConfig.getCorrelation())) {
				try {
					log.setCorrelationId(processor.express(auditLogConfig
							.getCorrelation()));
				} catch (SpelEvaluationException e) {
					log.setCorrelationId(e.getMessage());
				}
			}

			auditLogDao.audit(log);
		}

		// Role as Message Bridge
		if (channelOut != null) {
			channelOut.send(messageIn);
		}
	}

	@Override
	public void setChannelOut(MessageChannel out) {
		// TODO Auto-generated method stub
		this.channelOut = out;
	}

	@Override
	public void setAuditLogConfig(AuditLogConfig logConfig) {
		// TODO Auto-generated method stub
		this.auditLogConfig = logConfig;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setExtractor(Extractor extractor) {
		// TODO Auto-generated method stub
		this.extractor = extractor;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setConverter(Converter converter) {
		// TODO Auto-generated method stub
		this.converter = converter;
	}

	public void setAuditLogDao(AuditLogDao dao) {
		this.auditLogDao = dao;
	}

}
