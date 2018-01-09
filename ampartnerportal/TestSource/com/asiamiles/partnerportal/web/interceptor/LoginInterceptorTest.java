package com.asiamiles.partnerportal.web.interceptor;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.URLHelper;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author CPPBENL
 *

 */
public class LoginInterceptorTest extends TestCase {
	private LoginInterceptor interceptor;
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
		request = new MockHttpServletRequest();
		session = new MockHttpSession();
		interceptor = new LoginInterceptor();
		
		SiteProperties siteProperties = new SiteProperties();
		Resource fileResource = new ClassPathResource("ampartnerportal.properties");
		siteProperties.setPlatform("LOCAL");
		siteProperties.setPropertiesPath(fileResource);
		interceptor.setSiteProperties(siteProperties);
		
		URLHelper urlHelper = new URLHelper();
		urlHelper.setSiteProperties(siteProperties);
		
		interceptor.setUrlHelper(urlHelper);
		
		
		
	}

	
	/** test to access url before login
	 * 
	 * @throws Exception
	 */
	public void testNoLogin() throws Exception {
		Throwable tx = null;
		request.setServletPath("/instantRedeem/redemption.do");
		request.setContextPath("amPartner");
		try {
			interceptor.preHandle(request, response, null);
			Assert.fail("didn't throw ModelAndViewDefiningException, test failed");
		} catch (ModelAndViewDefiningException e) {
			tx = e;
		}
		assertEquals(ModelAndViewDefiningException.class, tx.getClass());
	}
	
	/** test to access allowUrl after login
	 * @throws Exception
	 */
	public void testLoggedinAllowUrl() throws Exception {
		userSession = new UserSession();
		agent = new Agent();
		agent.setAdministratorIndicator("A");
		userSession.setAgent(agent);
		request.setSession(session);
		session.setAttribute(UserSession.SESSION_ATTRIBUTE_NAME, userSession);
		request.setServletPath("/instantRedeem/redemption.do");
		request.setContextPath("amPartner");
		boolean returnBoolean = false;
		try {
			returnBoolean = interceptor.preHandle(request, response, null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		assertTrue(returnBoolean);
		assertNull(response.getRedirectedUrl());
	}
	
	/** test to access unallowUrl after login
	 * @throws Exception
	 */
	public void testLoggedinNotAllowUrl() throws Exception {
		userSession = new UserSession();
		agent = new Agent();
		agent.setAdministratorIndicator("A");
		userSession.setAgent(agent);
		request.setSession(session);
		session.setAttribute(UserSession.SESSION_ATTRIBUTE_NAME, userSession);
		request.setServletPath("/instantRedeem/abcd.do");
		request.setContextPath("amPartner");
		boolean returnBoolean = false;
		try {
			returnBoolean = interceptor.preHandle(request, response, null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		assertTrue(returnBoolean);
		assertEquals("http://localhost:80/amPartner/instantRedeem/qrCodeReader.do", response.getRedirectedUrl());
	}
	
	
}
