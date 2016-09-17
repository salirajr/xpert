/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	8:48:57 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.selector;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.core.MessageSelector;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.ErrorConstants;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.selector.Selector;
import com.xybase.ax.eai.archcomp.selector.dao.SelectorDao;

/**
 * @note
 *
 */
public class MessageSelectorImpl implements MessageSelector, CrowbarBus,
		LevergearBus {

	private List<Selector> selectors;
	private ExpressionParser parser;
	private StandardEvaluationContext context;
	private Object[] variables;

	private final static Logger log = LogManager
			.getLogger(MessageSelectorImpl.class);

	private SelectorDao iSelectorDao;

	private boolean isAlive = true;

	public MessageSelectorImpl() {
		// TODO Auto-generated constructor stub
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
	}

	public MessageSelectorImpl(Object... objects) {
		parser = new SpelExpressionParser();

		context = new StandardEvaluationContext();
		for (Object object : objects) {
			context.setVariable(object.toString(), object);
		}
		this.variables = objects;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.integration.core.MessageSelector#accept(org.
	 * springframework.messaging.Message)
	 */
	@Override
	public boolean accept(Message<?> arg0) {
		if (!isAlive)
			return true;
		context.setRootObject(arg0);
		boolean isValid = true;
		int n = selectors.size(), i = 0;
		String discardMessage = "";
		
		do {
			try {
				if(selectors.get(i).getSequence()==35){
					System.out.println("Message Selector =============== >> "+parser.parseExpression(
							"#stringUtil.toInteger(payload.text.length())").getValue(context,
									Object.class));
//					%#stringUtil.toInteger(#xmbLength.get(payload.msgCode)))==0)
				}
				isValid = parser.parseExpression(
						selectors.get(i).getExpression()).getValue(context,
						Boolean.class);
				discardMessage = selectors.get(i).getDiscardMessage();
			} catch (SpelEvaluationException e) {
				throw new InternalErrorRuntimeException("[expression#"
						+ selectors.get(i).getId() + ""
						+ selectors.get(i).getSequence() + ":"
						+ selectors.get(i).getExpression() + "]", e,
						ErrorConstants.Code.SELECTOR_EXCEPTION);
			} catch (Exception e) {
				throw new InternalErrorRuntimeException(e);
			}
		} while (++i < n && isValid);
		if (!isValid)
			throw new InternalErrorRuntimeException(discardMessage,
					ErrorConstants.Code.SELECTOR_EXCEPTION);
		return isValid;
	}

	@ManagedOperation
	public Object evaluate(String expression) {
		log.info("evaluate(" + expression + ")");
		return parser.parseExpression(expression).getValue(context,
				Object.class);
	}

	public void setSelectors(List<Selector> selectors) {
		this.selectors = selectors;
	}

	public void setSelectorDao(SelectorDao dao) {
		this.iSelectorDao = dao;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		int id = selectors.get(0).getId();
		selectors = iSelectorDao.get(id);
		parser = new SpelExpressionParser();
		CrowbarBus lookup;
		context = new StandardEvaluationContext();
		for (Object object : variables) {
			if (object instanceof CrowbarBus) {
				lookup = (CrowbarBus) object;
				lookup.reinitialized();
				context.setVariable(lookup.toString(), lookup);
			} else {
				context.setVariable(object.toString(), object);
			}
		}
		return BusRspUtil.asResponse(CrowbarConstant.code.INITIALIZATION_SUCCEED,
				CrowbarConstant.state.INITIALIZATION_SUCCEED, this.getClass()
						.getName() + " is refreshed!");
	}

	@Override
	public String reinitialized(String operand) {
		String response = "";
		// TODO Auto-generated method stub
		switch (operand) {

		case "selectors":
			int id = selectors.get(0).getId();
			selectors = iSelectorDao.get(id);
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Selector rules is refreshed!");
			break;
		case "variables":
			CrowbarBus lookup;
			for (Object object : variables) {
				if (object instanceof CrowbarBus) {
					lookup = (CrowbarBus) object;
					lookup.reinitialized();
					context.setVariable(lookup.toString(), lookup);
				} else {
					context.setVariable(object.toString(), object);
				}
			}
			response = BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Lookup variables is refreshed!");
			break;
		default:
			response = BusRspUtil.asResponse(CrowbarConstant.code.DESCRIBEINFO,
					CrowbarConstant.state.CREINITIALIZATIONP, StringUtil
							.constraints("selectors",
									StringUtil.RegX.doubleQuotes), StringUtil
							.constraints("variables",
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
						"Subtle as a filter logics, combining of SPeL and consequence of logics validation",
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
