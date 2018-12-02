package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotSignUpColonistException extends RuntimeException {

	private static final long serialVersionUID = -6141236785008068568L;


	public static final String MESSAGE_UID_EXISTS = "This unique id is already registered.";

	public static final String EXPRESSION_ID_USER_EXISTS = "constraint [colonist_intergalactic_id_idx]";


	public CannotSignUpColonistException(final String message, final Throwable cause) {

		super(message, cause);

	}
}
