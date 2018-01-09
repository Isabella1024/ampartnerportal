/**
 * Unit Tests for <code>PaperlessRedemptionFormValidator</code>.
 * 
 * @author CPPKENW
 * @see com.asiamiles.partnerportal.domain.logic.PaperlessRedemptionFormValidator
 * 
 */
package com.asiamiles.partnerportal.domain.logic;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.PaperlessRedemptionForm;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

import junit.framework.TestCase;
import java.util.ArrayList;

public class PaperlessRedemptionFormValidatorTest extends TestCase {

	private PaperlessRedemptionFormValidator validator;
	private PaperlessRedemptionForm redemptionForm;
	private Errors errors;
	
	private Logger logger = AppLogger.getAppLogger();
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new PaperlessRedemptionFormValidator();

		redemptionForm = new PaperlessRedemptionForm();
		NARClaimDetails nar  = new NARClaimDetails();
		NARClaimDetails nar2  = new NARClaimDetails();
		ArrayList narList = new ArrayList();
		ArrayList claimList = new ArrayList();
		PaperlessClaim p = new PaperlessClaim();
		PaperlessClaim p2 = new PaperlessClaim();
		
		p.setClaimNumber("1234567");
		p.setSecurityCode("12345678901");
		claimList.add(p);
		p2.setClaimNumber("2345678");
		p2.setSecurityCode("12345678901");
		claimList.add(p);
		
		nar.setClaimNo(new Integer("1234567"));
		nar.setMemberID("12345678");
		nar.setToBeProcessed(true);
		nar.setAction(NARClaimDetails.ACTION_COMPLETE);
		nar.setReceiptNo("12345");
		narList.add(nar);
		
		nar2.setClaimNo(new Integer("2345678"));
		nar2.setMemberID("12345678");
		nar2.setToBeProcessed(true);
		nar2.setAction(NARClaimDetails.ACTION_COMPLETE);
		nar2.setReceiptNo("12345");
		narList.add(nar2);
		
		redemptionForm.setClaims(claimList);
		redemptionForm.setNARDetails(narList);

		errors = new BindException(redemptionForm, "redemptionForm");
	}
	
	public void testValidate() {
		validator.validate(redemptionForm, errors);
		logger.debug(errors.getAllErrors().toString());
		assertTrue(errors.hasErrors());
	}
	
	public void testValidateRefAndReason() {
		validator.validateRefAndReason(redemptionForm, errors,null);
		assertTrue(!errors.hasErrors());
	}

	public void testValidateMemberInfo() {
		redemptionForm.setMemberId("1234567890");
		redemptionForm.setMemberEmbossedName("Ken Wong");
		validator.validateMemberInfo(redemptionForm, errors);
		assertTrue(!errors.hasErrors());
	}
	
	public void testValidateClaims(){
		validator.validateClaims(redemptionForm, errors);
		assertTrue(!errors.hasErrors());
	}
	
}
