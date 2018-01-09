/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.domain.UserSession;

/**
 * Interceptor for populating the MDC value for Log4j
 * 
 * @author CPPBENL
 * 
 */
public class Log4jMDCInterceptor extends HandlerInterceptorAdapter {

	//Request Header for Akamai
	public static final String IP_HEADER_NAME = "X-Client-IP";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		String sessionId = WebUtils.getSessionId(request);
		
		MDC.put("remoteIP", getRemoteIP(request));
		
		if (StringUtils.isNotEmpty(sessionId)) {
			MDC.put("SessionID", sessionId);
		} else {
			MDC.remove("SessionID");
		}
		
		if (session != null && session.getAgent() != null) {
			MDC.put("AgentID", session.getAgent().getAgentID());
			MDC.put("PartnerCode", session.getAgent().getPartnerCode());
		} else if (session != null && StringUtils.isNotEmpty(session.getWebCLSAdminAgentID())) {
			MDC.put("AgentID", "WebCLS - " + session.getWebCLSAdminAgentID());
		} else {
			MDC.remove("AgentID");
			MDC.remove("PartnerCode");
		}
		
		return true;
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
