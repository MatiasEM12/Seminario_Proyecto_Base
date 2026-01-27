package ar.edu.unrn.seminario.exception;

public class DataExistsException extends Exception{
	public DataExistsException() {
		super();
	}

	public DataExistsException(String message) {
		super(message);
	}
}
