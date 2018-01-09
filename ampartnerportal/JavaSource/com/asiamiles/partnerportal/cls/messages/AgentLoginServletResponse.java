/*
 * Created on Jun 19, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

/**
 * @author CPPBENL
 *
 */
public class AgentLoginServletResponse extends CLSResponse {

	/*
	 * NOTE: instance variables has to match the tag names (case sensitive )in the 
	 * CLS Response XML, as XStream uses serialization and ignores getters and setters.
	 */ 
	
	private String partnerCode;
	private String partnerName;
	private Integer timeZone;
	private String agentID;
	private String EmailAddress;
	private String FamilyName;
	private String FirstName;
	private String AdministratorIndicator;
	//cppjox add for AML38938 20160523 start
	private String PartnerGroup;
	
	public String getPartnerGroup() {
		return PartnerGroup;
	}

	public void setPartnerGroup(String partnerGroup) {
		PartnerGroup = partnerGroup;
	}
	//cppjox add for AML38938 20160523 end

	/**
	 * 
	 */
	public AgentLoginServletResponse() {
	}

	
	/**
	 * @return Returns the agentID.
	 */
	public String getAgentID() {
		return agentID;
	}
	/**
	 * @param agentID The agentID to set.
	 */
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	/**
	 * @return Returns the administratorIndicator.
	 */
	public String getAdministratorIndicator() {
		return AdministratorIndicator;
	}
	/**
	 * @param administratorIndicator The administratorIndicator to set.
	 */
	public void setAdministratorIndicator(String administratorIndicator) {
		this.AdministratorIndicator = administratorIndicator;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmailAddress() {
		return EmailAddress;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmailAddress(String emailAddress) {
		this.EmailAddress = emailAddress;
	}
	/**
	 * @return Returns the familyName.
	 */
	public String getFamilyName() {
		return FamilyName;
	}
	/**
	 * @param familyName The familyName to set.
	 */
	public void setFamilyName(String familyName) {
		this.FamilyName = familyName;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return FirstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.FirstName = firstName;
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
	 * @return Returns the timezone.
	 */
	public Integer getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timezone The timezone to set.
	 */
	public void setTimeZone(Integer timeZone) {
		this.timeZone = timeZone;
	}
	
	public static AgentLoginServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("AgentLoginServlet", AgentLoginServletResponse.class);
		Object obj = xstream.fromXML(xml);
		return (AgentLoginServletResponse)obj;
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
}
