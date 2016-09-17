/** 
 * Modification History
 * Date				Time				Modified By             Comments
 * **************************************************************************************
 * Mar 1, 2015		10:18:43 AM			Jovi Rengga Salira		Initial Creation
 * Mar 21, 2015		10:18:43 AM			Jovi Rengga Salira		Add Variables inside it.
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.extract;

import java.util.Iterator;
import java.util.Map;

import com.xybase.ax.eai.archcomp.message.converter.util.Converter;

public interface Extractor<T> {

	public Object extract(String rule);

	public int count(String rule);

	public Iterator<String> iterate(String rule);

	public void setContext(T context);
	
	public T getContext();

	public Map<String, String> getVariables();

	public void setVariables(Map<String, String> variables);

	@SuppressWarnings("rawtypes")
	public Converter getConverter();

	public Extractor<T> clones();
}
