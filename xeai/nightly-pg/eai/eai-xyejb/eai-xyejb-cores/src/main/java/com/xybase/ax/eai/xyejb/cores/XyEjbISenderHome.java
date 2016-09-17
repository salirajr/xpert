/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 22, 2015	9:56:03 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xyejb.cores;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.xybase.ax.eai.xyejb.api.XyEjbISender;
import com.xybase.ax.eai.xyejb.api.model.XyEjbMessage;

/**
 * @note
 *
 */
@Stateless
@Remote(XyEjbISender.class)
public class XyEjbISenderHome implements XyEjbISender {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.xyejb.api.XyEjbISender#dispatch(com.xybase.ax.eai.xyejb
	 * .api.model.XyEjbMessage)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public XyEjbMessage dispatch(XyEjbMessage payload) {
		System.out.println(payload.getPayload());
		return null;
	}

}
