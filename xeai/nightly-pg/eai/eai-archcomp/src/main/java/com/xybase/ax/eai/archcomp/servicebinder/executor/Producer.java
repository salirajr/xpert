package com.xybase.ax.eai.archcomp.servicebinder.executor;

import org.springframework.context.support.AbstractApplicationContext;

import com.xybase.ax.eai.archcomp.control.bus.ExecutorBus;

public interface Producer extends ExecutorBus {
	
	public void setBaseContext(AbstractApplicationContext parents);

	public void setContext(String... contextLocation);
	
	public String refresh(AbstractApplicationContext parents);
	
}
