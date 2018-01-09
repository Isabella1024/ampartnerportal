/*
 * Created on Jun 26, 2009
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
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * MultiActionController for "simple" Agent Administration functions
 * 
 * @author CPPBENL
 *
 *  */
public class AgentAdminController extends MultiActionController {

	private static final Logger logger = AppLogger.getAppLogger();
	
	private CLSFacade clsFacade;
	private SiteProperties siteProperties;
		
	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
	/**
	 * Returns the view with a list of agents for that partner
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listAgents(HttpServletRequest request, HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		try {
			List agents = clsFacade.retrieveAgents(loggedInAgent.getAgentID(), null,loggedInAgent.getPartnerGroup());
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, "agents", agents);
		} catch (CLSException e) {
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, "clsexception", e);
		}
	}
	
	/**
	 * Resets the selected agent's password
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Agent targetAgent = null;
		Map model = new HashMap();
		
		String agentID = request.getParameter("agentID");
		if (StringUtils.isBlank(agentID)) {
			throw new SystemException(SystemException.REQUEST_MISSING_PARAMETERS, "missing agentID parameter");
		}
		try {
			List agents = clsFacade.retrieveAgents(loggedInAgent.getAgentID(), null,loggedInAgent.getPartnerGroup());
			for (Iterator it = agents.iterator(); it.hasNext();) {
				Agent agent = (Agent)it.next();
				if (agentID.equalsIgnoreCase(agent.getAgentID())) {
					targetAgent = agent;
					//cppjox add for AML38938 20160621 start 
					targetAgent.setPartnerGroup(loggedInAgent.getPartnerGroup());
					//cppjox add for AML38938 20160621 start 
				}
			}
			if (targetAgent == null) {
				throw new SystemException(SystemException.CLS_REQUEST_FAILURE, "CLS Agent not Found");
			}
			clsFacade.updateAgent(targetAgent, ActionCode.AGENT_RESET_PASSWORD, loggedInAgent.getAgentID(), targetAgent.getPartnerCode());
			model.put("messageCode", "message_success_resetPassword");
			agents = clsFacade.retrieveAgents(loggedInAgent.getAgentID(), null,loggedInAgent.getPartnerGroup());
			model.put("agents", agents);
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, model);
			
		} catch (CLSException e) {
			return new ModelAndView(ViewConstants.AGENT_ADMIN_LIST_AGENTS, "clsexception", e);
		}
		
	}
}
