/*
 * Created on Jun 12, 2009
 *
 */
package com.asiamiles.partnerportal;


/**
 * System Exceptions that are expected to be recoverable without programmatic intervention
 * (e.g. Connection Timeout, No HTTP Response) 
 * @author CPPBENL
 *
 */
public class RecoverableSystemException extends SystemException {

	public static final String HTTP_CONN_TIMEOUT = "httpConnectionTimeout";
	public static final String HTTP_NO_RESPONSE = "httpNoResponse";

	
	// -----------
	// Constructor 
	// -----------
	

	/**
	 * @param errorCode
	 */
	public RecoverableSystemException(String errorCode) {
		super(errorCode);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 */
	public RecoverableSystemException(String errorCode, String message) {
		super(errorCode, message);
	}
	
	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public RecoverableSystemException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public RecoverableSystemException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
