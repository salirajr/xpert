package com.xybase.ax.eai.archcomp.servicebinder.executor;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.messaging.Message;

import com.xybase.ax.eai.archcomp.control.bus.ExecutorBus;

public interface IExecutor extends ExecutorBus {

	public void setBaseContext(AbstractApplicationContext parents);

	public void setContext(String... contextLocation);

	@SuppressWarnings("rawtypes")
	public void execute(Message message);
	
	public String refresh(AbstractApplicationContext parents);
}
