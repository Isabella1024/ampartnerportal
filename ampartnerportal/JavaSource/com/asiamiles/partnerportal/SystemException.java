/*
 * Created on Dec 3, 2007
 *
 */
package com.asiamiles.partnerportal;


/**
 * Top parent UncheckedException in Exception hierarchy
 * 
 * @author CPPHOWC
 *
 */
public class SystemException extends RuntimeException {

	public static final String SESSION_ATTRIBUTE_NAME = "exception";
	
	public static final String HTTP_CONN_INVALID_RESPONSE = "httpConnectionInvalidResponse";
	public static final String HTTP_REQUEST_ERROR = "httpRequestError";
	public static final String HTTP_IOEXCEPTION = "httpIOException";
	public static final String REQUEST_MISSING_PARAMETERS = "error_missing_parameters";
	public static final String IOEXCEPTION = "IOException";
	public static final String SERVERCONFIG = "error_serverConfig";
	public static final String CRYPTO_FAILURE = "error_cryptoFailure";
	public static final String INVALID_REQUEST = "error_invalid_request";
	public static final String WEBCLS_INVALID_REQUEST = "error_webcls_invalidRequest";
	public static final String WEBCLS_SESSION_TIMEOUT = "error_webcls_sessionTimeout";
	public static final String CLS_REQUEST_FAILURE = "error_cls_requestFailure";
	
	private String errorCode = null;
		
	
	// -----------
	// Constructor 
	// -----------
	
	/**
	 * @param errorCode
	 */
	public SystemException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	
	/**
	 * @param errorCode
	 * @param message
	 */
	public SystemException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	
	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public SystemException(String errorCode, String message,  Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	

	/**
	 * @param errorCode
	 * @param cause
	 */
	public SystemException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	

	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		if (super.getMessage() == null) {
			return errorCode;
		}
		return "(" + errorCode + ") " + super.getMessage();
	}
}
