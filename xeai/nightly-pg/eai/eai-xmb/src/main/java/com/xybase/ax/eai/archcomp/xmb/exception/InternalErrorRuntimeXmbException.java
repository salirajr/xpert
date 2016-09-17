/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.xmb.exception;

import com.xybase.ax.eai.archcomp.constant.ErrorConstants;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;

public class InternalErrorRuntimeXmbException extends
		InternalErrorRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalErrorRuntimeXmbException(final String message) {
		super(InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + message);
	}

	public InternalErrorRuntimeXmbException() {
		super();
	}

	public InternalErrorRuntimeXmbException(final Exception cause) {
		super(ErrorConstants.Message.UNKNOWN_EXCEPTION_MSG
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE
				+ ErrorConstants.Code.UNKNOWN_EXCEPTION + "], Caused by "
				+ cause);
	}

	public InternalErrorRuntimeXmbException(final String message,
			final Exception cause) {
		super(message + InternalConstant.XEAI_ERROR_MESSAGE_PREFACE
				+ ErrorConstants.Code.UNKNOWN_EXCEPTION + "], Caused by "
				+ cause);
	}

	public InternalErrorRuntimeXmbException(final String message,
			final Exception cause, final String errorCode) {
		super("\"" + message + "\""
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + errorCode
				+ "], Caused by " + cause, null, errorCode);

	}

	public InternalErrorRuntimeXmbException(final String message,
			final String errorCode) {
		super("\"" + message + "\""
				+ InternalConstant.XEAI_ERROR_MESSAGE_PREFACE + errorCode + "]");

	}

}
