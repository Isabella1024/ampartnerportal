/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.ForgotPasswordRequest;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class ForgotPasswordRequestValidatorTest extends TestCase {

	private ForgotPasswordRequestValidator validator;
	private Errors errors;
	private ForgotPasswordRequest form;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new ForgotPasswordRequestValidator();
		form = new ForgotPasswordRequest();
		errors = new BindException(form, "command");
	}

	public void testValidateNullString() {
		validator.validate(form, errors);
		assertEquals(1, errors.getErrorCount());
	}
	
	public void testValidateEmptyString() {
		form.setAgentID("");
		validator.validate(form, errors);
		assertEquals(1, errors.getErrorCount());
	}
	
	public void testValidateBlanks() {
		form.setAgentID("  ");
		validator.validate(form, errors);
		assertEquals(1, errors.getErrorCount());
	}
	
	public void testSuccess() {
		form.setAgentID("abc");
		validator.validate(form, errors);
		assertEquals(0, errors.getErrorCount());		
	}
}
