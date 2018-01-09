/*
 * Created on Jun 10, 2009
 *
 */
package com.asiamiles.partnerportal.cls;

/**
 * Checked Exception for any CLS-related errors
 * 
 * @author CPPBENL
 *
 */
public class CLSException extends Exception {
	
	public static final String UNKNOWN_ERROR_CODE = "unknown";
	public static final String UNAVAILABLE_ERROR_CODE = "unavailable";
	private static final String ERROR_MESSAGE_CODE_PREFIX = "error_cls_";
	
	private String appName;
	private String errorCode;
	
	/**
	 * Constructs an new instance of CLSException with the specified application name and error code.
	 * @param appName
	 * @param errorCode
	 */
	public CLSException (String appName, String errorCode) {
		super(constructErrorMessageCode(appName, errorCode));
		this.appName = appName;
		this.errorCode = errorCode;
		
		if (errorCode == null || errorCode.equals("")) {
			this.errorCode = UNKNOWN_ERROR_CODE;
		}
	}
	
	/**
	 * Constructs an new instance of CLSException with the specified application name, error code and parent cause
	 * @param appName
	 * @param errorCode
	 */
	public CLSException (String appName, String errorCode, Throwable cause) {
		super(constructErrorMessageCode(appName, errorCode),cause);
		this.appName = appName;
		this.errorCode = errorCode;

		if (errorCode == null || errorCode.equals("")) {
			this.errorCode = UNKNOWN_ERROR_CODE;
		}

	}
	
	/**
	 * Constructs an new instance of CLSException with the specified application name, error code, and error message.
	 * @param appName
	 * @param errorCode
	 */
	public CLSException (String appName, String errorCode, String message) {
		super(constructErrorMessageCode(appName, errorCode) + " : " + message);
		this.appName = appName;
		this.errorCode = errorCode;

		if (errorCode == null || errorCode.equals("")) {
			this.errorCode = UNKNOWN_ERROR_CODE;
		}
	
	}

	
	/**
	 * Constructs an new instance of CLSException with the specified application name, error code, message and parent cause
	 * @param appName
	 * @param errorCode
	 */
	public CLSException (String appName, String errorCode, String message, Throwable cause) {
		super(constructErrorMessageCode(appName, errorCode) + " : " + message, cause);
		this.appName = appName;
		this.errorCode = errorCode;

		if (errorCode == null || errorCode.equals("")) {
			this.errorCode = UNKNOWN_ERROR_CODE;
		}

	}
	
	public String getAppName() {
		return appName;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Helper method for constructing the exception message when none is supplied in the constructor
	 * @param appName
	 * @param errorCode
	 * @return
	 */
	private static String constructErrorMessageCode(String appName, String errorCode) {
		if (errorCode == null || errorCode.equals("")) {
			errorCode = UNKNOWN_ERROR_CODE;
		}

		return ERROR_MESSAGE_CODE_PREFIX + appName + "_" + errorCode;
	}
	
	/**
	 * Returns the error message code that is to be used to retrive the localised message
	 * from the STR.
	 * @return
	 */
	public String getErrorMessageCode() {
		return ERROR_MESSAGE_CODE_PREFIX + appName + "_" + errorCode;
	}
	
}
