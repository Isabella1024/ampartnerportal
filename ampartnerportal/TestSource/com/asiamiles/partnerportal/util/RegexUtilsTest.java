/*
 * Created on Jul 30, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class RegexUtilsTest extends TestCase {

	private void performTest(Pattern pattern, String testString, boolean expectedResult) {
		Matcher matcher = pattern.matcher(testString);
		assertEquals(expectedResult, matcher.matches());
	}
	
	public void testAgentNamePattern() {
		Pattern pattern = RegexUtils.getAgentNamePattern();
		
		performTest(pattern, "", false);
		performTest(pattern, "abc123", false);
		performTest(pattern, "Bob", true);
		performTest(pattern, "BOB", true);
		performTest(pattern, "Bob-Blah", true);
		performTest(pattern, "Bob's Blah", true);
		performTest(pattern, "Bob & Blah", false);
	}
	
	public void testPasswordPattern() {
		Pattern pattern = RegexUtils.getPasswordPattern();
		
		performTest(pattern, "", false);
		performTest(pattern, "abc123", true);
		performTest(pattern, "ABC123", true);
		performTest(pattern, "123456", true);
		performTest(pattern, "abc", false);
		performTest(pattern, "12345678", true);
		performTest(pattern, "abc123DEF", false);
		performTest(pattern, "abc123!@#", false);
		performTest(pattern, "abc 123", false);
	}
}
