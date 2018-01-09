/*
 * Created on Jun 22, 2009
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
public class AgentLoginServletResponseTest extends TestCase {
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/AgentLoginServletResponse.xml");
		AgentLoginServletResponse response = AgentLoginServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
		assertEquals("THE LANDIS TAIPEI - REGULAR", response.getPartnerName());
		assertEquals(new Integer(800), response.getTimeZone());
		assertEquals("TEST11", response.getAgentID());
		assertEquals("CPPBIL@CATHAYPACIFIC.COM", response.getEmailAddress());
		assertEquals("TEST", response.getFamilyName());
		assertEquals("TEST", response.getFirstName());
		assertEquals("A", response.getAdministratorIndicator());
	}
}
