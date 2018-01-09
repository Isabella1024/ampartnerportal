package com.asiamiles.partnerportal.web.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.cls.CLSInterfaceProperties;
import com.asiamiles.partnerportal.domain.ReportForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.HTTPConnectionHelper;

/**
 * Unit test for Report Controller
 * TODO: write description for this class.
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
@RunWith(PowerMockRunner.class)
public class ReportControllerTest extends TestCase {
    
    @Mock
    ReportController reportController;
    
    @Mock
    private CLSFacade clsFacade;
    
    @Mock
    private SiteProperties clsProperties;
    
    @Mock
    MockHttpServletRequest request;
    
    @Mock
    Errors errors;
    
    ReportForm reportForm;

    @Before
    public void setUp() throws Exception {
        reportController = PowerMockito.spy(new ReportController());
        request = new MockHttpServletRequest();
        
        clsFacade = PowerMockito.spy(new CLSFacade());
        clsProperties = PowerMockito.mock(SiteProperties.class);
        clsFacade.setClsProperties(clsProperties);
        clsFacade.setHttpConnectionHelper(new HTTPConnectionHelper());
        
        reportController.setClsFacade(clsFacade);
        
        errors = PowerMockito.mock(Errors.class);
        
    }
    
    /**
     * Unit test for method formBackingObject.
     * 
     * @param us
     * @param nextPageCode
     * @param prePageCode
     */
    public void testFormBackingObject(UserSession us, String nextPageCode, String prePageCode){
        if(null != nextPageCode && !"".equals(nextPageCode)){
            request.setParameter("nextPageCode", nextPageCode);
        }
        if(null != prePageCode && !"".equals(prePageCode)){
            request.setParameter("prePageCode", prePageCode);
        }
        request.getSession().setAttribute("userSession", us);
        try {
            MockForRetrieveCollectionList();
            reportForm = (ReportForm) reportController.formBackingObject(request);
        } catch (ModelAndViewDefiningException e) {
            System.out.println("execute method formBackingObject take ModelAndViewDefiningException: " + e);
        } catch (Exception e) {
            System.out.println("execute method formBackingObject take exception: " + e);
        }
    }
    
    /**
     * 
     * Verify the result.
     * 
     * @param currentPage
     * @param totalNum
     * @param totalPage
     * @param nextPackageCode
     * @param nextClaimNum
     * @param prePackageCode
     * @param preClaimNum
     */
    public void verifyResult(String currentPage, String totalNum, String totalPage, String nextPackageCode, String nextClaimNum
        , String prePackageCode, String preClaimNum) {
        boolean expectedResult = false;
        String expectedStr = "The result is expected: currentPage = #currentPage#, " +
        		"totalNum = #totalNum#, totalPage = #totalPage#, " +
        		"nextPackageCode = #nextPackageCode#, nextClaimNum = #nextClaimNum#, " +
        		"prePackageCode = #prePackageCode#, preClaimNum = #preClaimNum# ";
        String notExpectedStr = "The result is not expected: " +
        		    "currentPage = #currentPage# ( " + reportForm.getCurrentPage() + "), " +
        		    "totalNum = #totalNum# (" + reportForm.getTotalNum() + "), " +
        		    "totalPage = #totalPage# (" + reportForm.getTotalPage() + "), " +
                    "nextPackageCode = #nextPackageCode# ( " + reportForm.getNextPackageCode() + "), " +
            		"nextClaimNum = #nextClaimNum# ( " + reportForm.getNextClaimNum() + "), " +
            		"prePackageCode = #prePackageCode# ( " +reportForm.getPrePackageCode() + "), " +
            		"preClaimNum = #preClaimNum# (" + reportForm.getPreClaimNum() + ") ";
        if(totalNum.equals(reportForm.getTotalNum()) && totalPage.equals(reportForm.getTotalPage()) 
            && nextPackageCode.equals(reportForm.getNextPackageCode()) && nextClaimNum.equals(reportForm.getNextClaimNum())
            && prePackageCode.equals(reportForm.getPrePackageCode()) && preClaimNum.equals(reportForm.getPreClaimNum())){
            expectedResult = true;
            expectedStr = expectedStr.replace("#currentPage#", currentPage);
            expectedStr = expectedStr.replace("#totalNum#", totalNum).replace("#totalPage#", totalPage);
            expectedStr = expectedStr.replace("#nextPackageCode#", nextPackageCode).replace("#nextClaimNum#", nextClaimNum);
            expectedStr = expectedStr.replace("#prePackageCode#", prePackageCode).replace("#preClaimNum#", preClaimNum);
            System.out.println(expectedStr);
        } else {
            notExpectedStr = notExpectedStr.replace("#currentPage#", currentPage);
            notExpectedStr = notExpectedStr.replace("#totalNum#", totalNum).replace("#totalPage#", totalPage);
            notExpectedStr = notExpectedStr.replace("#nextPackageCode#", nextPackageCode).replace("#nextClaimNum#", nextClaimNum);
            notExpectedStr = notExpectedStr.replace("#prePackageCode#", prePackageCode).replace("#preClaimNum#", preClaimNum);
            System.out.println(notExpectedStr);
        }
        Assert.assertEquals(expectedResult ? expectedStr : notExpectedStr, true, expectedResult);
    }
    
    /**
     * 
     * Mock for method RetrieveCollectionList in class CLSFacade.
     *
     */
    public void MockForRetrieveCollectionList(){
        try {
            Mockito.when(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT)).thenReturn("30000");
            HashMap credentialsMap = new HashMap();
            credentialsMap.put("sAppID", "paperlessNAR");
            credentialsMap.put("sPassword", "95fs6521fa");
            
            PowerMockito.doReturn(credentialsMap).when(clsFacade, "getCredentials", CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL);
            URL url = new URL("http://ixservice.ete.cathaypacific.com:80/ixClsNARPortal/PaperlessCollectionRetrievalServlet");
            PowerMockito.doReturn(url).when(clsFacade, "getURLForApp", CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL);
            
        } catch (CLSException e) {
            System.out.println("Retrieve Collection List take CLSException: " + e);
        } catch (MalformedURLException e) {
            System.out.println("Retrieve Collection List take MalformedURLException: " + e);
        } catch (Exception e) {
            System.out.println("Retrieve Collection List take Exception: " + e);
        }
    }
    
    /**
     * 
     * Unit test for method postProcessPage.
     * 
     * @param us
     * @param rf
     */
    public void testPostProcessPage(UserSession us, ReportForm rf){
        request.getSession().setAttribute("userSession", us);
        try {
            Mockito.when(errors.hasErrors()).thenReturn(false);
            MockForRetrieveCollectionList();
            reportController.postProcessPage(request, rf, errors, 0);
            reportForm = rf;
        } catch (ModelAndViewDefiningException e) {
            System.out.println("execute method postProcessPage take ModelAndViewDefiningException: " + e);
        } catch (Exception e) {
            System.out.println("execute method postProcessPage take exception: " + e);
        }
    }
}
