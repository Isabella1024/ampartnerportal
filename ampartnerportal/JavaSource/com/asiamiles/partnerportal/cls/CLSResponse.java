/*
 * Created on Jun 19, 2009
 *
 */
package com.asiamiles.partnerportal.cls;

/**
 * Top-level abstract class for CLS API Response objects
 * @author CPPBENL
 *
 */
public abstract class CLSResponse {

	/**
	 * Status Code for a successful CLS API Call.
	 */
	public static final String STATUS_SUCCESS = "0000";
	
	protected String statusCode;
	protected String errorCode;
	protected String errorMessage;
	
	public CLSResponse(){}
	
	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return Returns the statusCode.
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
