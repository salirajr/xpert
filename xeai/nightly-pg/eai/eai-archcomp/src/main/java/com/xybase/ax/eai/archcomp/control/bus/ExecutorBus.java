package com.xybase.ax.eai.archcomp.control.bus;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;

public interface ExecutorBus extends LevergearBus {
	
	@SuppressWarnings("rawtypes")
	@ManagedOperation
	public Message inject(String command);

	@ManagedOperation
	public String alter(String contextFile);

	@ManagedOperation
	public String info();

}
