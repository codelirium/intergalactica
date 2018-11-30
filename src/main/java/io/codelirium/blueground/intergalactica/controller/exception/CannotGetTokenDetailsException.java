package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotGetTokenDetailsException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public final static String MESSAGE_INVALID_OR_MISSING_TOKEN = "Invalid or missing token.";


	public CannotGetTokenDetailsException(final String message) {

		super(message);

	}
}
