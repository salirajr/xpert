package com.xybase.ax.eai.archcomp.control.bus;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;

public interface BinderBus extends LevergearBus {

	@SuppressWarnings("rawtypes")
	@ManagedOperation
	public Message inject(String binderValue, String command);
	
	@ManagedOperation
	public String inject(String command);

	@ManagedOperation
	public String alter(String binderValue, String contextFile);

	@ManagedOperation
	public String start(String binderValue);

	@ManagedOperation
	public String stop(String binderValue);

	@ManagedOperation
	public String add(String binderValue, String contextFile);

	@ManagedOperation
	public String alterBase(String contextFile, boolean isBaseAltered);

	@ManagedOperation
	public String info(String binderValue);

	@ManagedOperation
	public String info();

	@ManagedOperation
	public String refresh();

	@ManagedOperation
	public String refresh(String binderValue);
}
