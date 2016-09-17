/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 22, 2015	2:51:31 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xyejb.api;

import com.xybase.ax.eai.xyejb.api.model.XyEjbMessage;

/**
 * @note
 *
 */
@SuppressWarnings("rawtypes")
public interface XyEjbISender {

	public XyEjbMessage dispatch(XyEjbMessage payload);

}
