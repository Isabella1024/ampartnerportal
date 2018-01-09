/*
 * Created on Jun 29, 2009
 *
 */
package com.asiamiles.partnerportal.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

/**
 * Exception Handler for handling exceptions within the web application
 * @author CPPBENL
 *
 */
public class SystemExceptionHandler implements HandlerExceptionResolver, Ordered {

	private static Logger logger = AppLogger.getAppLogger();
	
	private int order;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("Caught exception " + ex.getClass() + ": " + ex.getMessage(), ex);
		if (ex instanceof SystemException) {
			Map model = new HashMap();
			model.put("message", ex.getMessage());
			model.put(SystemException.SESSION_ATTRIBUTE_NAME, ex);
			return new ModelAndView("error", model);		
		} else {
			Map model = new HashMap();
			model.put("message", ex.getMessage());
			model.put(SystemException.SESSION_ATTRIBUTE_NAME, ex);
			return new ModelAndView("error", model);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

}
