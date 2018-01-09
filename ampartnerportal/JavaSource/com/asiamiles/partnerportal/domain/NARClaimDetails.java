/*
 * Created on Jul 8, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

import java.util.Date;

/**
 * Data Object for NAR Claim Details
 * @author CPPBENL
 *
 */
public class NARClaimDetails {
	
	public static final String ACTION_COMPLETE = "COMPLETE";
	public static final String ACTION_CANCEL = "CANCEL";
	
	private String claimStatusCode;
	private Integer claimNo;
	private String approvalCode;
	private String holderName;
	private String holderFirstName;
	private Date collectionTime;
	private String collectionHandledBy;
	private Date completionTime;
	private String completionHandledBy;
	private String packageCode;
	private String packageDescription;
	private String cancelReason;
	private String receiptNo;
	private String remarks;
	private String remarks2;
	private String action; //COMPLETE or CANCEL
	private PaperlessClaim paperlessClaim;

	//Used in Redemption Verification Only 
	private boolean toBeProcessed;	
	private Date consumptionStartDate;
	private Date consumptionEndDate;

	//Used on Pending Claims, Billing & Reporting
	private Date billedDate;
	private String establishmentCode;
	private String memberID;
	
	//Used in Billing only
	private boolean toBeBilled;
	
	//Steven ADD for AML34188 20140710 START
	private String pageNextValue;
	private String pageNextPkgValue;
	//Steven ADD for AML34188 20140710 END
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
	/**
	 * @return Returns the claimStatusCode.
	 */
	public String getClaimStatusCode() {
		return claimStatusCode;
	}
	/**
	 * @param claimStatusCode The claimStatusCode to set.
	 */
	public void setClaimStatusCode(String claimStatusCode) {
		this.claimStatusCode = claimStatusCode;
	}
	/**
	 * @return Returns the collectionHandledBy.
	 */
	public String getCollectionHandledBy() {
		return collectionHandledBy;
	}
	/**
	 * @param collectionHandledBy The collectionHandledBy to set.
	 */
	public void setCollectionHandledBy(String collectionHandledBy) {
		this.collectionHandledBy = collectionHandledBy;
	}
	/**
	 * @return Returns the collectionTime.
	 */
	public Date getCollectionTime() {
		return collectionTime;
	}
	/**
	 * @param collectionTime The collectionTime to set.
	 */
	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}
	/**
	 * @return Returns the completionHandledBy.
	 */
	public String getCompletionHandledBy() {
		return completionHandledBy;
	}
	/**
	 * @param completionHandledBy The completionHandledBy to set.
	 */
	public void setCompletionHandledBy(String completionHandledBy) {
		this.completionHandledBy = completionHandledBy;
	}
	/**
	 * @return Returns the completionTime.
	 */
	public Date getCompletionTime() {
		return completionTime;
	}
	/**
	 * @param completionTime The completionTime to set.
	 */
	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}
	/**
	 * @return Returns the consumptionEndDate.
	 */
	public Date getConsumptionEndDate() {
		return consumptionEndDate;
	}
	/**
	 * @param consumptionEndDate The consumptionEndDate to set.
	 */
	public void setConsumptionEndDate(Date consumptionEndDate) {
		this.consumptionEndDate = consumptionEndDate;
	}
	/**
	 * @return Returns the consumptionStartDate.
	 */
	public Date getConsumptionStartDate() {
		return consumptionStartDate;
	}
	/**
	 * @param consumptionStartDate The consumptionStartDate to set.
	 */
	public void setConsumptionStartDate(Date consumptionStartDate) {
		this.consumptionStartDate = consumptionStartDate;
	}
	/**
	 * @return Returns the holderFirstName.
	 */
	public String getHolderFirstName() {
		return holderFirstName;
	}
	/**
	 * @param holderFirstName The holderFirstName to set.
	 */
	public void setHolderFirstName(String holderFirstName) {
		this.holderFirstName = holderFirstName;
	}
	/**
	 * @return Returns the holderName.
	 */
	public String getHolderName() {
		return holderName;
	}
	/**
	 * @param holderName The holderName to set.
	 */
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	/**
	 * @return Returns the packageCode.
	 */
	public String getPackageCode() {
		return packageCode;
	}
	/**
	 * @param packageCode The packageCode to set.
	 */
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	/**
	 * @return Returns the packageDescription.
	 */
	public String getPackageDescription() {
		return packageDescription;
	}
	/**
	 * @param packageDescription The packageDescription to set.
	 */
	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}
	/**
	 * @return Returns the approvalCode.
	 */
	public String getApprovalCode() {
		return approvalCode;
	}
	/**
	 * @param approvalCode The approvalCode to set.
	 */
	public void setApprovalCode(String securityCode) {
		this.approvalCode = securityCode;
	}
	/**
	 * @return Returns the toBeProcessed.
	 */
	public boolean isToBeProcessed() {
		return toBeProcessed;
	}
	/**
	 * @param toBeProcessed The toBeProcessed to set.
	 */
	public void setToBeProcessed(boolean toBeProcessed) {
		this.toBeProcessed = toBeProcessed;
	}
	/**
	 * @return Returns the paperlessClaim.
	 */
	public PaperlessClaim getPaperlessClaim() {
		return paperlessClaim;
	}
	/**
	 * @param paperlessClaim The paperlessClaim to set.
	 */
	public void setPaperlessClaim(PaperlessClaim paperlessClaim) {
		this.paperlessClaim = paperlessClaim;
	}
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return Returns the billedDate.
	 */
	public Date getBilledDate() {
		return billedDate;
	}
	/**
	 * @param billedDate The billedDate to set.
	 */
	public void setBilledDate(Date billedDate) {
		this.billedDate = billedDate;
	}
	/**
	 * @return Returns the cancelReason.
	 */
	public String getCancelReason() {
		return cancelReason;
	}
	/**
	 * @param cancelReason The cancelReason to set.
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	/**
	 * @return Returns the establishmentCode.
	 */
	public String getEstablishmentCode() {
		return establishmentCode;
	}
	/**
	 * @param establishmentCode The establishmentCode to set.
	 */
	public void setEstablishmentCode(String establishmentCode) {
		this.establishmentCode = establishmentCode;
	}
	/**
	 * @return Returns the receiptNo.
	 */
	public String getReceiptNo() {
		return receiptNo;
	}
	/**
	 * @param receiptNo The receiptNo to set.
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the remarks2.
	 */
	public String getRemarks2() {
		return remarks2;
	}
	/**
	 * @param remarks2 The remarks2 to set.
	 */
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	/**
	 * @return Returns the toBeBilled.
	 */
	public boolean isToBeBilled() {
		return toBeBilled;
	}
	/**
	 * @param toBeBilled The toBeBilled to set.
	 */
	public void setToBeBilled(boolean toBeBilled) {
		this.toBeBilled = toBeBilled;
	}
	/**
	 * @return Returns the memberID.
	 */
	public String getMemberID() {
		return memberID;
	}
	/**
	 * @param memberID The memberID to set.
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	//Steven ADD for AML34188 20140710 START
	public String getPageNextValue() {
		return pageNextValue;
	}
	public void setPageNextValue(String pageNextValue) {
		this.pageNextValue = pageNextValue;
	}
	public String getPageNextPkgValue() {
		return pageNextPkgValue;
	}
	public void setPageNextPkgValue(String pageNextPkgValue) {
		this.pageNextPkgValue = pageNextPkgValue;
	}

	//Steven ADD for AML34188 20140710 END
	
}
