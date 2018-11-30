package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotValidateTokenException extends RuntimeException {

	private static final long serialVersionUID = -4121399461330213666L;


	public final static String MESSAGE_INVALID_OR_MISSING_TOKEN = "Invalid or missing token.";


	public CannotValidateTokenException(final String message) {

		super(message);

	}
}
