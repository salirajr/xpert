package com.xybase.ax.eai.archcomp.servicebinder;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.jmx.export.annotation.ManagedOperation;

import com.xybase.ax.eai.archcomp.control.bus.BinderBus;
import com.xybase.ax.eai.archcomp.larik.Larik;
import com.xybase.ax.eai.archcomp.lookup.Lookup;

public interface ServiceBinder extends BinderBus, DisposableBean {

	@ManagedOperation
	public void setBinderKey(String headerKey);

	@ManagedOperation
	public void setDefaultBinder(String binderKey);

	public void setBaseCtxResources(Larik baseContextResource);

	public void setLCtxResources(Lookup contextResource);

}
