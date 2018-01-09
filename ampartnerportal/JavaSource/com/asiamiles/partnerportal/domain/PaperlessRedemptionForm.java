/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections.CollectionUtils;

import com.asiamiles.partnerportal.domain.predicate.ToBeCancelledNARClaimDetailsPredicate;
import com.asiamiles.partnerportal.domain.predicate.ToBeCompletedNARClaimDetailsPredicate;


/**
 * Data Object for Paperless Redemption Form
 * @author CPPBENL
 *
 */
public class PaperlessRedemptionForm {

	private static final int MAX_CLAIMS = 5;
	
	private String memberId;
	private String memberEmbossedName;
	private List claims; //List<PaperlessClaim>
	private List NARDetails; //List<NARClaimDetails>
	
	public PaperlessRedemptionForm(){
		
	}
	
	public PaperlessRedemptionForm(boolean initEmptyClaims) {
		claims = new ArrayList(MAX_CLAIMS);
		for (int i = 0; i < MAX_CLAIMS; i++) {
			claims.add(new PaperlessClaim());
		}
	}
	
	public PaperlessRedemptionForm(String memberId, String claimNO, String securityCode) {
		claims = new ArrayList(1);
		PaperlessClaim claim = new PaperlessClaim();
		claim.setClaimNumber(claimNO);
		claim.setSecurityCode(securityCode);
		
		claims.add(claim);
		this.memberId = memberId;
	}
	
	/**
	 * @return Returns the claims.
	 */
	public List getClaims() {
		return claims;
	}
	/**
	 * @param claims The claims to set.
	 */
	public void setClaims(List claims) {
		this.claims = claims;
	}
	/**
	 * @return Returns the memberEmbossedName.
	 */
	public String getMemberEmbossedName() {
		return memberEmbossedName;
	}
	/**
	 * @param memberEmbossedName The memberEmbossedName to set.
	 */
	public void setMemberEmbossedName(String memberEmbossedName) {
		this.memberEmbossedName = memberEmbossedName;
	}
	/**
	 * @return Returns the memberId.
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return Returns the nARDetails.
	 */
	public List getNARDetails() {
		return NARDetails;
	}
	/**
	 * @param details The nARDetails to set.
	 */
	public void setNARDetails(List details) {
		NARDetails = details;
	}
	
	public List getCompletedNARDetails() {
		List result = new ArrayList();
		CollectionUtils.select(NARDetails, new ToBeCompletedNARClaimDetailsPredicate(), result);
		return result;
	}
	
	public List getCancelledNARDetails() {
		List result = new ArrayList();
		CollectionUtils.select(NARDetails, new ToBeCancelledNARClaimDetailsPredicate(), result);
		return result;		
	}


}
