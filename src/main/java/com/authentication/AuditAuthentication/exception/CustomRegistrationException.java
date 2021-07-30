package com.authentication.AuditAuthentication.exception;

/**
 * This class is used for handling exceptions pertaining to registration
 *
 */
public class CustomRegistrationException extends RuntimeException {

	public CustomRegistrationException(String message) {
		super(message);
	}

	public CustomRegistrationException(Throwable cause) {
		super(cause);
	}

	public CustomRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomRegistrationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}