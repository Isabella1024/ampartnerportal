package com.asiamiles.partnerportal.web.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

/**
 * Interceptor for filtering logged-in sessions by IP address.
 * 
 * @author CPPBENL
 *
 */
public class IPAddressFilterInterceptor extends HandlerInterceptorAdapter {

	Logger logger = AppLogger.getAppLogger(); 
	
	public static final String PARTNER_PREFIX = "partner_";
	public static final String INTERNAL_SUPPORT = "cx_support";
	
	//	Request Header for Akamai
	public static final String IP_HEADER_NAME = "X-Client-IP";
	
	private SiteProperties ipAddressProperties;
	
	
	/**
	 * @return Returns the siteProperties.
	 */
	public SiteProperties getIpAddressProperties() {
		return ipAddressProperties;
	}
	/**
	 * @param siteProperties The siteProperties to set.
	 */
	public void setIpAddressProperties(SiteProperties ipAddressProperties) {
		this.ipAddressProperties = ipAddressProperties;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		UserSession userSession = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		
		//Not logged in - allow for the meantime
		if (userSession == null) {
			return true;
		}
		Agent agent = userSession.getAgent();
		
		String remoteAddr = getRemoteIP(request);
		
		List allowedIpAddresses = ipAddressProperties.getPropertyAsList(PARTNER_PREFIX + agent.getPartnerCode());
		List internalIpAddresses = ipAddressProperties.getPropertyAsList(INTERNAL_SUPPORT);
		
		//If the partner does not have IP Address restriction (i.e. no entries in the IP Address list) -> allow
		if (allowedIpAddresses.isEmpty()) {
			logger.debug("No IP Address filter for partner " + agent.getPartnerCode());
			return true;
		}
		
		// Check if remote address is CX Support, if so then allow
		if (isRemoteAddressAllowed(remoteAddr, internalIpAddresses)) {
			logger.debug("Accessing Partner " + agent.getPartnerCode() + " from internal IP Address " + remoteAddr);
			return true;
		}
		
		// If the remote address fits into one of the partner's IP address ranges, then allow
		if (isRemoteAddressAllowed(remoteAddr, allowedIpAddresses)) {
			return true;
		}
		
		//DENY - redirect back to login page + cleaning the session
		logger.warn("Access denied for request coming from " + remoteAddr + " for partner " + agent.getPartnerCode());
		
		//Clean up Session
		HttpSession session  = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		Cookie cookie = new Cookie("amPartnerLogin", null);
		cookie.setMaxAge(0); //expires immediately
		cookie.setPath("/amPartner");
		response.addCookie(cookie);

		
		ModelAndView modelAndView = new ModelAndView("LoginForm");
		modelAndView.addObject("messageCode", "error_forbidden_location");
		throw new ModelAndViewDefiningException(modelAndView);
	}
	
	protected boolean isRemoteAddressAllowed(String remoteAddr, List allowedAddressRegexes) {
		
		for (Iterator it = allowedAddressRegexes.iterator(); it.hasNext();) {
			String regex = (String)it.next();
			
			//Replaces "." with "\." and "*" with "[0-9].*" to fit into the regex match 
			regex = regex.replaceAll("\\.", "\\\\.").replaceAll("\\*", "[0-9.]*");
			Pattern pattern = Pattern.compile(regex);
			if (pattern.matcher(remoteAddr).matches()) {
				return true;
			}
		}
		
		return false;
	}
	
    /**
     * <P>This method is to get the IP Address of a Remote Application</P>
     * @param request  HttpServletRequest object
     * @return String  IP Address
     */
    public static String getRemoteIP(HttpServletRequest request) {
		if (StringUtils.isNotEmpty(request.getHeader(IP_HEADER_NAME))) {
			return request.getHeader(IP_HEADER_NAME);
		} else {
			return request.getRemoteAddr();
		}
	}
}
