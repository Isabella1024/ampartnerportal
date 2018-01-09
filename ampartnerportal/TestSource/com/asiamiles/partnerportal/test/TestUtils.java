/*
 * Created on Jun 25, 2009
 *
 */
package com.asiamiles.partnerportal.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Utility class used for Unit Testing 
 * 
 * @author CPPBENL
 *
 */
public class TestUtils {

	private TestUtils(){}

	public static String fetchXML(URL resource) throws Exception{
		StringBuffer buffer = new StringBuffer();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
		
		String line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		return buffer.toString();
	}
	
	
}
