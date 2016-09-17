/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	10:45:57 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.stub.dao;

import com.xybase.ax.eai.xystub.stub.Stub;

/**
 * @note
 *
 */
public interface StubDao {
	
	public void setQuery(String query);
	
	public Stub lookup(String requestIn);

}
