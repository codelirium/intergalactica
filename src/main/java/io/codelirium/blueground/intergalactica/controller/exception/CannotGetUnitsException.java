package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotGetUnitsException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public final static String MESSAGE_INVALID_OR_MISSING_TOKEN = "Invalid or missing token.";


	public CannotGetUnitsException(final String message) {

		super(message);

	}

	public CannotGetUnitsException(final String message, final Throwable cause) {

		super(message, cause);

	}
}
