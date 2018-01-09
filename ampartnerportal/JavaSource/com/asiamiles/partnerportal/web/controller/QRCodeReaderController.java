/*
 * Created on Jun 26, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * MultiActionController for "simple" Agent Administration functions
 * 
 * @author CPPBENL
 *
 *  */
public class QRCodeReaderController extends MultiActionController {

	private SiteProperties siteProperties;
	private URLHelper urlHelper;
	private static final Logger logger = AppLogger.getAppLogger();
	
	/**
	 * Returns the view with a list of agents for that partner
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView displayQRCodeReader(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if (StringUtils.isEmpty(request.getParameter("memberId")) && StringUtils.isEmpty(request.getParameter("claimNO")) && StringUtils.isEmpty(request.getParameter("securityCode")) && StringUtils.isEmpty(request.getParameter("mb"))) {
			return new ModelAndView(ViewConstants.INSTANT_REDEEM_QRCODE_READER);
		} else {
			
			Map parameterMap = new HashMap();
			
			for (Enumeration enum = request.getParameterNames(); enum.hasMoreElements();) {
				String paramName = (String)enum.nextElement();
				parameterMap.put(paramName, request.getParameter(paramName));
			}
			
			String instantRedeemURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_REDEMPTION_URL);

			String secureInstantRedeemURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), instantRedeemURL, parameterMap, urlHelper.isSSL(request));
			logger.debug("Forwarding request to " + secureInstantRedeemURL);
			response.sendRedirect(secureInstantRedeemURL);
			return null;
		}
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
}
