/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	9:15:40 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.sender;

/**
 * @note
 *
 */
public interface Sender<T> {

	public T dispatch(T payload);

}
