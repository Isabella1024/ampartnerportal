/**
 * Unit Tests for <code>BillingFormValidator</code>.
 * 
 * @author CPPKENW
 * @see com.asiamiles.partnerportal.domain.logic.BillingFormValidator
 * 
 */
package com.asiamiles.partnerportal.domain.predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;

import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Date;

public class HandledByAgentNARClaimDetailsPredicateTest extends TestCase {

	private Errors errors;
	private ArrayList narList;
	
	private Logger logger = AppLogger.getAppLogger();
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		NARClaimDetails nar  = new NARClaimDetails();
		NARClaimDetails nar2  = new NARClaimDetails();
		narList = new ArrayList();
		ArrayList claimList = new ArrayList();
		PaperlessClaim p = new PaperlessClaim();
		PaperlessClaim p2 = new PaperlessClaim();
		
		p.setClaimNumber("1234567");
		p.setSecurityCode("234234");
		claimList.add(p);
		p2.setClaimNumber("2345678");
		p2.setSecurityCode("234432");		
		claimList.add(p);
		
		nar.setClaimNo(new Integer("1234567"));
		nar.setMemberID("12345678");
		nar.setCollectionTime(new Date());
		nar.setCompletionTime(new Date());
		nar.setBilledDate(new Date());
		nar.setCollectionHandledBy("test11");
		nar.setCompletionHandledBy("test12");
		narList.add(nar);
		
		nar2.setClaimNo(new Integer("2345678"));
		nar2.setMemberID("12345678");
		nar2.setCollectionHandledBy("test12");
		narList.add(nar2);

	}

	public void testEvaluate() {
		CollectionUtils.filter(narList, new HandledByAgentNARClaimDetailsPredicate("test12"));
		assertTrue(narList.size()==2);
	}

}
