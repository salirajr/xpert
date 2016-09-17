/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	9:12:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.receiver;

/**
 * @note
 *
 */
public interface Receiver<T> {

	public T invoke(T payload);
	
}
