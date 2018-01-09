/*
 * Created on Jun 12, 2009
 *
 */
package com.asiamiles.partnerportal;


/**
 * @author CPPBENL
 *
 */
public class SystemNotAccessibleException extends SystemException {

	public static final String STR_INACCESSIBLE = "error_str_inaccessible";
	
	// -----------
	// Constructor 
	// -----------
	
	/**
	 * @param errorCode
	 */
	public SystemNotAccessibleException(String errorCode) {
		super(errorCode);
	}
	
	/**
	 * @param errorCode
	 * @param message
	 */
	public SystemNotAccessibleException(String errorCode, String message) {
		super(errorCode, message);
	}
	
	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public SystemNotAccessibleException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public SystemNotAccessibleException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
