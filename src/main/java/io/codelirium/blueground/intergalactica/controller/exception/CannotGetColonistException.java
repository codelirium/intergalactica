package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotGetColonistException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public final static String MESSAGE_CANNOT_GET_COLONIST = "Failed to get colonist details.";


	public CannotGetColonistException(final String message) {

		super(message);

	}
}
