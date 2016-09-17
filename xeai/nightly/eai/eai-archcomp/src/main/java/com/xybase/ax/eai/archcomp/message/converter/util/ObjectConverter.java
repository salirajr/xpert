/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Jun 6, 2015	1:35:00 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.message.converter.util;

/**
 * @param <T>
 * @note
 *
 */
public class ObjectConverter implements Converter<Object, Object> {

	/*
	 * Nothing to here!! (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.IConverter#toContext
	 * (java.lang.Object)
	 */
	@Override
	public Object toContext(Object payload) {
		// TODO Auto-generated method stub
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.message.converter.util.IConverter#toString
	 * (java.lang.Object)
	 */
	@Override
	public String toString(Object payload) {
		// TODO Auto-generated method stub
		return payload.toString();
	}

	@Override
	public Object express(Object payload, String expression) {
		// TODO Auto-generated method stub
		return payload;
	}

}
