package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotGenerateTokenException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public static final String MESSAGE_CANNOT_GENERATE_TOKEN = "The token cannot be generated.";


	public CannotGenerateTokenException(final String message) {

		super(message);

	}
}
