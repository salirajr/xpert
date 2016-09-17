/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 14, 2015	11:57:56 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.converter.util;

/**
 * @param <T>
 * @param <E>
 * @note
 *
 */
public interface Converter<T, E> {

	public T toContext(Object payload);
	
	public String toString(T payload);
	
	public E express(Object payload, String expression);

}
