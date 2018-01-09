/*
 * Created on Jun 12, 2009
 *
 */
package com.asiamiles.partnerportal;

/**
 * @author CPPBENL
 *
 */
public class SystemConfigException extends SystemException {

	public static final String CLS_MALFORMED_URL = "error_config_cls_malformedURL";
	public static final String SSL_INVALID_PORT = "error_ssl_invalid_port";
	public static final String STR_LOAD_ERROR = "error_str_load_failure"; 
	
	// -----------
	// Constructor 
	// -----------
	
	/**
	 * @param errorCode
	 */
	public SystemConfigException(String errorCode) {
		super(errorCode);
	}
	

	/**
	 * @param errorCode
	 * @param message
	 */
	public SystemConfigException(String errorCode, String message) {
		super(errorCode, message);
	}
	
	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public SystemConfigException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	/**
	 * @param errorCode
	 * @param cause
	 */
	public SystemConfigException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
