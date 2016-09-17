/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * 04-01-2015   ----            Abdul Azis Nur			add InternalErrorRuntimeException without Exception cause
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;

import com.xybase.ax.eai.archcomp.constant.ErrorConstants;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;

public class InternalErrorRuntimeException extends
		AbstractInternalErrorRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1668986607425353401L;

	public InternalErrorRuntimeException(final String message) {
		super(InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + message);
	}

	public InternalErrorRuntimeException() {
		super();
	}

	public InternalErrorRuntimeException(final Exception cause) {
		super(ErrorConstants.Message.UNKNOWN_EXCEPTION_MSG
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE
				+ ErrorConstants.Code.UNKNOWN_EXCEPTION + "], Caused by " + cause);
	}

	public InternalErrorRuntimeException(final String message,
			final Exception cause) {
		super(message + InternalConstant.XEAI_ERROR_MESSAGE_PREFACE
				+ ErrorConstants.Code.UNKNOWN_EXCEPTION + "], Caused by " + cause);
	}

	public InternalErrorRuntimeException(final String message,
			final Exception cause, final String errorCode) {
		super("\"" + message + "\""
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + errorCode
				+ "], Caused by " + cause, null, errorCode);

	}
	
	public InternalErrorRuntimeException(final String message, final String errorCode) {
		super("\"" + message + "\""
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + errorCode
				+ "]");

	}
}
