/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 19, 2016		5:29:12 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcher;

import org.springframework.batch.core.BatchStatus;

/**
 * @author salirajr
 *
 */
public interface Launcher {

	public BatchStatus execute();

	public void stop();

}
