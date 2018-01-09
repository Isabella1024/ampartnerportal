/**
 * 
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.dao.InstantRedeemGroupDao;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.InstantRedeemPartnerGroup;
import com.asiamiles.partnerportal.domain.InstantRedeemAssignGroupToPackageCommand;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.web.ViewConstants;

/**
 * @author fuzhen.he
 *
 */
public class InstantRedeemGroupController extends MultiActionController {
	
	private static final String ASSIGN_TYPE_AGENT_ID = "agentId";
	
	private static final String ASSIGN_TYPE_PACKAGE_CODE = "packageCode";
	
	private InstantRedeemGroupDao instantRedeemGroupDao;
	
	private CLSFacade clsFacade;
	
	public ModelAndView groupAdd(final HttpServletRequest pRequest, final HttpServletResponse pResponse,
			final InstantRedeemPartnerGroup instantRedeemPartnerGroupCommand) {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		String user = loggedInAgent.getAgentID();
		instantRedeemPartnerGroupCommand.setPartnerCode(loggedInAgent.getPartnerCode());
		instantRedeemGroupDao.storeGroup(instantRedeemPartnerGroupCommand, user);
		return listGroupAssign(pRequest, pResponse);
	}
	
	public ModelAndView loadGroupAdd(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_PARTNER_GROUP_ADD);
	}
	
	public ModelAndView groupAssign(final HttpServletRequest pRequest, final HttpServletResponse response,
			final InstantRedeemPartnerGroup instantRedeemPartnerGroupCommand) {
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		String user = loggedInAgent.getAgentID();
		instantRedeemPartnerGroupCommand.setPartnerCode(loggedInAgent.getPartnerCode());
		if(ASSIGN_TYPE_AGENT_ID.equalsIgnoreCase(instantRedeemPartnerGroupCommand.getType())) {
			instantRedeemGroupDao.clearAgentGroup(instantRedeemPartnerGroupCommand.getPartnerCode(), instantRedeemPartnerGroupCommand.getGroupId());
			instantRedeemGroupDao.storeAgentGroup(instantRedeemPartnerGroupCommand, user);
			return groupAdmin(pRequest, response);
		} else if(ASSIGN_TYPE_PACKAGE_CODE.equalsIgnoreCase(instantRedeemPartnerGroupCommand.getType())) {
			instantRedeemGroupDao.clearPackageCodeGroup(instantRedeemPartnerGroupCommand.getPartnerCode(), instantRedeemPartnerGroupCommand.getGroupId());
			instantRedeemGroupDao.storePackageCodeGroup(instantRedeemPartnerGroupCommand, user);
			return groupAdmin(pRequest, response);
		}
		return groupAdmin(pRequest, response);
	}
	
	public ModelAndView listGroupForPackage(final HttpServletRequest pRequest, final HttpServletResponse response)	{
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Map instantRedeemMap = new HashMap();
		
		String packageCode = pRequest.getParameter("packageCode");
		instantRedeemMap.put("packageCode", packageCode);
		
		List groups = instantRedeemGroupDao.listGroup(loggedInAgent.getPartnerCode());
		instantRedeemMap.put("groups", groups);
		
		List assignedGroups = instantRedeemGroupDao.queryGroupByPackageCode(loggedInAgent.getPartnerCode(), packageCode);
		instantRedeemMap.put("assignedGroups", assignedGroups);
		
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_LIST_GROUP_FOR_PACKAGE, instantRedeemMap);
	}
	
	public ModelAndView listGroupPackage(final HttpServletRequest pRequest, final HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Map instantRedeemMap = new HashMap();
		
		String groupId = pRequest.getParameter("groupId");
		instantRedeemMap.put("groupId", groupId);
		
		List packageCodes = instantRedeemGroupDao.listInstantPackageCode(loggedInAgent.getPartnerCode());
		instantRedeemMap.put("packageCodes", packageCodes);
		
		List existingPackage = instantRedeemGroupDao.queryPackageByGroupName(loggedInAgent.getPartnerCode(), groupId);
		instantRedeemMap.put("existingPackage", existingPackage);

		return new ModelAndView(ViewConstants.INSTANT_REDEEM_PACKAGE_GROUP_ASSIGN, instantRedeemMap);
	}
	
	public ModelAndView listGroupAssign(final HttpServletRequest pRequest, final HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Map instantRedeemMap = new HashMap();
		
		String groupId = pRequest.getParameter("groupId");
		instantRedeemMap.put("groupId", groupId);
		try {
			List agents = clsFacade.retrieveAgents(loggedInAgent.getAgentID(), loggedInAgent.getPartnerCode(),loggedInAgent.getPartnerGroup());
			instantRedeemMap.put("agents", agents);
		} catch (CLSException e) {
			return new ModelAndView(ViewConstants.INSTANT_REDEEM_GROUP_ASSIGN, "clsexception", e);
		}
		
		List existingMember = instantRedeemGroupDao.queryAgentByGroupName(loggedInAgent.getPartnerCode(), groupId);
		instantRedeemMap.put("existingMember", existingMember);
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_GROUP_ASSIGN, instantRedeemMap);
	}
	
	public ModelAndView groupAdmin(final HttpServletRequest pRequest, final HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Map instantRedeemMap = new HashMap();
		
		List groups = instantRedeemGroupDao.listGroup(loggedInAgent.getPartnerCode());
		instantRedeemMap.put("groups", groups);
		
		List packages = instantRedeemGroupDao.listInstantPackageCode(loggedInAgent.getPartnerCode());
		instantRedeemMap.put("packages", packages);
		
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_GROUP_ADMIN, instantRedeemMap);
	}
	
	public ModelAndView deleteGroup(final HttpServletRequest pRequest, final HttpServletResponse response)	{
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		
		this.instantRedeemGroupDao.deleteGroup(loggedInAgent.getPartnerCode(), pRequest.getParameter("groupId"));
		
		return groupAdmin(pRequest, response);
	}
	
	public ModelAndView assignGroupToPackage(final HttpServletRequest pRequest, final HttpServletResponse response,
			InstantRedeemAssignGroupToPackageCommand command)	{
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		
		String partnerCode = command.getPartnerCode();
		String user = loggedInAgent.getAgentID();
		String packageCode = command.getPackageCode();
		String [] groupId = command.getGroupId();
		
		this.instantRedeemGroupDao.clearGroupForPackageCode(partnerCode, packageCode);
		this.instantRedeemGroupDao.storeGroupForPackageCode(groupId, packageCode, partnerCode, user);	
		return groupAdmin(pRequest, response);
	}

	public InstantRedeemGroupDao getInstantRedeemGroupDao() {
		return instantRedeemGroupDao;
	}

	public void setInstantRedeemGroupDao(InstantRedeemGroupDao instantRedeemGroupDao) {
		this.instantRedeemGroupDao = instantRedeemGroupDao;
	}

	public CLSFacade getClsFacade() {
		return clsFacade;
	}

	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
}
