package edu.sjsu.videolibrary.exception;

public class InvalidCreditCardException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCreditCardException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidCreditCardException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidCreditCardException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCreditCardException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidCreditCardException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
