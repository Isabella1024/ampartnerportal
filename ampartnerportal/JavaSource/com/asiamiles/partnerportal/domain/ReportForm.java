/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;


import java.util.List;
import java.io.Serializable;


/**
 * Form Data Object for Report Functions
 * @author CPPKENW
 *
 */
public class ReportForm implements Serializable{
	
	private List claims;
	private int claimIndex;
	private String fromDay;
	private String fromMonth;
	private String fromYear;
	private String toDay;
	private String toMonth;
	private String toYear;	
	//Steven UPDATE for AML34188 20140710 START
	//private String progressOption;
	//private String completeOption;
	//private String billOption;
	//Steven UPDATE for AML34188 20140710 END
	private String keyword;
	private String reason;
	private String voidedClaim;	// pass from voidClaim.do to report.do, indicates which claim has been voided
	private String claimToVoid;	// pass from report.do to voidClaim.do, indicates which claim will be voided
	private NARClaimDetails activeClaim; //information of the voiding claim
	//Steven ADD for AML34188 20140710 START
	private String choose;
	private String nextPackageCode;
	private String nextClaimNum;
	private String prePackageCode;
	private String preClaimNum;
	private String totalNum;
	private String totalPage;
	private int currentPage;
	//Steven ADD for AML34188 20140710 END
	
	public ReportForm(){
		//by default, the 3 checkbox are checked
		//Steven UPDATE for AML34188 20140710 START
		choose="progressOption";
		/*progressOption = "1";
		completeOption = "0";
		billOption = "0";*/
		//Steven UPDATE for AML34188 20140710 END
	}
	
	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * @return Returns the fromDay.
	 */
	public String getFromDay() {
		return fromDay;
	}
	/**
	 * @param fromDay The fromDay to set.
	 */
	public void setFromDay(String fromDay) {
		this.fromDay = fromDay;
	}
	/**
	 * @return Returns the fromMonth.
	 */
	public String getFromMonth() {
		return fromMonth;
	}
	/**
	 * @param fromMonth The fromMonth to set.
	 */
	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}
	/**
	 * @return Returns the fromYear.
	 */
	public String getFromYear() {
		return fromYear;
	}
	/**
	 * @param fromYear The fromYear to set.
	 */
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return Returns the toDay.
	 */
	public String getToDay() {
		return toDay;
	}
	/**
	 * @param toDay The toDay to set.
	 */
	public void setToDay(String toDay) {
		this.toDay = toDay;
	}
	/**
	 * @return Returns the toMonth.
	 */
	public String getToMonth() {
		return toMonth;
	}
	/**
	 * @param toMonth The toMonth to set.
	 */
	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}
	/**
	 * @return Returns the toYear.
	 */
	public String getToYear() {
		return toYear;
	}
	/**
	 * @param toYear The toYear to set.
	 */
	public void setToYear(String toYear) {
		this.toYear = toYear;
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
	
	//Steven UPDATE for AML34188 20140710 START
/*
	*//**
	 * @return Returns the billOption.
	 *//*
	public String getBillOption() {
		return billOption;
	}
	*//**
	 * @param billOption The billOption to set.
	 *//*
	public void setBillOption(String billOption) {
		this.billOption = billOption;
	}
	*//**
	 * @return Returns the completeOption.
	 *//*
	public String getCompleteOption() {
		return completeOption;
	}
	*//**
	 * @param completeOption The completeOption to set.
	 *//*
	public void setCompleteOption(String completeOption) {
		this.completeOption = completeOption;
	}
	*//**
	 * @return Returns the progressOption.
	 *//*
	public String getProgressOption() {
		return progressOption;
	}
	*//**
	 * @param progressOption The progressOption to set.
	 *//*
	public void setProgressOption(String progressOption) {
		this.progressOption = progressOption;
	}*/
	
	//Steven UPDATE for AML34188 20140710 END
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
	 * @return Returns the voidedClaim.
	 */
	public String getVoidedClaim() {
		return voidedClaim;
	}
	/**
	 * @param voidedClaim The voidedClaim to set.
	 */
	public void setVoidedClaim(String voidedClaim) {
		this.voidedClaim = voidedClaim;
	}
	/**
	 * @return Returns the claimToVoid.
	 */
	public String getClaimToVoid() {
		return claimToVoid;
	}
	/**
	 * @param claimToVoid The claimToVoid to set.
	 */
	public void setClaimToVoid(String claimToVoid) {
		this.claimToVoid = claimToVoid;
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

	//Steven ADD for AML34188 20140710 START
	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}
	
	public String getNextClaimNum() {
		return nextClaimNum;
	}

	public void setNextClaimNum(String nextClaimNum) {
		this.nextClaimNum = nextClaimNum;
	}

	public String getNextPackageCode() {
		return nextPackageCode;
	}

	public void setNextPackageCode(String nextPackageCode) {
		this.nextPackageCode = nextPackageCode;
	}

	public String getPrePackageCode() {
		return prePackageCode;
	}

	public void setPrePackageCode(String prePackageCode) {
		this.prePackageCode = prePackageCode;
	}

	public String getPreClaimNum() {
		return preClaimNum;
	}

	public void setPreClaimNum(String preClaimNum) {
		this.preClaimNum = preClaimNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	//Steven ADD for AML34188 20140710 END
		
}
