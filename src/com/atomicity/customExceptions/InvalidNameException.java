package com.atomicity.customExceptions;

public class InvalidNameException extends Exception {

	private static final long serialVersionUID = -4073093727455493241L;

	public InvalidNameException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}