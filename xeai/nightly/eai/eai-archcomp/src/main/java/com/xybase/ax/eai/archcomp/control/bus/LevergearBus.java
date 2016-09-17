/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 19, 2015	12:39:47 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.control.bus;

import org.springframework.jmx.export.annotation.ManagedOperation;

/**
 * @note
 *
 */
public interface LevergearBus {

	@ManagedOperation
	public String stop();

	@ManagedOperation
	public String start();
	
	@ManagedOperation
	public boolean isAlive();
	
	@ManagedOperation
	public String info();
}
