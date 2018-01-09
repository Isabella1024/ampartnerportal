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

import com.asiamiles.partnerportal.domain.BillingForm;
import com.asiamiles.partnerportal.domain.NARClaimDetails;

import junit.framework.TestCase;
import java.util.ArrayList;

public class BillingFormValidatorTest extends TestCase {

	private BillingFormValidator validator;
	private BillingForm billingForm;
	private Errors errors;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new BillingFormValidator();

		billingForm = new BillingForm();
		NARClaimDetails nar  = new NARClaimDetails();
		NARClaimDetails nar2  = new NARClaimDetails();
		ArrayList list = new ArrayList();
		
		nar.setClaimNo(new Integer("1234567"));
		nar.setMemberID("12345678");
		list.add(nar);
		
		nar2.setClaimNo(new Integer("2345678"));
		nar2.setMemberID("12345678");
		list.add(nar2);
		
		billingForm.setClaims(list);

		errors = new BindException(billingForm, "billingForm");
	}

	public void testValidate() {
		validator.validate(billingForm, errors);
		assertTrue(errors.hasErrors());
	}
	
	public void testValidateDetail() {
		billingForm.setInvoiceNo("123123");
		validator.validateDetail(billingForm, errors);
		assertTrue(!errors.hasErrors());
	}

}
