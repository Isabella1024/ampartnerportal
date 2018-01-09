/*
 * Created on Jul 17, 2009
 *
 * 
 */
package com.asiamiles.partnerportal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * Controller to redirect logged-in users to Invalid Offer page.
 * @author ACN
 */
public class InvalidOfferController extends MultiActionController {

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
	 * Returns the view with invalid offer
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView invalidOffer(HttpServletRequest request, HttpServletResponse response){
		logger.debug("InvalidOfferController request.getServletPath = " +request.getServletPath());

		return new ModelAndView(ViewConstants.INSTANT_REDEEM_INVALID_OFFER);
	}
	
	/**
	 * Returns the view with expired offer
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView expiredOffer(HttpServletRequest request, HttpServletResponse response){
		logger.debug("InvalidOfferController request.getServletPath = " +request.getServletPath());

		return new ModelAndView(ViewConstants.INSTANT_REDEEM_EXPIRED_OFFER);
	}
	
	/**
	 * Returns the view with redeemed offer
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView redeemedOffer(HttpServletRequest request, HttpServletResponse response){
		logger.debug("InvalidOfferController request.getServletPath = " +request.getServletPath());

		return new ModelAndView(ViewConstants.INSTANT_REDEEM_REDEEMED_OFFER);
	}
	
	/**
	 * Returns the view with redeemed offer
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView notAllowOffer(HttpServletRequest request, HttpServletResponse response){
		logger.debug("InvalidOfferController request.getServletPath = " +request.getServletPath());

		return new ModelAndView(ViewConstants.INSTANT_NOT_ALLOW_OFFER);
	}

}
