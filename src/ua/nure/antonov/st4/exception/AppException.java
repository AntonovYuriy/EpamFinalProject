package ua.nure.antonov.st4.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 8288779062647218916L;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
