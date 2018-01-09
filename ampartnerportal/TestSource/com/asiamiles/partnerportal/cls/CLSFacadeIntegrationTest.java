/*
 * Created on Jun 24, 2009
 *
 */
package com.asiamiles.partnerportal.cls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.util.HTTPConnectionHelper;

import junit.framework.TestCase;

/**
 * CAUTION: THIS CLASS CONTAINS ONLY INTEGRATION TESTS FOR CLSFACADE!!
 *   I.E. : IT WILL UPDATE DATA STORED IN CLS IF THE TESTS UPDATES ANYTHING!!
 *     SO : REVIEW THE STATIC VARIABLE VALUES BEFORE YOU RUN THESE TESTS!!
 * 
 * @author CPPBENL
 *
 */
public class CLSFacadeIntegrationTest extends TestCase {

	// Update this value to your email address so any email sent by CLS will land in your inbox
	public static final String EMAIL_ADDRESS = "CPPBENL@CATHAYPACIFIC.COM";
	
	// Test Agent Credentials
	public static final String TEST_AGENT_ID = "test11";
	public static final String TEST_AGENT_PASSWORD = "00000000";
	
	// Test Administrator Agent Credentials
	public static final String TEST_ADMIN_ID = "test11";
	public static final String PARTNER_CODE = "310";
	
	SiteProperties siteProperties;
	
	CLSFacade clsFacade;
	
	public CLSFacadeIntegrationTest(String name) throws Exception{
		super(name);
		siteProperties = new SiteProperties();
		siteProperties.setPropertiesPath(new ClassPathResource("clsInterface.properties"));
		siteProperties.setPlatform("local");
	}
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		clsFacade = new CLSFacade();
		clsFacade.setClsProperties(siteProperties);
		clsFacade.setHttpConnectionHelper(new HTTPConnectionHelper());
	}

	
	public void testLoginAgent() throws Exception{
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		assertNotNull(agent);
	}
	
	public void testRetrieveClaims() throws Exception {
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		String memberID = "1535104469";
		String embossedName = "Tester Test";
		List inputClaims = new ArrayList();
		PaperlessClaim claim = new PaperlessClaim();
		claim.setClaimNumber("3432062");
		claim.setSecurityCode("45400346120");
		inputClaims.add(claim);
		
		Map claims = clsFacade.retrieveClaims(agent, memberID, embossedName, inputClaims);
		
	}

	public void testRetrieveInvoice() throws Exception {
		Agent agent = clsFacade.loginAgent("cppkenw", "12345678");
		clsFacade.retrieveInvoice(agent);
	}	
	
	public void  testRetrieveCollectionList() throws Exception{
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		String establishmentCode = "ALL";
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -2);
		c2.add(Calendar.MONTH, 2);
		List claims = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_BILLED,establishmentCode,null,c1.getTime(),c2.getTime());
	}
	
	public void  testBilling() throws Exception{
		Agent agent = clsFacade.loginAgent("cppkenw", "12345678");
		String establishmentCode = "310";
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		//clsFacade.billing(agent,establishmentCode,"",c1.getTime(),c2.getTime(),"12345",null);
	}
	
	public void  testUpdateCollection() throws Exception{
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		String memberID = "1535104469";
		Integer claimNo = new Integer(3432062);
		clsFacade.updateCollection(agent,ActionCode.NAR_COMPLETE,memberID,claimNo,null,"12345","remark","remark2","");
	}
	
	public void testRetrieveAgents() throws Exception {
		String adminID = TEST_ADMIN_ID;
		String partnerCode = PARTNER_CODE;
		List agents = clsFacade.retrieveAgents(adminID, null);
		assertFalse(agents.isEmpty());
		
		Agent agent = (Agent)agents.get(0);
		assertNotNull(agent.getEmailAddress());
		assertNotNull(agent.getAdministratorIndicator());
		assertNotNull(agent.getAgentID());
		
		agents = clsFacade.retrieveAgents(adminID, null);
	}
	
	public void testUpdateAgentCreate() throws Exception {
		
		Agent agent = new Agent();
		agent.setAgentID("ben.lai2");
		agent.setEmailAddress(EMAIL_ADDRESS);
		agent.setFirstName("Ben");
		agent.setFamilyName("Test");
		agent.setAdministratorIndicator("A");
		agent.setRemarks("Testing Remarks");
		
		//clsFacade.updateAgent(agent, ActionCode.AGENT_CREATE, TEST_ADMIN_ID, null);
	}

	public void testUpdateAgentCreateWithPartnerCode() throws Exception {
		
		Agent agent = new Agent();
		agent.setAgentID("ben.lai3");
		agent.setEmailAddress(EMAIL_ADDRESS);
		agent.setFirstName("Ben");
		agent.setFamilyName("Test");
		agent.setAdministratorIndicator("N");
		agent.setRemarks("Testing Remarks");
		agent.setPartnerCode("129");
		
		clsFacade.updateAgent(agent, ActionCode.AGENT_CREATE, "WEBCLSADMIN", agent.getPartnerCode());
	}
	
	public void testUpdateAgentUpdate() throws Exception {
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		agent.setRemarks("Blahblah Moolah!!!");
		clsFacade.updateAgent(agent, ActionCode.AGENT_UPDATE, TEST_ADMIN_ID, null);
		List agents = clsFacade.retrieveAgents(TEST_ADMIN_ID, "310");
		
		for (Iterator it = agents.iterator(); it.hasNext();) {
			Agent agent2 = (Agent)it.next();
			if (agent2.getAgentID().equals(agent.getAgentID())) {
				assertEquals(agent.getRemarks().toUpperCase(), agent2.getRemarks());
			}
		}
	}
	
	public void testUpdateAgentDelete() throws Exception {
		Agent agent = new Agent();
		agent.setAgentID("ben.lai2");
		//clsFacade.updateAgent(agent, ActionCode.AGENT_DELETE, TEST_ADMIN_ID, null);
		
	}
	
	public void testUpdateAgentResetPassword() throws Exception {
		Agent agent = new Agent();
		agent.setAgentID("ken.wong");
		clsFacade.updateAgent(agent, ActionCode.AGENT_RESET_PASSWORD, TEST_ADMIN_ID, null);
	}
	
	public void testChangeAgentPassword() throws Exception {
		String newPassword = "00000000";
		Agent agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
		
		// Test for invalid inputs
		try {
			clsFacade.changeAgentPassword(null, TEST_AGENT_ID, TEST_AGENT_PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {}
		try {
			clsFacade.changeAgentPassword(agent, "", newPassword);
			fail();
		} catch (IllegalArgumentException e) {}
		try {
			clsFacade.changeAgentPassword(agent, TEST_AGENT_PASSWORD, "");
			fail();
		} catch (IllegalArgumentException e) {}

		clsFacade.changeAgentPassword(agent, TEST_AGENT_PASSWORD, newPassword);
		
		//Verification
		agent = clsFacade.loginAgent(TEST_AGENT_ID, newPassword);
		
		//cleanup
		clsFacade.changeAgentPassword(agent, newPassword, TEST_AGENT_PASSWORD);
		agent = clsFacade.loginAgent(TEST_AGENT_ID, TEST_AGENT_PASSWORD);
	}
	
	public void testForgotPassword() throws Exception {
		try {
			clsFacade.forgotPassword("");
			fail();
		} catch (IllegalArgumentException e){}
		
		//clsFacade.forgotPassword(TEST_AGENT_ID);
		clsFacade.forgotPassword("ben.lai");
	}
	
}

