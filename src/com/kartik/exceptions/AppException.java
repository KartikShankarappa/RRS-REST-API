package com.kartik.exceptions;

public class AppException extends Exception{

	private static final long serialVersionUID = 7958848549977589838L;

	public AppException() {
	}
	
	public AppException(String message) {
		super(message);
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
