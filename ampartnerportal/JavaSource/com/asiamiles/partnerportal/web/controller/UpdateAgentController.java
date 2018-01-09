/*
 * Created on Jul 3, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.dao.InstantRedeemGroupDao;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.web.ViewConstants;

/**
 * Form Controller for updating agent information.
 * 
 * 
 * @author CPPBENL
 *
 */
public class UpdateAgentController extends SimpleFormController {
	private CLSFacade clsFacade;
	
	private InstantRedeemGroupDao instantRedeemGroupDao;
	
	public UpdateAgentController() {
		setCommandName("agent");
	}
	
	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
	
	/**
	 * @param instantRedeemGroupDao The instantRedeemGroupDao to set.
	 */
	public void setInstantRedeemGroupDao(InstantRedeemGroupDao instantRedeemGroupDao) {
		this.instantRedeemGroupDao = instantRedeemGroupDao;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent adminAgent = session.getAgent();
		Agent updatedAgent = (Agent)command;
		Map model = new HashMap();
		updatedAgent.setPartnerGroup(adminAgent.getPartnerGroup());
		
		String updateCommand = request.getParameter("updateCommand");
		if (StringUtils.isBlank(updateCommand)) {
			throw new SystemException(SystemException.INVALID_REQUEST, "Update Command is empty");
		} else if (!"Delete".equalsIgnoreCase(updateCommand) && !"Save".equalsIgnoreCase(updateCommand)) {
			throw new SystemException(SystemException.INVALID_REQUEST, "Unknown Update Command '" + updateCommand+ "'");
		}
		
		try {
			
			if ("Save".equalsIgnoreCase(updateCommand)) {
				clsFacade.updateAgent(updatedAgent, ActionCode.AGENT_UPDATE, adminAgent.getAgentID(), null);
				model.put("messageCode", "message_success_updateAgent");
			} else if ("Delete".equalsIgnoreCase(updateCommand)) {
				//Ensure that we are not deleting the currently logged-in agent
				if (adminAgent.getAgentID().equalsIgnoreCase(updatedAgent.getAgentID())) {
					errors.reject("error_deleteCurrentAgent", "You cannot delete the agent you are currently logged in with.");
					model.putAll(errors.getModel());
					return new ModelAndView(ViewConstants.AGENT_ADMIN_UPDATE_AGENT_FORM, model);
				}
				clsFacade.updateAgent(updatedAgent, ActionCode.AGENT_DELETE, adminAgent.getAgentID(), null);
				model.put("messageCode", "message_success_deleteAgent");
				//When an agent is being removed from partner portal, remove the agent from grouping list at the same time
				instantRedeemGroupDao.deleteAgentGroupByAgentId(updatedAgent.getAgentID());
			}
			
			//Fetch the list of agents to be displayed in the ListAgents view 
			List agents = clsFacade.retrieveAgents(adminAgent.getAgentID(), null,adminAgent.getPartnerGroup());
			model.put("agents", agents);
			
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, model);
		} catch (CLSException e) {
			model.put("clsexception", e);
			model.put(getCommandName(), updatedAgent);
			return new ModelAndView(ViewConstants.AGENT_ADMIN_UPDATE_AGENT_FORM, model);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		String agentID = request.getParameter("agentID");
		if (StringUtils.isBlank(agentID)) {
			throw new SystemException(SystemException.REQUEST_MISSING_PARAMETERS);
		}
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent adminAgent = session.getAgent();
		
		List agents = clsFacade.retrieveAgents(adminAgent.getAgentID(), null,adminAgent.getPartnerGroup());
		
		for (Iterator it = agents.iterator(); it.hasNext();) {
			Agent agent = (Agent)it.next();
			if (agent.getAgentID().equalsIgnoreCase(agentID)) {
				return agent;
			}
		}
		throw new SystemException(SystemException.INVALID_REQUEST, "Agent not found: " + agentID);
	}
}
