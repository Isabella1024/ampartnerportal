package com.asiamiles.partnerportal.web.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *

 */
public class IPAddressFilterInterceptorTest extends TestCase {

	private IPAddressFilterInterceptor interceptor;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private UserSession userSession;
	private Agent agent;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
		userSession = new UserSession();
		agent = new Agent();
		userSession.setAgent(agent);
		
		request.setSession(session);
		session.setAttribute(UserSession.SESSION_ATTRIBUTE_NAME, userSession);
		
		
		interceptor = new IPAddressFilterInterceptor();
		
		SiteProperties ipAddresses = new SiteProperties();
		Resource fileResource = new ClassPathResource("permittedIPAddresses.properties");
		ipAddresses.setPlatform("LOCAL");
		ipAddresses.setPropertiesPath(fileResource);
		
		interceptor.setIpAddressProperties(ipAddresses);
		
	}

	/*
	 * Class under test for boolean preHandle(HttpServletRequest, HttpServletResponse, Object)
	 */
	public void testPartnerWithNoFilter() throws Exception {
		agent.setPartnerCode("000");
		request.setRemoteAddr("127.0.0.1");
		assertTrue(interceptor.preHandle(request, response, null));
	}
	
	public void testPartner192() throws Exception{
		agent.setPartnerCode("192");
		request.setRemoteAddr("192.168.0.1");
		assertTrue(interceptor.preHandle(request, response, null));
		
		try {
			request.setRemoteAddr("203.203.0.203");
			interceptor.preHandle(request, response, null);
			fail();
		} catch (Exception e) {
			
		}
	}
	
	public void testPartner203() throws Exception {
		agent.setPartnerCode("203");
		request.setRemoteAddr("203.168.0.1");
		assertTrue(interceptor.preHandle(request, response, null));
		
		try {
			request.setRemoteAddr("192.203.0.203");
			interceptor.preHandle(request, response, null);
			fail();
		} catch (Exception e) {
			
		}
		
		
	}
	
	public void testIsRemoteAddressAllowed() throws Exception {
		List regexes = new ArrayList();
		regexes.add("192.168.0.1");
		
		
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.0.1", regexes));
		assertFalse(interceptor.isRemoteAddressAllowed("192.168.0.2", regexes));
		assertFalse(interceptor.isRemoteAddressAllowed("19201680001", regexes));
		
		regexes.clear();
		regexes.add("192.168.0.*");
		
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.0.1", regexes));
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.0.2", regexes));
		assertFalse(interceptor.isRemoteAddressAllowed("192.168.1.1", regexes));
		
		regexes.clear();
		regexes.add("192.168.*");
		
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.0.1", regexes));
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.0.2", regexes));
		assertTrue(interceptor.isRemoteAddressAllowed("192.168.1.1", regexes));
		assertFalse(interceptor.isRemoteAddressAllowed("192.169.1.1", regexes));
		
	}
	
	public void testRegexes() throws Exception {
		assertTrue(Pattern.matches("192\\.168\\.0\\.1", "192.168.0.1"));
		assertFalse(Pattern.matches("192\\.168\\.0\\.1", "192.168.0.2"));
		assertFalse(Pattern.matches("192\\.168\\.0\\.1", "19201680001"));
		
		assertTrue(Pattern.matches("192\\.168\\.0\\.[0-9.]*", "192.168.0.1"));
		assertTrue(Pattern.matches("192\\.168\\.0\\.[0-9.]*", "192.168.0.2"));
		assertFalse(Pattern.matches("192\\.168\\.0\\.[0-9.]*", "192.168.1.1"));
		assertFalse(Pattern.matches("192\\.168\\.0\\.[0-9.]*", "19201680001"));
		
		
		assertTrue(Pattern.matches("192\\.168\\.[0-9.]*", "192.168.0.1"));
		assertTrue(Pattern.matches("192\\.168\\.[0-9.]*", "192.168.0.2"));
		assertTrue(Pattern.matches("192\\.168\\.[0-9.]*", "192.168.1.1"));
		assertFalse(Pattern.matches("192\\.168\\.[0-9.]*", "192.169.0.1"));
	}
}
