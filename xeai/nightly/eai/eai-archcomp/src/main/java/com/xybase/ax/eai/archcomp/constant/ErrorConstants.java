/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.constant;

public class ErrorConstants {

	public class Code {
		public static final String UNKNOWN_EXCEPTION = "1000";
		public static final String UNKNOWN_EVENTS = "1000";
		public static final String MESSAGEHANDLER_EXCEPTION = "1100";
		public static final String TRANSFORMATION_EXCEPTION = "1101";

		public static final String SELECTOR_EXCEPTION = "2000";
	}

	public class Message {
		public static final String UNKNOWN_EXCEPTION_MSG = "\"Unknown Expception!!\"";
		public static final String XEAI_ERROR_MESSAGE_XMB_FILTER = "Error Filter Message";
	}

	public class State {
		public static final String UNKNOWN_EVENTS = "UNKNOWN_EVENTS";
		public static final String NO_EVENTS = "NO_EVENTS";
	}

}
