package io.codelirium.blueground.intergalactica.controller.exception;

public class CannotProcessUnitViewingException extends RuntimeException {

	private static final long serialVersionUID = -8543535989670275603L;


	public static final String MESSAGE_FAILED_TO_INCREASE_UNIT_VIEWERS = "Failed to increase unit viewers.";

	public static final String MESSAGE_FAILED_TO_DECREASE_UNIT_VIEWERS = "Failed to decrease unit viewers.";


	public CannotProcessUnitViewingException(final String message, final Throwable cause) {

		super(message, cause);

	}
}
