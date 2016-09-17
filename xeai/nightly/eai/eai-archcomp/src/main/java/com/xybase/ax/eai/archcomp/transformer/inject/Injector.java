/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * Nov 17, 2015	4:37:19 PM	    Abdul Azis Nur			update  :
 * 															- update method inject(Object value, String rule) , change String 'value' to Object 'value'
 *
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.inject;

import java.util.Map;

import com.xybase.ax.eai.archcomp.message.converter.util.Converter;

public interface Injector<T> {

	public void inject(Object value, String rule);

	public void setTemplate(String template);
	
	public void setTemplate(T context);

	public T finalized();

	public Map<String, String> getVariables();

	public void setVariables(Map<String, String> variables);

	@SuppressWarnings("rawtypes")
	public Converter getConverter();

	public Injector<T> clones();
}
