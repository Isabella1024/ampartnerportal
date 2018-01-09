/*
 * Created on Jul 16, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * Form Controller Class for creating partner admin agents. 
 * @author CPPBENL
 *
 */
public class CLSAdminController extends SimpleFormController {

	private Logger logger = AppLogger.getAppLogger(); 
	
	private CLSFacade clsFacade;

	public CLSAdminController() {
		setCommandName("agent");
	}
	
	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
	/* (non-Javadoc)
	 * NOTE: We need to validate partner code before validating agent information in order
	 * for the Partner Code errors to be displayed first.
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBind(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		String adminID = session.getWebCLSAdminAgentID();
		Agent newAgent = (Agent)command;
		
		/* Check that the partnerCode is present (we need this to check whether the partner
		 * already has an admin agent)
		 */ 
		if (StringUtils.isEmpty(newAgent.getPartnerCode())) {
			errors.rejectValue("partnerCode", "required", "Partner Code must be Supplied");
			return;
		}
		try {
			// Check that the partner doesn't have any existing admin agents
			//AML38938 Start
			if (hasAdminUser(adminID, newAgent.getPartnerCode(), newAgent.getPartnerGroup())) {
			//AML38938 End
				errors.rejectValue("partnerCode", "error_adminAgentAlreadyExists",new Object[]{newAgent.getPartnerCode()},"An administrator already exists for partner code " + newAgent.getPartnerCode());
			}
		} catch (CLSException e) {
			if ("05".equals(e.getErrorCode())) {
				errors.rejectValue("partnerCode", "error_invalidPartnerCode", new Object[]{newAgent.getPartnerCode()}, "Partner Code does not exist");
			} else {
				errors.reject(e.getErrorMessageCode(), e.getMessage());
			}
		}	
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		String adminID = session.getWebCLSAdminAgentID();
		
		Agent newAgent = (Agent)command;
		ModelAndView modelAndView = new ModelAndView(ViewConstants.CLS_ADMIN_NEW_ADMIN_AGENT_FORM);
		try {
			clsFacade.updateAgent(newAgent, ActionCode.AGENT_CREATE, adminID, newAgent.getPartnerCode());
		} catch (CLSException e) {
			modelAndView.addObject("clsexception", e);
			modelAndView.addObject(getCommandName(), newAgent);
			return modelAndView;
		}
		
		modelAndView.addObject("messageCode", "message_success_newAgent");
		modelAndView.addObject(getCommandName(), new Agent());
		return modelAndView; 
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new Agent();
	}

	/**
	 * Checks whether the partner with the specified partner code  has any existing admin agents
	 * @param adminID the admin user ID doing the check
	 * @param partnerCode the partner code
	 * @return <code>true</code> if the partner has existing admin agents, <code>false</code> otherwise.
	 * @throws CLSException if there are any problems with the CLS interface call
	 */
	//AML38938 Start
	protected boolean hasAdminUser(String adminID, String partnerCode, String partnerGroup) throws CLSException {
	//AML38938 End
		List agentsList = clsFacade.retrieveAgents(adminID, partnerCode, partnerGroup);
		for (Iterator it = agentsList.iterator();it.hasNext(); ) {
			Agent agent = (Agent)it.next();
			if (Agent.AGENT_TYPE_ADMINISTRATOR.equals(agent.getAdministratorIndicator())) {
		//AML38938 Start
				if (partnerGroup.equals(agent.getPartnerGroup())){
					return true;
				}
		//AML38938 Start
			}
		}
		return false;
	}
 }
