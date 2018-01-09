/**
 * Unit Tests for <code>ReportFormValidator</code>.
 * 
 * @author CPPKENW
 * @see com.asiamiles.partnerportal.domain.logic.ReportFormValidator
 * 
 */
package com.asiamiles.partnerportal.domain.logic;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.ReportForm;

import junit.framework.TestCase;

public class VoidClaimValidatorTest extends TestCase {

	private VoidClaimValidator validator;
	private ReportForm reportForm;
	private Errors errors;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new VoidClaimValidator();

		reportForm = new ReportForm();
		reportForm.setReason("no reason");
		errors = new BindException(reportForm, "reportForm");
	}

	public void testValidateReason() {
		validator.validateReason(reportForm, errors);		
		assertTrue(!errors.hasErrors());
	}
	

}
