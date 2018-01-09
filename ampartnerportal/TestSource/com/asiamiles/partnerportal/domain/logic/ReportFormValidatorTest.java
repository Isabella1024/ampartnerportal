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

public class ReportFormValidatorTest extends TestCase {

	private ReportFormValidator validator;
	private ReportForm reportForm;
	private Errors errors;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new ReportFormValidator();
		reportForm = new ReportForm();
		reportForm.setFromDay("10");
		reportForm.setFromMonth("6");
		reportForm.setFromYear("2009");
		reportForm.setToDay("15");
		reportForm.setToMonth("7");
		reportForm.setToYear("2009");	
		reportForm.setKeyword("test12");
		errors = new BindException(reportForm, "reportForm");
	}

	public void testValidate() {
		validator.validate(reportForm, errors);
		assertTrue(!errors.hasErrors());
	}
	
	public void testValidateDetail() {
		validator.validateDate(reportForm, errors);		
		assertTrue(!errors.hasErrors());
	}
	
	public void testValidateKeyword() {
		validator.validateKeyword(reportForm, errors);		
		assertTrue(!errors.hasErrors());
	}
	
}
