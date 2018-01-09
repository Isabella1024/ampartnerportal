/*
 * Created on Jul 6, 2009
 *
 */
package com.asiamiles.partnerportal.str;

import org.springframework.validation.DefaultMessageCodesResolver;

/**
 * Customized implmentation of the <code>DefaultMessageCodesResolver</code>, to replace the default
 * CODE_SEPARATOR (the period "." character) with underscore.
 * 
 * This implementation is done to circumvent the limitation of STR Message Codes: it does not accept 
 * periods (".") in its message codes.
 * 
 * @author CPPBENL
 *
 * 
 */
public class STRMessageCodesResolver extends DefaultMessageCodesResolver {

	public static final String STR_CODE_SEPARATOR = "_";
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.DefaultMessageCodesResolver#postProcessMessageCode(java.lang.String)
	 */
	protected String postProcessMessageCode(String code) {
		String result = super.postProcessMessageCode(code);
		result = result.replaceAll("\\" + super.CODE_SEPARATOR, STR_CODE_SEPARATOR);
//		System.out.println("*** postProcessMessageCode(" + code + "): " + result);
		return result;
	}
}
