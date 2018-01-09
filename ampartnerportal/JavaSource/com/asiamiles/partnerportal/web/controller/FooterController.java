/*
 * Created on Jun 5, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;


import java.util.HashMap;
import java.util.Map;

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
import com.cathaypacific.utility.Logger;

/**
 * <code>MultiActionController</code> to facilitate the display of the login form,
 * authenticating the user and logging out the user.
 * @author CPPBENL
 *
 */
public class FooterController extends MultiActionController{
	/**
	 * Displays the Login Form
	 * @param request the HTTP Request
	 * @param response the HTTP Response
	 * @return
	 */
	public ModelAndView copyright(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("copyright");
	}

    /**
	 * Displays the Login Form
	 * @param request the HTTP Request
	 * @param response the HTTP Response
	 * @return
	 */
	public ModelAndView privacy(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("privacy");
	}

    	/**
	 * Displays the Login Form
	 * @param request the HTTP Request
	 * @param response the HTTP Response
	 * @return
	 */
	public ModelAndView terms(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("terms");
	}
}
