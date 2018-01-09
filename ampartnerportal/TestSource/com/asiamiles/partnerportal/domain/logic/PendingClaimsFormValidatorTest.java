/**
 * Unit Tests for <code>BillingFormValidator</code>.
 * 
 * @author CPPKENW
 * @see com.asiamiles.partnerportal.domain.logic.BillingFormValidator
 * 
 */
package com.asiamiles.partnerportal.domain.logic;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.PendingClaimsForm;
import com.asiamiles.partnerportal.domain.NARClaimDetails;

import junit.framework.TestCase;

public class PendingClaimsFormValidatorTest extends TestCase {

	private PendingClaimsFormValidator validator;
	private PendingClaimsForm pendingClaimsForm;
	private Errors errors;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new PendingClaimsFormValidator();

		pendingClaimsForm = new PendingClaimsForm();
		NARClaimDetails nar = new NARClaimDetails();
		nar.setAction(NARClaimDetails.ACTION_COMPLETE);
		nar.setReceiptNo("123456");
		pendingClaimsForm.setActiveClaim(nar);

		errors = new BindException(pendingClaimsForm, "pendingClaimsForm");
	}

	public void testValidateDetail() {
		validator.validateDetail(pendingClaimsForm, errors, null);		
		assertTrue(!errors.hasErrors());
	}
	
	public void testValidate() {
		validator.validate(pendingClaimsForm, errors);
		assertTrue(!errors.hasErrors());
	}

}
