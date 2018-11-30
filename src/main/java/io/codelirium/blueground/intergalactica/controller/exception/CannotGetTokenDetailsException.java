package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotGetTokenDetailsException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public final static String MESSAGE_CANNOT_GET_TOKEN_DETAILS = "Failed to get token details.";


	public CannotGetTokenDetailsException(final String message) {

		super(message);

	}
}
