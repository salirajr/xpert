package com.xybase.ax.eai.archcomp.handler.audit;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;
import com.xybase.ax.eai.archcomp.message.converter.util.Converter;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;

public interface AuditHandler {

	public void audit(Message<?> messageIn);

	public void setChannelOut(MessageChannel out);

	@SuppressWarnings("rawtypes")
	public void setExtractor(Extractor extractor);

	public void setAuditLogConfig(AuditLogConfig logConfig);

	@SuppressWarnings("rawtypes")
	public void setConverter(Converter converter);

}
