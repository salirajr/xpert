package com.xybase.ax.eai.xystub.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.handler.IHandler;

public class DefaultEchoIHandler<T, E> implements IHandler<T, E> {

	private final static Logger log = LogManager
			.getLogger(DefaultEchoIHandler.class);

	private int counters;

	public DefaultEchoIHandler() {
		// TODO Auto-generated constructor stub
		counters = 0;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATION,
				CrowbarConstant.state.CREINITIALIZATION,
				CrowbarConstant.message.CREINITIALIZATION);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void handle(GenericMessage messageIn) {
		// TODO Auto-generated method stub
		counters++;
		log.info("Message.headers['" + counters + "','"
				+ messageIn.getHeaders() + "']");
		log.info("Message.payload.class['" + counters + "','"
				+ messageIn.getPayload().getClass() + "']");
		log.info("Message.payload['" + counters + "','"
				+ messageIn.getPayload() + "']");
	}
}
