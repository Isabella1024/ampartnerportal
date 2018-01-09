/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.cathaypacific.utility.Logger;



/**
 * Interceptor for ensuring that the request is coming from a SSL source.
 * 
 * @author CPPBENL
 * 
 */
public class SSLInterceptor extends HandlerInterceptorAdapter {

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
	
	/*
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		logger.debug("ServerPort: " + request.getServerPort() + " secure: " + request.isSecure() + " forceSSL: " + siteProperties.getPropertyByPlatform(SiteProperties.ENFORCE_SSL));
		boolean forceSSL = Boolean.valueOf(siteProperties.getPropertyByPlatform(SiteProperties.ENFORCE_SSL)).booleanValue(); 
		
		// Return if we're not enforcing SSL connections
		if (!forceSSL) {
			logger.debug("SSL Enforcement has been disabled.");
			return true;
		}
		
		if (urlHelper.isSSL(request)) {
			return true;
		} else {
			String secureURL = urlHelper.sslFilter(request.getRequestURL().toString());
			if (StringUtils.isNotEmpty(request.getQueryString())) {
				secureURL += "?" + request.getQueryString();
			}
			logger.debug("Redirecting non-SSL Request to " + secureURL);
			response.sendRedirect(secureURL);
			return false;
		}
	}
}
