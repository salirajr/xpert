/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;


public class ExternalErrorRuntimeException extends
		AbstractExternalErrorRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3316468153287309949L;

	public ExternalErrorRuntimeException(final String message) {
		super(message);
	}

	public ExternalErrorRuntimeException() {
		super();
	}

	public ExternalErrorRuntimeException(final Exception cause) {
		super(cause);
	}

	public ExternalErrorRuntimeException(final String message,
			final Exception cause) {
		super(message, cause);
	}

}
