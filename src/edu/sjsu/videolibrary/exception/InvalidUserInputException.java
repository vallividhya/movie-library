package edu.sjsu.videolibrary.exception;

public class InvalidUserInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUserInputException() {
		super();
	}

	public InvalidUserInputException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		//super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidUserInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUserInputException(String message) {
		super(message);
	}

	public InvalidUserInputException(Throwable cause) {
		super(cause);
	}
	
	

}
