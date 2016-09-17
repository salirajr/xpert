/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 19, 2015	12:44:18 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.control.bus;

import org.springframework.jmx.export.annotation.ManagedOperation;

/**
 * @note
 *
 */
public interface CrowbarBus {

	@ManagedOperation
	public boolean isinitialized();

	@ManagedOperation
	public String reinitialized();

	@ManagedOperation
	public String reinitialized(String operand);

	@ManagedOperation
	public String destroy();

	@ManagedOperation
	public String destroy(String operand);
	
	@ManagedOperation
	public String info();

}
