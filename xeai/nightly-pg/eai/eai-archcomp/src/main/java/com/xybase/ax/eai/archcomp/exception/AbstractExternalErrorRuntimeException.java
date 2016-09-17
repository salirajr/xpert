/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;


public class AbstractExternalErrorRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5382578803027567422L;

	public AbstractExternalErrorRuntimeException(final String message) {
		super(message);
	}

	public AbstractExternalErrorRuntimeException() {
		super();
	}

	public AbstractExternalErrorRuntimeException(final Exception cause) {
		super(cause);
	}

	public AbstractExternalErrorRuntimeException(final String message,
			final Exception cause) {
		super(message, cause);
	}

}
