/*
 * Created on Jun 11, 2009
 *

 */
package com.asiamiles.partnerportal.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

/**
 * Interceptor to log the request for Web Analytics purposes.
 * @author CPPBENL
 * 
 */
public class WAInterceptor extends HandlerInterceptorAdapter {

	private static Logger waLogger = AppLogger.getWALogger();
	private static Logger logger = AppLogger.getAppLogger();

	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String target = null;
		
		for (Enumeration enum = request.getParameterNames(); enum.hasMoreElements();) {
			String paramName = (String)enum.nextElement();
			if (paramName.startsWith("_")) {
				target = paramName;
			}
		}
		logger.info("Request URL: " + request.getRequestURL().toString() + (target == null? "" : "?" + target));

		for (Enumeration enum = request.getParameterNames(); enum.hasMoreElements();) {
			String paramName = (String)enum.nextElement();
			if (paramName.equals(target)) {
				continue;
			}
			if (paramName.toLowerCase().indexOf("password") >= 0) {
				logger.debug("Parameter " + paramName + "=" + request.getParameter(paramName));
			} else {
				logger.debug("Parameter " + paramName + "=" + request.getParameter(paramName));
			}
		} 
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String target = null;
		
		for (Enumeration enum = request.getParameterNames(); enum.hasMoreElements();) {
			String paramName = (String)enum.nextElement();
			if (paramName.startsWith("_")) {
				target = paramName;
			}
		}
		
		//Only write to WA Log if it's not a login request
		//WA for Login requests are handled separately in LoginController
		if (request.getRequestURL().indexOf("/login.do") == -1) {
			waLogger.info(request.getRequestURL() + (target == null? "" : "?" + target));
		}
	}
}
