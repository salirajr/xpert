package com.xybase.ax.eai.archcomp.xmb.receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;
import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao;
import com.xybase.ax.eai.archcomp.common.dao.SoleRetrieval;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.receiver.Receiver;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;
import com.xybase.ax.eai.archcomp.transformer.extract.ObjectExtractor;
import com.xybase.ax.eai.archcomp.xmb.common.UUIDGenerator;
import com.xybase.ax.eai.archcomp.xmb.util.XMBMessageUtil;
import com.xybase.xmb.XMBTextMessage;

public class XmbWsReceiver implements Receiver<String> {

	private final String beanName = "WsXmbSubDataPool";
	private final static Logger log = LogManager
			.getLogger(XmbEjbIReceiver.class);

	private final String soapResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><dispatch xmlns=\"urn:xmb\"><message><refId/></message></dispatch></soapenv:Body></soapenv:Envelope>";

	private MessageChannel defaultChannelOut;
	private AuditLogDao auditLogDao;
	private AuditLogConfig auditLogConfig;
	private ObjectExtractor extractors;
	private SoleRetrieval xeaiSequence;

	public XmbWsReceiver() {
		// TODO Auto-generated constructor stub
		this.auditLogConfig = new AuditLogConfig();
		this.extractors = new ObjectExtractor();
	}

	public void setDefaultChannelOut(MessageChannel defaultChannelOut) {
		this.defaultChannelOut = defaultChannelOut;
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

	public void setAuditLogConfig(AuditLogConfig auditLogConfig) {
		this.auditLogConfig = auditLogConfig;
	}

	public void setXeaiSequence(SoleRetrieval xeaiSequence) {
		this.xeaiSequence = xeaiSequence;
	}

	@Override
	public String invoke(String soapRequest) {
		// TODO Auto-generated method stub
		Extractor<Object> oe = this.extractors.clones();
		XMBTextMessage xmbMessage = new XMBTextMessage();
		try {
			xmbMessage = XMBMessageUtil.fromSoap(soapRequest);
		} catch (IOException | SOAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<String, Object> header = new HashMap<String, Object>();
		header.put(MessageConstants.IntegrationID, xeaiSequence.retrieve());
		header.put(MessageConstants.Headers.XeaiEventID,
				xmbMessage.getMsgCode());
		xmbMessage.setRefId(UUIDGenerator.generate());
		GenericMessage<XMBTextMessage> messageOut = new GenericMessage<XMBTextMessage>(
				xmbMessage, header);
		oe.setContext(messageOut);
		AuditLog log = new AuditLog(messageOut);
		log.setPayload(soapRequest.replaceAll("<refId/>", "<refId>"
				+ xmbMessage.getRefId() + "</refId>"));
		log.setEndpoint("3.2.2.XEAI.2|" + beanName);
		log.setCorrelationId(StringUtil.cast(oe.extract(auditLogConfig
				.getCorrelation())));
		log.setAuditParam(StringUtil.cast(oe.extract(auditLogConfig
				.getParameterized())));
		log.setAuditType(auditLogConfig.getType());
		String response;
		try {
			this.defaultChannelOut.send(messageOut);
			response = soapResponse.replaceAll("<refId/>", "<refId>"
					+ xmbMessage.getRefId() + "</refId>");
			if (auditLogConfig.getConfig() == 1)
				auditLogDao.audit(log);
		} catch (Exception e) {
			xmbMessage.setRefId(StringUtil.emptyChar);
			log.setSeverity(e.getCause().toString());
			response = soapResponse.replaceAll("<message><refId/></message>",
					"<message>" + e.getCause().toString() + "</message>");
			auditLogDao.audit(log);
		}
		return response;
	}
}
