/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;


public abstract class AbstractInternalErrorRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4753328703650161268L;

	public AbstractInternalErrorRuntimeException(final String message) {
		super(message);
	}

	public AbstractInternalErrorRuntimeException() {
		super();
	}

	public AbstractInternalErrorRuntimeException(final Exception cause) {
		super(cause);
	}

	public AbstractInternalErrorRuntimeException(final String message,
			final Exception cause) {
		super(message, cause);
	}

	public AbstractInternalErrorRuntimeException(final String message,
			final Exception cause, final String errorCode) {
		super(message, cause);
	}

}
