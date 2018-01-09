/*
 * Created on Jun 18, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.net.SocketTimeoutException;
import java.net.URL;

import com.asiamiles.partnerportal.SystemException;

import junit.framework.TestCase;

/**
 * 
 * @author CPPBENL
 *
 */
public class HTTPConnectionHelperTest extends TestCase {

	HTTPConnectionHelper helper;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		helper = new HTTPConnectionHelper();
	}

	public void testPostToSite() throws Exception {
		URL url = new URL("http://www.asiamiles.com/am/en/homepage");
		String result = helper.postToSite(url, null, 0);
		assertNotNull(result);
	}

	public void testPostToSiteWithTimeout() throws Exception {
		URL url = new URL("http://prestaam.asiamiles.com/am/en/homepage");
		try {
			String result = helper.postToSite(url, null, 10);
			fail("The Response was a bit too fast - didn't timeout");
		} catch (SystemException e) {
			assertEquals(SocketTimeoutException.class, e.getCause().getClass());
		}
	}
	
	public void testPostToSiteWithNegativeTimeout() throws Exception {
		URL url = new URL("http://prestaam.asiamiles.com/am/en/homepage");
		try {
			String result = helper.postToSite(url, null, -10);
			fail("Negative Timeout Value was accepted");
		} catch (IllegalArgumentException e) {
		}
	}
	

}
