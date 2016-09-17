package com.xybase.ax.eai.archcomp.servicebinder.executor;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.constant.ServiceBinderConstant;
import com.xybase.ax.eai.archcomp.control.bus.constant.BinderConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;

public class ProducerImpl implements Producer {

	private final static Logger log = LogManager.getLogger(ProducerImpl.class);

	private AbstractApplicationContext baseContext;
	private String[] contextLocation;

	private MessageChannel controlIn;
	private PollableChannel controlOut;

	public ProducerImpl() {

	}

	public ProducerImpl(AbstractApplicationContext parent,
			String... contextLocation) {
		// TODO Auto-generated constructor stub
		this.setBaseContext(parent);
		this.setContext(contextLocation);
	}

	@Override
	public void setBaseContext(AbstractApplicationContext parents) {
		// TODO Auto-generated method stub
		this.baseContext = parents;
	}

	@Override
	public void setContext(String... contextLocation) {
		// TODO Auto-generated method stub
		this.contextLocation = contextLocation;
		try {

			if (!(baseContext.isActive() && baseContext.isRunning()))
				baseContext.refresh();

			baseContext = new ClassPathXmlApplicationContext(
					this.contextLocation, true, baseContext);

			controlIn = baseContext.getBean(
					ServiceBinderConstant.CONTROLBUS_CHANNELIN,
					MessageChannel.class);

			controlOut = baseContext.getBean(
					ServiceBinderConstant.CONTROLBUS_CHANNELOUT,
					PollableChannel.class);

		} catch (Exception e) {
			log.error("Context Producer['" + Arrays.toString(contextLocation)
					+ "'] failed to start!, cause: " + e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Message inject(String command) {
		// TODO Auto-generated method stub
		log.info("inject(" + command + ")");
		controlIn.send(new GenericMessage<String>(command));
		return controlOut.receive();
	}

	@Override
	public String alter(String contextFile) {
		// TODO Auto-generated method stub
		setContext(contextFile);
		return BusRspUtil.asResponse(BinderConstant.code.ALTERED_SUCCESS,
				BinderConstant.state.ALTERED,
				BinderConstant.message.ALTERED_SUCCESS);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asInfo(this.getClass().getSimpleName(),
				"List of bean registered.",
				baseContext.getBeanDefinitionNames());
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		if (baseContext != null
				&& (baseContext.isActive() || baseContext.isRunning()))
			baseContext.destroy();
		baseContext = null;
		return BusRspUtil.asResponse(BinderConstant.state.DESTROYED,
				BinderConstant.message.DESTROYED);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		return BusRspUtil
				.asResponse(BinderConstant.code.ERR_START,
						BinderConstant.state.ERR_START,
						BinderConstant.message.DISABLED);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return baseContext.isActive();
	}

	@Override
	public String refresh(AbstractApplicationContext parents) {
		// TODO Auto-generated method stub'
		stop();
		parents.refresh();
		setBaseContext(parents);
		setContext(this.contextLocation);
		return BusRspUtil.asResponse(BinderConstant.state.REFRESHED,
				BinderConstant.message.REFRESHED);
	}
}
