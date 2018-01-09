/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

/**
 * Data object for a NAR Claim
 * @author CPPBENL
 *
 */
public class PaperlessClaim {

	private String claimNumber;
	private String securityCode;
	
	/**
	 * @return Returns the securityCode.
	 */
	public String getSecurityCode() {
		return securityCode;
	}
	/**
	 * @param securityCode The securityCode to set.
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	/**
	 * @return Returns the claimNumber.
	 */
	public String getClaimNumber() {
		return claimNumber;
	}
	/**
	 * @param claimNumber The claimNumber to set.
	 */
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
}
