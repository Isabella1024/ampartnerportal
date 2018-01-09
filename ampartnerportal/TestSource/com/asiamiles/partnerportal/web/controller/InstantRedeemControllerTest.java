package com.asiamiles.partnerportal.web.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.domain.logic.PaperlessRedemptionFormValidator;
import com.asiamiles.partnerportal.str.STRFacade;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;


/**
 * @author CPPBENL
 *

 */
public class InstantRedeemControllerTest extends MockObjectTestCase {
	private InstantRedeemController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private UserSession userSession;
	private Agent agent;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest("POST","instantRedeemForm");
		session = new MockHttpSession();
		controller = new InstantRedeemController();
		
		SiteProperties siteProperties = new SiteProperties();
		Resource fileResource = new ClassPathResource("ampartnerportal.properties");
		siteProperties.setPlatform("LOCAL");
		siteProperties.setPropertiesPath(fileResource);
		controller.setSiteProperties(siteProperties);
		
		URLHelper urlHelper = new URLHelper();
		urlHelper.setSiteProperties(siteProperties);
		
		controller.setUrlHelper(urlHelper);
		
		userSession = new UserSession();
		agent = new Agent();
		agent.setAdministratorIndicator("A");
		userSession.setAgent(agent);
		session.setAttribute(UserSession.SESSION_ATTRIBUTE_NAME, userSession);
		request.setSession(session);
		request.setContextPath("amPartner");
		
//		STRFacade strFacade = new STRFacade();
//		strFacade.setSiteProperties(siteProperties);
//		strFacade.setMessageMapHandler(MessageMapHandler.getInstance());
//		PaperlessRedemptionFormValidator vd = new PaperlessRedemptionFormValidator();
//		vd.setStrFacade(strFacade);
//		controller.setValidator(vd);
		
//		SiteProperties clsProperties = new SiteProperties();
//		Resource clsResource = new ClassPathResource("clsInterface.properties");
//		clsProperties.setPlatform("LOCAL");
//		clsProperties.setPropertiesPath(clsResource);
//		CLSFacade clsFacade = new CLSFacade();
//		clsFacade.setClsProperties(clsProperties);
//		clsFacade.setHttpConnectionHelper(new HTTPConnectionHelper());
//		controller.setClsFacade(clsFacade);
	}
		
	/** test finish flow
	 * @throws Exception
	 */
	public void testFinish() throws Exception {
		Mock strInstanseMockManger = mock(STRFacade.class);
		strInstanseMockManger.expects(atLeastOnce()).method("getMessage").withAnyArguments().will(returnValue("remarks"));              
		STRFacade strFacade = (STRFacade) strInstanseMockManger.proxy();
		PaperlessRedemptionFormValidator vd = new PaperlessRedemptionFormValidator();
		vd.setStrFacade(strFacade);
		controller.setValidator(vd);
		
		request.addParameter("memberId", "1910000084");
		request.addParameter("claimNO", "7000982");
		request.addParameter("securityCode", "04609715109");
		List NARDetails1 = new ArrayList();
		NARClaimDetails narClaimDetails = new NARClaimDetails();
		narClaimDetails.setPaperlessClaim(new PaperlessClaim());
		NARDetails1.add(narClaimDetails);
		Map claimsMap1 = new HashMap();
		claimsMap1.put("MemberName", "peter");
		claimsMap1.put("NARDetails",NARDetails1);
		Mock clsInstanseMockManger = mock(CLSFacade.class);
	    clsInstanseMockManger.expects(once()).method("retrieveClaims").withAnyArguments().will(returnValue(claimsMap1));              
	    CLSFacade clsFacade = (CLSFacade) clsInstanseMockManger.proxy();
	    controller.setClsFacade(clsFacade);

		ModelAndView mav = controller.handleRequest(request, response);
		request.addParameter(InstantRedeemController.PARAM_FINISH, "value");
		mav = controller.handleRequest(request, response);
		assertEquals(ViewConstants.INSTANT_REDEEM_ACKNOWLEDGEMENT, mav.getViewName());
	}
	
	/** test process flow
	 * @throws Exception
	 */
	public void testProcess() throws Exception {
		PaperlessRedemptionFormValidator vd = new PaperlessRedemptionFormValidator();
		controller.setValidator(vd);
		
		request.addParameter("memberId", "1910000084");
		request.addParameter("claimNO", "7000982");
		request.addParameter("securityCode", "04609715109");
		List NARDetails1 = new ArrayList();
		NARClaimDetails narClaimDetails = new NARClaimDetails();
		narClaimDetails.setPaperlessClaim(new PaperlessClaim());
		NARDetails1.add(narClaimDetails);
		Map claimsMap1 = new HashMap();
		claimsMap1.put("MemberName", "peter");
		claimsMap1.put("NARDetails",NARDetails1);
		Mock clsInstanseMockManger = mock(CLSFacade.class);
	    clsInstanseMockManger.expects(once()).method("retrieveClaims").withAnyArguments().will(returnValue(claimsMap1));              
	    CLSFacade clsFacade = (CLSFacade) clsInstanseMockManger.proxy();
	    controller.setClsFacade(clsFacade);

		ModelAndView mav = controller.handleRequest(request, response);
		assertEquals("instantRedeem/CollectClaims", mav.getViewName());
	}
	
	/** test cancel flow
	 * @throws Exception
	 */

	public void testCancel() throws Exception {
		PaperlessRedemptionFormValidator vd = new PaperlessRedemptionFormValidator();
		controller.setValidator(vd);
		
		ModelAndView mav = controller.handleRequest(request, response);
		request.addParameter(InstantRedeemController.PARAM_CANCEL, "value");
		mav = controller.handleRequest(request, response);
		assertNull(mav);
		assertEquals("http://localhost:80/amPartner/instantRedeem/qrCodeReader.do", response.getRedirectedUrl());
			
	}
	
	

}
