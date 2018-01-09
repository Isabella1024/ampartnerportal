package com.asiamiles.partnerportal.web.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *

 */
public class QRCodeReaderControllerTest extends TestCase {
	private QRCodeReaderController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		controller = new QRCodeReaderController();
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest();
		
		SiteProperties siteProperties = new SiteProperties();
		Resource fileResource = new ClassPathResource("ampartnerportal.properties");
		siteProperties.setPlatform("LOCAL");
		siteProperties.setPropertiesPath(fileResource);
		controller.setSiteProperties(siteProperties);
		
		URLHelper urlHelper = new URLHelper();
		urlHelper.setSiteProperties(siteProperties);
		controller.setUrlHelper(urlHelper);
		
		
		
		
	}

	
	/** test to access url before login
	 * 
	 * @throws Exception
	 */
	public void testNoRequestParameter() throws Exception {
		ModelAndView m =controller.displayQRCodeReader(request, response);
		assertEquals(ViewConstants.INSTANT_REDEEM_QRCODE_READER, m.getViewName());
	}
	
	/** test to access allowUrl after login
	 * @throws Exception
	 */
	public void testHaveRequestParameter() throws Exception {
		
		request.setContextPath("amPartner");
		request.setServletPath("/instantRedeem/redemption.do");
		request.setParameter("memberId", "10");
		request.setParameter("claimNO", "20");
		request.setParameter("securityCode", "30");
		assertNull(controller.displayQRCodeReader(request, response));
	}
}
