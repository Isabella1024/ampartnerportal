/*
 * Created on Jun 25, 2009
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
public class ForgotPasswordServletResponse extends CLSResponse {

	private String PartnerAgentID;
	private String EmailAddress;
	private String Password;
	private String FamilyName;
	private String FirstName;
	private String AdministratorIndicator;
	
	
	
	/**
	 * 
	 */
	public ForgotPasswordServletResponse() {
		super();
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
		AdministratorIndicator = administratorIndicator;
	}
	/**
	 * @return Returns the emailAddress.
	 */
	public String getEmailAddress() {
		return EmailAddress;
	}
	/**
	 * @param emailAddress The emailAddress to set.
	 */
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
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
		FamilyName = familyName;
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
		FirstName = firstName;
	}
	/**
	 * @return Returns the partnerAgentID.
	 */
	public String getPartnerAgentID() {
		return PartnerAgentID;
	}
	/**
	 * @param partnerAgentID The partnerAgentID to set.
	 */
	public void setPartnerAgentID(String partnerAgentID) {
		PartnerAgentID = partnerAgentID;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		Password = password;
	}
	public static ForgotPasswordServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("ForgotPwdServlet", ForgotPasswordServletResponse.class);
		Object obj = xstream.fromXML(xml);
		return (ForgotPasswordServletResponse)obj;
	}
}
