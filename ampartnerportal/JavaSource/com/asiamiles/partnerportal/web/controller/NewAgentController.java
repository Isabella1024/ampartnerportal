/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.web.ViewConstants;

/**
 * Form Controller for creating new Agents.
 * @author CPPBENL
 *
 */
public class NewAgentController extends SimpleFormController {

	private CLSFacade clsFacade;
	
	public NewAgentController() {
		setCommandName("agent");
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
		Agent adminAgent = session.getAgent();
		Agent newAgent = (Agent)command;
		Map model = new HashMap();
		//cppjox add For AML38938 20160615 start
		newAgent.setPartnerGroup(adminAgent.getPartnerGroup());	
		//cppjox add For AML38938 20160615 end
		try {
			clsFacade.updateAgent(newAgent, ActionCode.AGENT_CREATE, adminAgent.getAgentID(), null);
			model.put("messageCode", "message_success_newAgent");
			
			//Fetch the list of agents to be displayed in the ListAgents view 
			List agents = clsFacade.retrieveAgents(adminAgent.getAgentID(), null,adminAgent.getPartnerGroup());
			model.put("agents", agents);
			
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, model);
		} catch (CLSException e) {
			model.put("clsexception", e);
			model.put(getCommandName(), newAgent);
			return new ModelAndView(ViewConstants.AGENT_ADMIN_NEW_AGENT_FORM, model);
		}
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new Agent();		
	}
}
