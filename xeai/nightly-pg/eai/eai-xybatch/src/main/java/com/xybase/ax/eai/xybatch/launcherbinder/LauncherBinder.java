/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 20, 2016		4:18:03 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcherbinder;

import org.springframework.jmx.export.annotation.ManagedOperation;

/**
 * @author salirajr
 *
 */
public interface LauncherBinder {

	@ManagedOperation
	public long execute();

	@ManagedOperation
	public long remove(String key);
	
	@ManagedOperation
	public long remove();

}
