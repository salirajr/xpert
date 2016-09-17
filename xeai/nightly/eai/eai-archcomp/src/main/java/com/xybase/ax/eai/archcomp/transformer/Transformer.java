/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 12, 2015	4:13:59 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer;

import java.util.Map;

import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.message.XMessage;

/**
 * @param <T>
 * @note
 *
 */
public interface Transformer<T> extends CrowbarBus {

	public class IndexIdentifier {
		public static String ENDPOINTER = "END_POINTER";
		public static String PARENT = "PARENT";
		public static String STARTINDEX = "START_INDEX";
	}


	public void setPayload(T payload);

	public XMessage<T> transform();

	public void setVariables(Map<String, String> variables);
	
	public void setContext(Object context);

}
