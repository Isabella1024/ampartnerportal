/*
 * Created on Jun 5, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.util.StringUtil;
import com.cathaypacific.utility.Logger;

/**
 * <code>MultiActionController</code> to facilitate the display of the login form,
 * authenticating the user and logging out the user.
 * @author CPPBENL
 *
 */
public class LoginController extends MultiActionController{
	
	private CLSFacade clsFacade;
	private SiteProperties siteProperties;
	private URLHelper urlHelper;
	
	private Logger logger = AppLogger.getAppLogger();
	private Logger waLogger = AppLogger.getWALogger();
	

	/**
	 * Displays the Login Form
	 * @param request the HTTP Request
	 * @param response the HTTP Response
	 * @return
	 */
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(ViewConstants.LOGIN_FORM);
	}
	
	/**
	 * Displays the instantRedeem Login Form
	 * @param request the HTTP Request
	 * @param response the HTTP Response
	 * @return
	 */
	public ModelAndView instantRedeemLoginForm(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM);
	}

	
	/**
	 * Authenticates the user request.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String agentId = request.getParameter("agentId");
		String password = request.getParameter("password");
		String requestURL = request.getServletPath();
		String forwardAction = request.getParameter("forwardAction");

		Map model = new HashMap();
		model.put("agentId", agentId);
		
		if (StringUtils.isEmpty(agentId) || StringUtils.isEmpty(password)) {
			model.put("messageCode", "error_login_info");
			if (requestURL.indexOf("/instantRedeem") >= 0) {
				return forwardSess(forwardAction, new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, model));
			} else {
				return new ModelAndView(ViewConstants.LOGIN_FORM, model);
			}
		} else {
			if (StringUtils.isNotEmpty(agentId) && !StringUtils.isAsciiPrintable(agentId)) {
				model.put("messageCode", "invalidFormat_agentID");
				if (requestURL.indexOf("/instantRedeem") >= 0) {
					return forwardSess(forwardAction, new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, model));
				} else {
					return new ModelAndView(ViewConstants.LOGIN_FORM, model);
				}
			} else if(StringUtils.isNotEmpty(password) && !StringUtils.isAsciiPrintable(password)) {
				model.put("messageCode", "invalidFormat_password");
				if (requestURL.indexOf("/instantRedeem") >= 0) {
					return forwardSess(forwardAction, new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, model));
				} else {
					return new ModelAndView(ViewConstants.LOGIN_FORM, model);
				}				
			}
		}
		
		try {
			logger.debug("Attempting to authenticate user using [" + agentId + ", " + password + "]");
			Agent agent = clsFacade.loginAgent(agentId, password);
			//Successfully logged in -> write to WA Log
			waLogger.info(request.getRequestURL() + "?agentID=" + agentId);
			
			UserSession session = new UserSession();
			session.setAgent(agent);
			WebUtils.setSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME, session);
			
			Cookie cookie = new Cookie("amPartnerLogin", "1");
			cookie.setMaxAge(-1); //expires when user closes browser
			cookie.setPath("/amPartner");
			response.addCookie(cookie);
			
			if (forwardAction != null) {
				String secureForwardAction = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), forwardAction, null, urlHelper.isSSL(request));
				logger.debug("Forwarding request to " + secureForwardAction);
				response.sendRedirect(secureForwardAction);
				return null;
			}
			String homepageURL = "";
			if (requestURL.indexOf("/instantRedeem") >= 0) {
				homepageURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_HOMEPAGE_URL);
			} else {
				homepageURL = siteProperties.getProperty(SiteProperties.HOMEPAGE_URL);
			}
			String secureHomepageURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), homepageURL, null, urlHelper.isSSL(request));
			logger.debug("Forwarding request to " + secureHomepageURL);
			response.sendRedirect(secureHomepageURL);
			return null;

		} catch (CLSException e) {
			logger.debug("Login Failure due to caught Exception", e);
			waLogger.info(request.getRequestURL() + "?agentID=" + agentId + "&status=" + e.getErrorCode());
			model.put("clsexception", e);
			if (requestURL.indexOf("/instantRedeem") >= 0) {
				return forwardSess(forwardAction, new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, model)); 
			} else {
				return new ModelAndView(ViewConstants.LOGIN_FORM, model);
			}
		}
	
	}
	
	
	/**
	 * Logs out the user.
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		UserSession userSession = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		if (userSession != null) {
			WebUtils.setSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME, null);
		}
		HttpSession session  = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		Cookie cookie = new Cookie("amPartnerLogin", null);
		cookie.setMaxAge(0); //expires immediately
		cookie.setPath("/amPartner");
		response.addCookie(cookie);
		
		if (request.getServletPath().indexOf("/instantRedeem") >= 0) {
			return new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, "messageCode", "message_success_logout");
		} else {
			return new ModelAndView(ViewConstants.LOGIN_FORM, "messageCode", "message_success_logout");
		}
	}
	

	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	/**
	 * @param siteProperties The siteProperties to set.
	 */
	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}
	
	/**
	 * @param urlHelper The urlHelper to set.
	 */
	public void setUrlHelper(URLHelper urlHelper) {
		this.urlHelper = urlHelper;
	}
	
	private ModelAndView forwardSess(String forwardAction, ModelAndView model){
		if(!StringUtil.isEmpty(forwardAction)){
			forwardAction = forwardAction.substring(forwardAction.indexOf("/instantRedeem"));
			Pattern langPattern = Pattern.compile("lang=[a-zA-Z]{2}&");
			Matcher langMatcher = langPattern.matcher(forwardAction);
			String fa = langMatcher.replaceAll(""); 
			logger.debug("forwardSess="+fa);
			return model.addObject("loginForwardAction", fa);
		}
		return model;
	}
}
