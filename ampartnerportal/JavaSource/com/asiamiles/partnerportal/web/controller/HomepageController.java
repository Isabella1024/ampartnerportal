/*
 * Created on Jul 17, 2009
 *
 * 
 */
package com.asiamiles.partnerportal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * Controller to redirect logged-in users to their landing pages.
 * This is to be used in conjunction with <code>LoginInterceptor</code> to
 * redirect unauthenticated requests back to the Login page.
 * @author CPPBENL
 * @see com.asiamiles.partnerportal.web.interceptor.LoginInterceptor
 */
public class HomepageController extends AbstractController {

	private Logger logger = AppLogger.getAppLogger();
	
	private SiteProperties siteProperties;
	private URLHelper urlHelper;
	
	
	
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
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();
		String loggedInURL = null;
		logger.debug("homepageController request.getServletPath = " +request.getServletPath());
		if (StringUtils.equals(request.getServletPath(),siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_HOMEPAGE_URL))) {
			if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())) {
				//cppjox add for AML38938 20160602 start (set different URL for agents who assigned with Accrual normal)
				if(Agent.AGENT_CATEGORY_ACCRUL.equals(agent.getPartnerGroup())){
					loggedInURL = siteProperties.getProperty(SiteProperties.ACCRUL_NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL);
				//cppjox add for AML38938 20160602 end
				}else
				loggedInURL = siteProperties.getProperty(SiteProperties.NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL);
			} else if (Agent.AGENT_TYPE_SUPERVISOR.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.SUPERVISOR_INSTANT_REDEEM_LANDING_URL);			
			} else if (Agent.AGENT_TYPE_FINANCE_AGENT.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.FINANCE_AGENT_INSTANT_REDEEM_LANDING_URL);
			} else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_INSTANT_REDEEM_LANDING_URL);
			} else {
				//Unauthorised Agent Type - send back to login page 
				return new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM, "messageCode", "error_unknown_agent_type");
			}
		} else {
			if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())) {
				//cppjox add for AML38938 20160602 start
				if(Agent.AGENT_CATEGORY_ACCRUL.equals(agent.getPartnerGroup())){
					loggedInURL = siteProperties.getProperty(SiteProperties.ACCRUL_NORMAL_AGENT_LANDING_URL);
				////cppjox add for AML38938 20160602 end
				}else
				loggedInURL = siteProperties.getProperty(SiteProperties.NORMAL_AGENT_LANDING_URL);
			} else if (Agent.AGENT_TYPE_SUPERVISOR.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.SUPERVISOR_LANDING_URL);			
			} else if (Agent.AGENT_TYPE_FINANCE_AGENT.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.FINANCE_AGENT_LANDING_URL);
			} else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())) {
				loggedInURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_LANDING_URL);
			} else {
				//Unauthorised Agent Type - send back to login page 
				return new ModelAndView("LoginForm", "messageCode", "error_unknown_agent_type");
			}
		}
		String secureLoggedInURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), loggedInURL, null, urlHelper.isSSL(request));
		logger.debug("Forwarding request to " + secureLoggedInURL);
		response.sendRedirect(secureLoggedInURL);
		return null;
	}

}
