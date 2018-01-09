package com.asiamiles.partnerportal.web.controller;

import org.junit.Test;

import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.ReportForm;
import com.asiamiles.partnerportal.domain.UserSession;

/**
 * Unit Test Case for Report Controller
 *
 * @author CPPJULI
 * @since 1.0 
 *
 * @version Revision History
 * <pre>
 * --------------------------------------------------------
 * Version |By            |Date          |Modification
 * -------------------------------------------------------- 
 * 1.0     |CPPJULI       |2014-11-21    |Create 
 * </pre>
 *
 */
public class ReportControllerTestCase extends ReportControllerTest {
    
    public static final String EMAIL_ADDRESS = "CPPJULI@CATHAYPACIFIC.COM";
    
    /**
     * Redirect to report page after login to application
     *
     */
    @Test
    public void firstAccessToReportPage(){
        UserSession us = new UserSession();
        us.setAgent(getAgent());
        testFormBackingObject(us, null, null);
        verifyResult("1", "0", "0", "", "0", "", "0");
    }
    
    /**
     * Access to report page from other page (e.g. billing page)
     *
     */
    @Test
    public void accessToReportPageFromOtherPage(){
        UserSession us = new UserSession();
        us.setAgent(getAgent());
        us.setFromDay("1");
        us.setFromMonth("9");
        us.setFromYear("2014");
        us.setToDay("1");
        us.setToMonth("11");
        us.setToYear("2014");
        testFormBackingObject(us, null, null);
        verifyResult("1", "583", "24", "MS4757", "6313693", "", "0");
    }
    
    /**
     * Click search button
     *
     */
    @Test
    public void searchReportResult(){
        UserSession us = new UserSession();
        us.setAgent(getAgent());
        ReportForm rf = new ReportForm();
        rf.setFromDay("1");
        rf.setFromMonth("9");
        rf.setFromYear("2014");
        rf.setToDay("1");
        rf.setToMonth("11");
        rf.setToYear("2014");
        rf.setChoose("A");
        testPostProcessPage(us, rf);
        verifyResult("1", "583", "24", "MS4757", "6313693", "", "0");
    }
    
    /**
     * Click previous page on page 2, redirect to page 1
     *
     */
    @Test
    public void prePageReport(){
        UserSession us = new UserSession();
        us.setAgent(getAgent());
        us.setFromDay("1");
        us.setFromMonth("9");
        us.setFromYear("2014");
        us.setToDay("1");
        us.setToMonth("11");
        us.setToYear("2014");
        us.setPreClaimNum("6689579");
        us.setPrePackageCode("MS4754");
        us.setCurrentPage(2);
        testFormBackingObject(us, null, "MS4754");
        verifyResult("1", "583", "24", "MS4757", "6313693", "", "0");
    }
    
    /**
     * Click next page on page 1, redirect to page 2
     *
     */
    @Test
    public void nextPageReport(){
        UserSession us = new UserSession();
        us.setAgent(getAgent());
        us.setFromDay("1");
        us.setFromMonth("9");
        us.setFromYear("2014");
        us.setToDay("1");
        us.setToMonth("11");
        us.setToYear("2014");
        us.setNextClaimNum("6313693");
        us.setNextPackageCode("MS4757");
        us.setCurrentPage(1);
        testFormBackingObject(us, "MS4757", null);
        verifyResult("2", "583", "24", "MS4757", "6558603", "MS4754", "6689579");
    }
    
    public Agent getAgent(){
        Agent agent = new Agent();
        agent.setAgentID("JACK");
        agent.setEmailAddress(EMAIL_ADDRESS);
        agent.setFirstName("Jane");
        agent.setFamilyName("Lee");
        agent.setAdministratorIndicator(Agent.AGENT_TYPE_ADMINISTRATOR);
        agent.setRemarks("Testing Remarks");
        agent.setPartnerCode("ARP");
        agent.setPartnerName("[ARP PAPERLESS - REGULAR");
        agent.setTimeZone(new Integer(800));
        return agent;
    }
}
