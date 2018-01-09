package com.asiamiles.partnerportal.cls.messages;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

public class MembershipProfileRetrievalServletResponse extends CLSResponse {

    private String memberID; 
    private String lastName;
    private String firstName;
    
    public MembershipProfileRetrievalServletResponse(){}
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the memberID
     */
    public String getMemberID() {
        return memberID;
    }
    /**
     * @param memberID the memberID to set
     */
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
    
    public static MembershipProfileRetrievalServletResponse decodeFromXML(String xml){
        XStream xstream = new XStream(new XppDomDriver());
        xstream.alias("MembershipProfileRetrievalServlet", MembershipProfileRetrievalServletResponse.class);
        Object obj = xstream.fromXML(xml);
        return (MembershipProfileRetrievalServletResponse)obj;
    }
}
