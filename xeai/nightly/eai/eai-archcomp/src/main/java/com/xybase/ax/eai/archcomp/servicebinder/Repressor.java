package com.xybase.ax.eai.archcomp.servicebinder;

import org.springframework.messaging.Message;

import com.xybase.ax.eai.archcomp.larik.Larik;

public interface Repressor<T>{
	
	public void  repress(Message<T> message);
	
	public void setBaseCtxResources(Larik baseContextResource);
}
	