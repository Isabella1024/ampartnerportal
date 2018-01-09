/*
 * Created on Jul 30, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.util.regex.Pattern;

/**
 * Utility Class for centralising all Regular Expressions
 * @author CPPBENL
 *
 */
public class RegexUtils {
		
	/**
	 * Private constructor to prevent accidential instantiation
	 */
	private RegexUtils() {}
	
	/**
	 * Returns the regular expression pattern for Agent First Names and Surnames.
	 * @return
	 */
	public static Pattern getAgentNamePattern() {
		return Pattern.compile("[a-zA-Z'\\- ]+");
	}
	
	/**
	 * Returns the regular expression pattern for agent passwords.
	 * @return
	 */
	public static Pattern getPasswordPattern() {
		return Pattern.compile("[a-zA-Z0-9]{6,8}");
	}

}
