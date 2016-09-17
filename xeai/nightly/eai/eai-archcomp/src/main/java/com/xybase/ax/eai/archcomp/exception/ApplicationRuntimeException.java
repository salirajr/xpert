/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;

public class ApplicationRuntimeException extends
		AbstractInternalErrorRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218926759072324027L;

	public ApplicationRuntimeException(final String message) {
		super(message);
	}

	public ApplicationRuntimeException() {
		super();
	}

	public ApplicationRuntimeException(final Exception cause) {
		super(cause);
	}

	public ApplicationRuntimeException(final String message,
			final Exception cause) {
		super(message, cause);
	}

}
