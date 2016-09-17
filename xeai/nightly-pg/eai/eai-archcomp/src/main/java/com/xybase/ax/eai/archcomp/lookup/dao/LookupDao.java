/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.lookup.dao;


import java.util.Map;

import com.xybase.ax.eai.archcomp.lookup.Lookup;

public interface LookupDao{
	
	
	public Lookup get(String... parameters);
	
	@SuppressWarnings("rawtypes")
	public Map getRaw(String... parameters);

}
