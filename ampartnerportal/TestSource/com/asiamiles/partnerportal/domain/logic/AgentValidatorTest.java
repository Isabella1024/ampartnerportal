/*
 * Created on Jul 17, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.Agent;

import junit.framework.TestCase;

/**
 * Unit Tests for <code>AgentValidator</code>.
 * 
 * TODO Finish All unit tests
 * @author CPPBENL
 * @see com.asiamiles.partnerportal.domain.logic.AgentValidator
 * 
 */
public class AgentValidatorTest extends TestCase {

	private AgentValidator validator;
	private Agent testAgent;
	private Errors errors;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new AgentValidator();

		testAgent = new Agent();
		testAgent.setAgentID("TestUser");
		testAgent.setEmailAddress("blah@cathaypacific.com");
		testAgent.setFamilyName("Test");
		testAgent.setFirstName("User");
		testAgent.setAdministratorIndicator("A");
		testAgent.setRemarks("Test Remarks");

		errors = new BindException(testAgent, "agent");
	}

	public void testValidate() {
		validator.validate(testAgent, errors);
		assertFalse(errors.hasErrors());
	}
	
	public void testValidateAgentID() {
		testAgent.setAgentID(null);
		validator.validate(testAgent, errors);
		assertTrue(errors.hasErrors());
	}
	
	public void testValidateValidFirstNames() {
		testAgent.setFirstName("blah-blah");
		validator.validate(testAgent, errors);
		assertFalse(errors.hasErrors());

		testAgent.setFirstName("Blah Blah's Blah-Blah");
		validator.validate(testAgent, errors);
		assertFalse(errors.hasErrors());

	
	}
	
	public void testValidateInvalidFirstName() {
		testAgent.setFirstName("Blahblah!@!@");
		validator.validate(testAgent, errors);
		assertTrue(errors.hasErrors());		
	}
	
	public void testvalidateInvalidEmailAddres() {
		testAgent.setEmailAddress("\u4E20blahblah@blahblah.com");
		validator.validate(testAgent, errors);
		assertTrue(errors.hasErrors());		
	}
}
