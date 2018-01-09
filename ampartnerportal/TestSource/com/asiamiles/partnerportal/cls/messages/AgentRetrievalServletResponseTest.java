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
public class AgentRetrievalServletResponseTest extends TestCase {
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/AgentRetrievalServletResponse.xml");
		AgentRetrievalServletResponse response = AgentRetrievalServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
		assertEquals("TEST11", response.getAdministratorAgentID());
		assertEquals("310", response.getPartnerCode());
		assertEquals(1, response.getAgents().size());
	}
}
