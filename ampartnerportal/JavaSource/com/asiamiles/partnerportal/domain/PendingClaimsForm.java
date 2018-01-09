/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;


import java.util.List;
import java.io.Serializable;


/**
 * Data Object for the Pending Claims function
 * @author CPPKENW
 *
 */
public class PendingClaimsForm implements Serializable{

	private List claims;	//filtered claims
	private List allClaims; //all the outstanding claims
	private int claimIndex;
	private NARClaimDetails activeClaim;
	private Integer claimNo; //the claim number to search
	

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
	 * @return Returns the claimIndex.
	 */
	public int getClaimIndex() {
		return claimIndex;
	}
	/**
	 * @param claimIndex The claimIndex to set.
	 */
	public void setClaimIndex(int claimIndex) {
		this.claimIndex = claimIndex;
	}
	/**
	 * @return Returns the allClaims.
	 */
	public List getAllClaims() {
		return allClaims;
	}
	/**
	 * @param allClaims The allClaims to set.
	 */
	public void setAllClaims(List allClaims) {
		this.allClaims = allClaims;
	}
	/**
	 * @return Returns the activeClaim.
	 */
	public NARClaimDetails getActiveClaim() {
		return activeClaim;
	}
	/**
	 * @param activeClaim The activeClaim to set.
	 */
	public void setActiveClaim(NARClaimDetails activeClaim) {
		this.activeClaim = activeClaim;
	}
	/**
	 * @return Returns the claimNo.
	 */
	public Integer getClaimNo() {
		return claimNo;
	}
	/**
	 * @param claimNo The claimNo to set.
	 */
	public void setClaimNo(Integer claimNo) {
		this.claimNo = claimNo;
	}
}
