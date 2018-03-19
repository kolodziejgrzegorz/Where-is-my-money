package com.whereIsMyMoney.exception;

public class DataExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096052700979368761L;
	
	public DataExistsException(String message) {
		super(message);
	}
}
