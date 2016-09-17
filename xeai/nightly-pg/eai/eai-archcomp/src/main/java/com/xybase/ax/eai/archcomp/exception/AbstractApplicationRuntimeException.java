/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.exception;


public class AbstractApplicationRuntimeException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 445802237594839870L;

	public AbstractApplicationRuntimeException(final String message) {
		super(message);
	}

	public AbstractApplicationRuntimeException() {
		super();
	}

	public AbstractApplicationRuntimeException(final Exception cause) {
		super(cause);
	}

	public AbstractApplicationRuntimeException(final String message,
			final Exception cause) {
		super(message, cause);
	}
	

}
