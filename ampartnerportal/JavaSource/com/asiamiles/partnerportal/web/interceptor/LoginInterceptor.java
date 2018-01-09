/*
 * Created on Jun 11, 2009
 *

 */
package com.asiamiles.partnerportal.web.interceptor;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Interceptor for verifying that the request has been properly authenticated 
 * and authorised to access the requested URL.
 * 
 * @author CPPBENL
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private SiteProperties siteProperties;
    private URLHelper urlHelper;
    private Logger logger = AppLogger.getAppLogger();

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
      * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
      */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        String requestURL = request.getServletPath();
        String query = request.getQueryString();
        ModelAndView modelAndView;
        if (requestURL.indexOf("instantRedeem") >= 0) {
    		modelAndView = new ModelAndView(ViewConstants.INSTANT_REDEEM_LOGIN_FORM);
    	} else {
    		modelAndView = new ModelAndView(ViewConstants.LOGIN_FORM);
    	}
        //Check whether a UserSession Exists. If not then redirect the user to the login page
        if (userSession == null) {
            if (query != null) {
                modelAndView.addObject("loginForwardAction", requestURL + "?" + query);
            } else {
                modelAndView.addObject("loginForwardAction", requestURL);
            }
            
            if (getLoginCookie(request) != null) {
            	modelAndView.addObject("messageCode", "error_session_timeout");
            }
            
            throw new ModelAndViewDefiningException(modelAndView);
        } else {
            //Check that the logged-in user has rights to access the URL.
            //If not then redirect the user to its user-group's landing page.

            Agent agent = userSession.getAgent();
            List allowedURLs = new ArrayList();
            String landingURL = "";

            

            if (agent == null) {
            	/* Either the user visited the clsAgentAdmin page (in which case the agent is null),
            	 * or the session went dodgy -> redirect back to Login page.
            	 */
                WebUtils.setSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME, null);
                throw new ModelAndViewDefiningException(modelAndView);            	
            }
            
            logger.info("agentGroup:"+agent.getPartnerGroup());
            logger.info("agentType:"+agent.getAdministratorIndicator());
            if (requestURL.indexOf("/instantRedeem") >= 0) {
            	if (Agent.AGENT_CATEGORY_REDEMPTION.equals(agent.getPartnerGroup())){
	            	if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())) {
	            		allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.NORMAL_AGENT_ALLOWED_URL_LIST));
		                landingURL = siteProperties.getProperty(SiteProperties.NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL);
	        		} else if (Agent.AGENT_TYPE_SUPERVISOR.equals(agent.getAdministratorIndicator())) {
	        			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.SUPERVISOR_ALLOWED_URL_LIST));
	        			landingURL = siteProperties.getProperty(SiteProperties.SUPERVISOR_INSTANT_REDEEM_LANDING_URL);			
	        		} else if (Agent.AGENT_TYPE_FINANCE_AGENT.equals(agent.getAdministratorIndicator())) {
	        			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.FINANCE_AGENT_ALLOWED_URL_LIST));
	        			landingURL = siteProperties.getProperty(SiteProperties.FINANCE_AGENT_INSTANT_REDEEM_LANDING_URL);
	        		} else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())) {
	        			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ADMINISTRATOR_ALLOWED_URL_LIST));
	        			landingURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_INSTANT_REDEEM_LANDING_URL);
	        		}
            	}else if(Agent.AGENT_CATEGORY_ACCRUL.equals(agent.getPartnerGroup())){
            		if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())){
            			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ACCRUL_NORMAL_ALLOWED_URL_LIST));
            			landingURL = siteProperties.getProperty(SiteProperties.ACCRUL_NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL);
            		}else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())){
            			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ACCRUL_ADMINISTRATOR_ALLOWED_URL_LIST));
            			landingURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_INSTANT_REDEEM_LANDING_URL);
            		}
            	}

            } else {
            	if (Agent.AGENT_CATEGORY_REDEMPTION.equals(agent.getPartnerGroup())){
		            if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())) {
		                allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.NORMAL_AGENT_ALLOWED_URL_LIST));
		                landingURL = siteProperties.getProperty(SiteProperties.NORMAL_AGENT_LANDING_URL);
		            } else if (Agent.AGENT_TYPE_SUPERVISOR.equals(agent.getAdministratorIndicator())) {
		                allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.SUPERVISOR_ALLOWED_URL_LIST));
		                landingURL = siteProperties.getProperty(SiteProperties.SUPERVISOR_LANDING_URL);                    
		            } else if (Agent.AGENT_TYPE_FINANCE_AGENT.equals(agent.getAdministratorIndicator())) {
		                allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.FINANCE_AGENT_ALLOWED_URL_LIST));
		                landingURL = siteProperties.getProperty(SiteProperties.FINANCE_AGENT_LANDING_URL);
		            } else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())) {
		                allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ADMINISTRATOR_ALLOWED_URL_LIST));
		                landingURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_LANDING_URL);
		            } else {
		                //Unauthorised Agent - redirect to login page
		                WebUtils.setSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME, null);
		                throw new ModelAndViewDefiningException(modelAndView);
		            }
            	}else if (Agent.AGENT_CATEGORY_ACCRUL.equals(agent.getPartnerGroup())){
            		if (Agent.AGENT_TYPE_NORMAL_PARTNER_AGENT.equals(agent.getAdministratorIndicator())){
            			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ACCRUL_NORMAL_ALLOWED_URL_LIST));
            			landingURL = siteProperties.getProperty(SiteProperties.ACCRUL_NORMAL_AGENT_LANDING_URL);
            			logger.info("allowedURL:"+allowedURLs);
            			logger.info("landingURL:"+landingURL);
            		}else if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())){
            			allowedURLs.addAll(siteProperties.getPropertyAsList(SiteProperties.ACCRUL_ADMINISTRATOR_ALLOWED_URL_LIST));
            			landingURL = siteProperties.getProperty(SiteProperties.ADMINISTRATOR_LANDING_URL);		
            		}
            	}
            }
            request.getSession().setAttribute("allowURLs", allowedURLs);

            String url = StringUtils.removeStart(requestURL, request.getContextPath());
            logger.info("url:"+url);
            if (!allowedURLs.contains(url)) {
            	String secureLandingURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), landingURL, null, urlHelper.isSSL(request));
                logger.info("Agent '" + agent.getAgentID() + "' is attempting to access an unauthorised URL: " + requestURL + ". Redirecting to " + secureLandingURL);
                response.sendRedirect(secureLandingURL);
            }

            return true;
        }
    }
    
    protected Cookie getLoginCookie(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	if (cookies == null) {
    		return null;
    	}
    	
    	for (int i = 0; i < cookies.length; i++) {
    		if (cookies[i].getName().equals("amPartnerLogin")) {
    			return cookies[i];
    		}
    	}
    	return null;
    }
}