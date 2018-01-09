/*
 * Created on Jun 17, 2009
 *
 */
package com.asiamiles.partnerportal.cls;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class providing constants and helper methods to property names 
 * for CLS Interface Properties
 * 
 * @author CPPBENL
 * 
 */
public final class CLSInterfaceProperties {

	/**
	 * Private Constructor to prevent accidental instantiation
	 *
	 */
	private CLSInterfaceProperties() {}
	
	// ### Platform Constants ###
	
	/** Local Developer's machine */
	public static final String PLATFORM_LOCAL = "LOCAL";
	
	/** Production Platform */
	public static final String PLATFORM_PRODUCTION = "PRD";
	
	/** Staging Platform */
	public static final String PLATFORM_STAGING = "STA";
	
	/** Shared Development Platform */
	public static final String PLATFORM_DEVELOPMENT = "DEV";
	
	// ### Common Property Names ###
	
	public static final String COMMON_HOST = "host";
	public static final String COMMON_PORT = "port";
	public static final String COMMON_PROTOCOL = "protocol";
	public static final String COMMON_DEBUG = "debug";
	public static final String COMMON_TIMEOUT = "timeout";
	public static final String COMMON_CONTEXT_PATH = "contextPath";
	public static final String COMMON_SERVLET = "servlet";
	
	// ### Servlet name constants ###
	public static final String APP_AGENT_RETRIEVAL = "agentRetrieval";
	public static final String APP_AGENT_UPDATE = "agentUpdate";
	public static final String APP_CHANGE_PWD = "changePwd";
	public static final String APP_AGENT_LOGIN = "agentLogin";
	public static final String APP_FORGOT_PWD = "forgotPwd";
	public static final String APP_PAPERLESS_RETRIEVAL = "paperlessRetrieval";
	public static final String APP_PAPERLESS_COLLECTION = "paperlessCollection";
	public static final String APP_PAPERLESS_COLLECTION_RETRIEVAL = "paperlessCollectionRetrieval";
	public static final String APP_PAPERLESS_INVOICE_RETRIEVAL = "paperlessInvoiceRetrieval";
	public static final String APP_PAPERLESS_PARTNER_INVOICE = "paperlessPartnerInvoice";
    
    public static final String APP_MEMBERSHIP_VERIFY = "membershipProfileRetrieval";
	
	//Common Parameters 
	public static final String HTTP_PARAM_APP_ID = "sAppID";
	public static final String HTTP_PARAM_PASSWORD = "sPassword";
	
	/**
	 * Constructs the property name from the specified components
	 * @param components
	 * @return
	 */
	public static String constructPropertyName(String[] components) {
		return StringUtils.join(components, '.');
	}
}
