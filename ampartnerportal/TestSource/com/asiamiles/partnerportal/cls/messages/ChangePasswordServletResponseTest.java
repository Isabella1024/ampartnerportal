/*
 * Created on Jun 25, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import java.net.URL;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.asiamiles.partnerportal.test.TestUtils;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class ChangePasswordServletResponseTest extends TestCase {

	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/ChangePasswordServletResponse.xml");
		ChangePasswordServletResponse response = ChangePasswordServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
	}
}
