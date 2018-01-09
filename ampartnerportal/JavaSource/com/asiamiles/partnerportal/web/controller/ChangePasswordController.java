/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.ChangePasswordRequest;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.web.ViewConstants;

/**
 * Form Controller for changing the current logged-in agent's password
 * @author CPPBENL
 *
 */
public class ChangePasswordController extends SimpleFormController {

	public static final String AGENT_ADMIN_CHANGE_PASSWORD_FORM = "agentAdmin/ChangePasswordForm";
	private CLSFacade clsFacade;
	
	public ChangePasswordController() {
		setCommandName("changePasswordForm");
	}
	
	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();
		Map model = new HashMap();

		ChangePasswordRequest changePasswordForm = (ChangePasswordRequest)command;
		
		try {
			clsFacade.changeAgentPassword(agent, changePasswordForm.getOldPassword(), changePasswordForm.getNewPassword());
			changePasswordForm = new ChangePasswordRequest();
			model.put("messageCode", "message_success_changePassword");
		} catch (CLSException e) {
			model.put("clsexception", e);
		}
		model.put(getCommandName(), changePasswordForm);
		return new ModelAndView(ViewConstants.AGENT_ADMIN_CHANGE_PASSWORD_FORM, model);
		
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new ChangePasswordRequest();
	}
}
