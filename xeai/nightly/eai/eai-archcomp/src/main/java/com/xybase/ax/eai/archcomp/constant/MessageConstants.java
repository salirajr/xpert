/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.constant;

public class MessageConstants {

	public static final String CorrelationId = "CorrelationID";
	public static final String DestinationName = "DestinationName";
	public static final String MessageType = "MessageType";
	public static final String EventName = "EventName";
	public static final String ID = "ID";
	public static final String Serverity = "Serverity";
	public static final String UniqueKey = "UniqueKey";
	public static final String IntegrationID = "IntegrationID";
	public static final String Timestamp = "Timestamp";
	public static final String Version = "Version";
	public static final String OriginSystem = "OriginSystem";
	public static final String Payload = "Payload";
	public static final String Endpoint = "Endpoint";
	public static final String ErrorID = "ErrorID";

	public class Headers {
		public static final String ContentType = "Content-Type";
		public static final String XeaiEventID = "XeaiEventID";
		public static final String XeaiAuditType = "XeaiAuditType";
		public static final String XeaiCores = "XeaiCores";
		public static final String XeaiVariables = "XeaiVars";
		public static final String XeaiServiceName = "XeaiServiceName";
		public static final String XeaiMethod = "XeaiMethod";
		public static final String XeaiIntegrationType = "XeaiIntegrationType";
		public static final String XeaiErrorMessage = "XeaiErrorMessage";

	}

	public class Common {
		public static final String timestamp = "timestamp";
	}

	public static final String[] ReferredConstant = new String[] {
			CorrelationId, EventName, ID, IntegrationID, Timestamp, UniqueKey,
			ErrorID, Headers.XeaiEventID, Headers.XeaiServiceName,
			Headers.XeaiIntegrationType, Headers.XeaiErrorMessage,
			Common.timestamp };
}
