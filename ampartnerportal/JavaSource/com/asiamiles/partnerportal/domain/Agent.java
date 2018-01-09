/*
 * Created on Jun 5, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

/**
 * Domain object representing an agent in the partner portal.
 * 
 * @author CPPBENL
 */
public class Agent {

	public static final String AGENT_TYPE_ADMINISTRATOR = "A";
	public static final String AGENT_TYPE_FINANCE_AGENT = "F";
	public static final String AGENT_TYPE_NORMAL_PARTNER_AGENT = "N";
	public static final String AGENT_TYPE_SUPERVISOR = "S";
	//cppjox add for AML38938 20160523 start 
	public static final String AGENT_CATEGORY_REDEMPTION = "R";
	public static final String AGENT_CATEGORY_ACCRUL = "A";
	//cppjox add for AML38938 20160523 end
	private String agentID;
	private String emailAddress;
	private String familyName;
	private String firstName;
	private String administratorIndicator;
	private String partnerCode;
	private String partnerName;
	private String remarks;
	private Integer timeZone;
	//AML38938 Start
	private String partnerGroup;
	//AML38938 End
	
	/**
	 * @return Returns the agentID.
	 */
	public String getAgentID() {
		return agentID;
	}
	/**
	 * @param agentID The agentID to set.
	 */
	public void setAgentID(String agentId) {
		this.agentID = agentId;
	}

	/**
	 * @return Returns the administratorIndicator.
	 */
	public String getAdministratorIndicator() {
		return administratorIndicator;
	}
	/**
	 * @param administratorIndicator The administratorIndicator to set.
	 */
	public void setAdministratorIndicator(String agentType) {
		this.administratorIndicator = agentType;
	}
	/**
	 * @return Returns the emailAddress.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress The emailAddress to set.
	 */
	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}
	/**
	 * @return Returns the familyName.
	 */
	public String getFamilyName() {
		return familyName;
	}
	/**
	 * @param familyName The familyName to set.
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the partnerName.
	 */
	public String getPartnerName() {
		return partnerName;
	}
	/**
	 * @param partnerName The partnerName to set.
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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
	 * @return Returns the partnerCode.
	 */
	public String getPartnerCode() {
		return partnerCode;
	}
	/**
	 * @param partnerCode The partnerCode to set.
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	/**
	 * @return Returns the timeZone.
	 */
	public Integer getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone The timeZone to set.
	 */
	public void setTimeZone(Integer timeZone) {
		this.timeZone = timeZone;
	}
	//AML38938 Start
	/**
	 * @return Returns the partnerGroup.
	 */
	public String getPartnerGroup() {
		return partnerGroup;
	}
	/**
	 * @param partnerGroup The partnerGroup to set.
	 */
	public void setPartnerGroup(String partnerGroup) {
		this.partnerGroup = partnerGroup;
	}
	//AML38938 End
}
