/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 24092014		Jovi Rengga Salira		Initial Creation,
 * 23102014		Jovi Rengga Salira		Variety of Application Runtime exception
 * **************************************************************************************
 */
package com.concept.ruleengine.archcomp.exception;

public class AbstractApplicationRuntimeException extends
		AbstractRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
