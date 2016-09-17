/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 2, 2015	4:46:12 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.sender;

/**
 * @param <T>
 * @note
 *
 */
public interface ISender<T> {

	public void dispatch(T payload);
}
