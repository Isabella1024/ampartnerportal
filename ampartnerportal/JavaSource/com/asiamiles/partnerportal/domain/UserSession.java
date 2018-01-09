/*
 * Created on Jun 10, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * User Session object for storing session information
 * 
 * @author CPPBENL
 * 
 */
public class UserSession {

	public static final String SESSION_ATTRIBUTE_NAME = "userSession";

	private Agent agent;

	private String webCLSAdminAgentID;

	private String fromDay;
	private String fromMonth;
	private String fromYear;
	private String toDay;
	private String toMonth;
	private String toYear;
	//Steven UPDATE for AML34188 20140710 START
	/*private String progressOption;
	private String completeOption;
	private String billOption;*/
	//Steven UPDATE for AML34188 20140710 END
	private String keyword;
	private String voidedClaim;
	
	//Steven ADD for AML34188 20140710 START
	private String choose;
	private String establishmentCode;
	
	private String nextPackageCode;
	private String nextClaimNum;
	private String prePackageCode;
	private String preClaimNum;
	private int currentPage;
	
	private List claims=new ArrayList(); //List<NARClaimDetails>
	
	private List unbillList=new ArrayList();
	//Steven ADD for AML34188 20140710 END

	public UserSession() {
	}

	/**
	 * @return Returns the agent.
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *            The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the webCLSAdminAgentID.
	 */
	public String getWebCLSAdminAgentID() {
		return webCLSAdminAgentID;
	}

	/**
	 * @param webCLSAdminAgentID
	 *            The webCLSAdminAgentID to set.
	 */
	public void setWebCLSAdminAgentID(String webCLSAdminAgentID) {
		this.webCLSAdminAgentID = webCLSAdminAgentID;
	}
	//Steven UPDATE for AML34188 20140710 START
/*	*//**
	 * @return Returns the billOption.
	 *//*
	public String getBillOption() {
		return billOption;
	}

	*//**
	 * @param billOption
	 *            The billOption to set.
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
	 * @param completeOption
	 *            The completeOption to set.
	 *//*
	public void setCompleteOption(String completeOption) {
		this.completeOption = completeOption;
	}*/
	//Steven ADD for AML34188 20140710 END
	/**
	 * @return Returns the fromDay.
	 */
	public String getFromDay() {
		return fromDay;
	}

	/**
	 * @param fromDay
	 *            The fromDay to set.
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
	 * @param fromMonth
	 *            The fromMonth to set.
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
	 * @param fromYear
	 *            The fromYear to set.
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
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	//Steven UPDATE for AML34188 20140710 START
/*	*//**
	 * @return Returns the progressOption.
	 *//*
	public String getProgressOption() {
		return progressOption;
	}

	*//**
	 * @param progressOption
	 *            The progressOption to set.
	 *//*
	public void setProgressOption(String progressOption) {
		this.progressOption = progressOption;
	}*/
	//Steven UPDATE for AML34188 20140710 END
	/**
	 * @return Returns the toDay.
	 */
	public String getToDay() {
		return toDay;
	}

	/**
	 * @param toDay
	 *            The toDay to set.
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
	 * @param toMonth
	 *            The toMonth to set.
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
	 * @param toYear
	 *            The toYear to set.
	 */
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	/**
	 * @return Returns the voidedClaim.
	 */
	public String getVoidedClaim() {
		return voidedClaim;
	}

	/**
	 * @param voidedClaim
	 *            The voidedClaim to set.
	 */
	public void setVoidedClaim(String voidedClaim) {
		this.voidedClaim = voidedClaim;
	}

	//Steven ADD for AML34188 20140710 START
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

	public void setPreClaimNum(String preClaimNum) {
		this.preClaimNum = preClaimNum;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public String getPreClaimNum() {
		return preClaimNum;
	}

	public String getEstablishmentCode() {
		return establishmentCode;
	}

	public void setEstablishmentCode(String establishmentCode) {
		this.establishmentCode = establishmentCode;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getClaims() {
		return claims;
	}

	public void setClaims(List claims) {
		this.claims = claims;
	}

	public List getUnbillList() {
		return unbillList;
	}

	public void setUnbillList(List unbillList) {
		this.unbillList = unbillList;
	}

	//Steven ADD for AML34188 20140710 END
	

}
