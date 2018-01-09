/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.interceptor;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.CryptoHelper;
import com.cathaypacific.utility.Logger;

/**
 * Interceptor for authenticating WebCLS users (which is different from normal Partner Portal users).
 * 
 * @author CPPBENL
 * 
 */
public class CLSAdminInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = AppLogger.getAppLogger();
	
	private CryptoHelper cryptoHelper;
	
	/**
	 * @param cryptoHelper The cryptoHelper to set.
	 */
	public void setCryptoHelper(CryptoHelper cryptoHelper) {
		this.cryptoHelper = cryptoHelper;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		String requestURL = request.getServletPath();
		String query = request.getQueryString();

		if (userSession != null && StringUtils.isNotEmpty(userSession.getWebCLSAdminAgentID())) {
			//WebCLS Admin already authenticated - proceed
			return true;
		} 
		
		//decrypt input parameters and put them into session
		String md = request.getParameter("md");
		
		
		if (StringUtils.isEmpty(md)) {
			if (getWebCLSLoginCookie(request) != null){
				//Prevously authenticated by session expired
				throw new SystemException(SystemException.WEBCLS_SESSION_TIMEOUT, "WebCLS Session has timed out. Please open the maintenance window again.");
			} else {
				throw new SystemException(SystemException.WEBCLS_INVALID_REQUEST, "Missing Request Parameters");
			}
		} 
 
		
		String decryptedParameters;
		try {
			decryptedParameters = cryptoHelper.decrypt(md);
			logger.debug("Decrypted Parameter String: " + decryptedParameters);
		} catch (SystemException e) {
			throw new SystemException(SystemException.WEBCLS_INVALID_REQUEST, "Missing Request Parameters", e);
		}
		Map params = getParameterMap(decryptedParameters);
		
		String agentID = (String)params.get("AgentID");
		String sTimestamp = (String)params.get("timestamp");
		
		if (StringUtils.isEmpty(agentID) || StringUtils.isEmpty(sTimestamp)) {
			throw new SystemException(SystemException.WEBCLS_INVALID_REQUEST, "Missing Request Parameters");
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH:mm:ss"); 
		Date timestamp = dateFormat.parse(sTimestamp);
		Date now = new Date();
		
		if (timestamp.getTime() + (1000 * 60 * 30) >= now.getTime()) {
			//we are within the 30 minute limit
			
			//Stick WebCLS user into session
			userSession = new UserSession();
			userSession.setWebCLSAdminAgentID(agentID);
			WebUtils.setSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME, userSession);
			
			//Insert Cookie to advise user in the event of session timeout
			Cookie cookie = new Cookie("WebCLSAdmin", "1");
			cookie.setPath("/amPartner");
			cookie.setMaxAge(-1); //Expires when user closes browser
			response.addCookie(cookie);
			
			return true;
		} else {
			throw new SystemException(SystemException.WEBCLS_SESSION_TIMEOUT, "WebCLS Session has timed out. Please open the maintenance window again.");
		}
	}
	
	protected Map getParameterMap(String parameters) {
		String[] params = parameters.split("&");
		Map paramMap = new HashMap();
		
		for (int i = 0; i < params.length; i++ ) {
			String[] param = params[i].split("=");
			paramMap.put(param[0], param[1]);
		}
		return paramMap;
	}

    protected Cookie getWebCLSLoginCookie(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	for (int i = 0; i < cookies.length; i++) {
    		if (cookies[i].getName().equals("WebCLSAdmin")) {
    			return cookies[i];
    		}
    	}
    	return null;
    }
}
