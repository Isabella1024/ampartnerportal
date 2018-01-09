package com.asiamiles.partnerportal.web.controller;

import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;

public class TestData {
    
    public static final String EMAIL_ADDRESS = "CPPJULI@CATHAYPACIFIC.COM";
    
    private UserSession userSession;
    
    private Agent agent;
    
    public TestData(){
        userSession = new UserSession();
        agent = new Agent();
    }
        
    public UserSession getUserSession(){
        return userSession;
    }
    
    public void setAgent(){
        agent.setAgentID("JACK");
        agent.setEmailAddress(EMAIL_ADDRESS);
        agent.setFirstName("Jane");
        agent.setFamilyName("Lee");
        agent.setAdministratorIndicator(Agent.AGENT_TYPE_ADMINISTRATOR);
        agent.setRemarks("Testing Remarks");
        agent.setPartnerCode("ARP");
        agent.setPartnerName("[ARP PAPERLESS - REGULAR");
        agent.setTimeZone(new Integer(800));
        userSession.setAgent(agent);
    }
   
    public Agent getAgent(){
        return agent;
    }
    
    public String getEstablishmentCode(){
        return "ALL";
    }
    
}
