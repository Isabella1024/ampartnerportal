/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.ForgotPasswordRequest;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * Form Controller for the forgot password form. 
 * @author CPPBENL
 *
 */
public class ForgotPasswordController extends SimpleFormController {
	
	private Logger logger = AppLogger.getAppLogger(); 
	private CLSFacade clsFacade;
	
	public ForgotPasswordController() {
		setCommandName("forgotPasswordForm");
	}
	
	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
		ForgotPasswordRequest form = (ForgotPasswordRequest)command;
		Map model = errors.getModel();
		try {
			clsFacade.forgotPassword(form.getAgentID());
			model.put("messageCode", "message_success_forgotPassword");
		} catch (CLSException e) {
			errors.reject(e.getErrorMessageCode(), e.getMessage());
			return new ModelAndView(ViewConstants.FORGOT_PASSWORD_FORM, errors.getModel());
		}
		return new ModelAndView(ViewConstants.LOGIN_FORM, model);
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new ForgotPasswordRequest();
	}
}
