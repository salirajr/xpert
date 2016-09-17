/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	9:58:31 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.support.GenericMessage;



import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.MessageConstants;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.handler.Handler;
import com.xybase.ax.eai.xystub.stub.Stub;
import com.xybase.ax.eai.xystub.stub.dao.StubDaoImpl;

/**
 * @note
 *
 */
@SuppressWarnings("rawtypes")
public class DefaultStubHandler implements Handler {

	private final static Logger log = LogManager
			.getLogger(DefaultStubHandler.class);

	private StubDaoImpl stubDao;
	private String noResponseErrorMessage = "No Response Availabe for the Request!!";

	private boolean isAlive = true;

	public StubDaoImpl getStubDao() {
		return stubDao;
	}

	public void setStubDao(StubDaoImpl stubDao) {
		this.stubDao = stubDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.service.IServiceHandler#invoke(org.springframework
	 * .messaging.support.GenericMessage)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GenericMessage<String> handle(GenericMessage messageIn) {

		if (!isAlive) {
			return messageIn;
		}
		// TODO Auto-generated method stub
		String requestIn = (String) messageIn.getPayload();
		Map<String, Object> header = new HashMap<String, Object>();
		Stub stub = stubDao.lookup(requestIn);
		if (stub.getResponseOut() == null) {
			stub.setResponseOut(noResponseErrorMessage);
			log.info(noResponseErrorMessage);
		}
		header.put(MessageConstants.Headers.ContentType, stub.getType());
		GenericMessage<String> messageOut = new GenericMessage<String>(
				stub.getResponseOut(), header);

		return messageOut;
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
		return BusRspUtil
				.asInfo(this.getClass().getName(),
						"Subtle as a mock-up system, evaluate request message to return the response base on definition!",
						StringUtil.constraints(LevergearBus.class.getName(),
								StringUtil.RegX.doubleQuotes), StringUtil
								.constraints(CrowbarBus.class.getName(),
										StringUtil.RegX.doubleQuotes));
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		isAlive = false;
		return BusRspUtil.asResponse(LevergearConstant.state.STOP,
				LevergearConstant.info.PASSES_STOP);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		isAlive = true;
		return BusRspUtil.asResponse(LevergearConstant.state.START,
				LevergearConstant.info.STARTED);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return isAlive;
	}
}
