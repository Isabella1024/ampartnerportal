package com.asiamiles.partnerportal.cls;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.HTTPConnectionHelper;
import com.asiamiles.partnerportal.web.controller.TestData;

@RunWith(PowerMockRunner.class)
public class CLSFacadeTest extends TestCase {
    
    @Mock
    CLSFacade clsFacade;
    
    @Mock
    private SiteProperties clsProperties;
        
    @Before
    public void setUp() throws Exception {
        clsFacade = PowerMockito.spy(new CLSFacade());
        clsProperties = PowerMockito.mock(SiteProperties.class);
        clsFacade.setClsProperties(clsProperties);
        clsFacade.setHttpConnectionHelper(new HTTPConnectionHelper());
    }
    
    @Test
    public void testRetrieveCollectionList(){
        TestData td = new TestData();
        UserSession us = td.getUserSession();
        String establishmentCode = "ALL";
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.add(Calendar.MONTH, -2);
        //c2.add(Calendar.MONTH, 2);
        Mockito.when(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT)).thenReturn("30000");
        HashMap credentialsMap = new HashMap();
        credentialsMap.put("sAppID", "paperlessNAR");
        credentialsMap.put("sPassword", "95fs6521fa");
        try {
            PowerMockito.doReturn(credentialsMap).when(clsFacade, "getCredentials", CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL);
            URL url = new URL("http://ixservice.ete.cathaypacific.com:80/ixClsNARPortal/PaperlessCollectionRetrievalServlet");
            PowerMockito.doReturn(url).when(clsFacade, "getURLForApp", CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL);
            
            List claims = clsFacade.retrieveCollectionList(us.getAgent(),ActionCode.NAR_RETRIEVAL_BILLED,
                establishmentCode, new Integer(800), c1.getTime(), c2.getTime(), null, null, "1");
            System.out.println("claims: " + claims);
            Assert.assertEquals("Retrieve Collection list more than 1. ", true, claims.size() > 0);
        } catch (CLSException e) {
            System.out.println("Retrieve Collection List take CLSException: " + e);
        } catch (MalformedURLException e) {
            System.out.println("Retrieve Collection List take MalformedURLException: " + e);
        } catch (Exception e) {
            System.out.println("Retrieve Collection List take Exception: " + e);
        }
        
    }
}
