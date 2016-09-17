/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Aug 25, 2015		9:10:19 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.matrix.dao;

import java.util.Map;

import com.xybase.ax.eai.archcomp.matrix.Matrix;

public interface MatrixDao{
	
	public Matrix get(String key, String... parameters);
	
	public Map<String, Map<String, String>> getRaw(String key, String... parameters);

}
