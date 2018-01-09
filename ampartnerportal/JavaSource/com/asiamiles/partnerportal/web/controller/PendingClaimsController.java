/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PendingClaimsForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.domain.logic.PendingClaimsFormValidator;
import com.asiamiles.partnerportal.domain.predicate.ClaimNoNARClaimDetailsPredicate;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;
import com.asiamiles.partnerportal.cls.ActionCode;
/**
 * Wizard Controller for processing pending claims
 * @author CPPKENW
 *
 */
public class PendingClaimsController extends AbstractWizardFormController {

	private static final String[] PAGES = new String[]{ViewConstants.PENDING_CLAIMS_PROCESS_CLAIMS, ViewConstants.PENDING_CLAIMS_COMPLETE_COLLECTION,ViewConstants.PENDING_CLAIMS_ACKNOWLEDGEMENT}; 
	
	private CLSFacade clsFacade;
	private SiteProperties siteProperties;

	private Logger logger = AppLogger.getAppLogger();
	
	/**
	 * 
	 */
	public PendingClaimsController() {
		setCommandName("pendingClaimsForm");
		setPages(PAGES);
	}

	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
	/**
	 * @param siteProperties The siteProperties to set.
	 */
	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}	

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = userSession.getAgent();
		
		PendingClaimsForm pendingClaimsForm = new PendingClaimsForm(); //Construct backing object
		try{
			List claims = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_OUTSTANDING,"",agent.getTimeZone(),null,null);
			pendingClaimsForm.setAllClaims(claims);
			pendingClaimsForm.setClaims(claims);
		}catch (CLSException e){
			throw new SystemException(SystemException.CLS_REQUEST_FAILURE, "Cannot retrieve pending claims: " + e.getMessage(), e);
		}
		return pendingClaimsForm;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#postProcessPage(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors, int)
	 */
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		if (errors.hasErrors()) {
			return;
		}

		UserSession session = (UserSession) WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();

		PendingClaimsForm pendingClaimsForm = (PendingClaimsForm) command;

		int targetPage = getTargetPage(request, page);
		switch (page) {
		case 0:
			if (targetPage == 1) {
				pendingClaimsForm.setActiveClaim((NARClaimDetails) pendingClaimsForm.getClaims().get(pendingClaimsForm.getClaimIndex()));
				logger.debug("Setting Active Claim to " + pendingClaimsForm.getActiveClaim().getClaimNo());
			} else {
				//Look for claim no in search form
				try {
					List claims = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_OUTSTANDING, "", agent.getTimeZone(), null, null);
					pendingClaimsForm.setAllClaims(claims);
					List filteredClaims = new ArrayList();
					String keyword = "";
					if (pendingClaimsForm.getClaimNo() != null) {
						keyword = pendingClaimsForm.getClaimNo().toString();
					}
					if (StringUtils.isNotEmpty(keyword)) {
						filteredClaims.addAll(CollectionUtils.select(claims, new ClaimNoNARClaimDetailsPredicate(keyword)));
					} else {
						filteredClaims.addAll(claims);
					}
					pendingClaimsForm.setClaims(filteredClaims);

				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			break;
		case 1:
			if (targetPage == 2) {
				int index = pendingClaimsForm.getClaimIndex();
				NARClaimDetails activeClaim = pendingClaimsForm.getActiveClaim();
				try {
					if (activeClaim.getAction().equals(NARClaimDetails.ACTION_COMPLETE)) {
						clsFacade.updateCollection(agent, ActionCode.NAR_COMPLETE, activeClaim.getMemberID(), activeClaim.getClaimNo(), null, activeClaim.getReceiptNo(), activeClaim.getRemarks(), activeClaim.getRemarks2(), "");
					} else if (activeClaim.getAction().equals(NARClaimDetails.ACTION_CANCEL)) {
						clsFacade.updateCollection(agent, ActionCode.NAR_VOID, activeClaim.getMemberID(), activeClaim.getClaimNo(), null, "", "", "", activeClaim.getCancelReason());
					}
				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			break;
		}

	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) throws Exception {
		int targetPage = getTargetPage(request, page);
		PendingClaimsForm pendingClaimsForm = (PendingClaimsForm) command;
		PendingClaimsFormValidator pendingClaimsFormValidator = (PendingClaimsFormValidator) getValidator();

		switch (page) {
		case 1:
			if (targetPage == 2) {
				pendingClaimsFormValidator.validateDetail(pendingClaimsForm, errors, request);
			}
			break;
		}
	}
	
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = userSession.getAgent();

		Integer timeZone = new Integer((int)Math.round(agent.getTimeZone().doubleValue()/100.0));
		String tZone = timeZone.toString();
		if (timeZone.intValue() > 0) {
			tZone = "+" + tZone;
		}
		
		Map refData = new HashMap();
		refData.put("timeZone", tZone);
		
		refData.put("agentType",agent.getAdministratorIndicator());
		
		return refData;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), command);
		return new ModelAndView (ViewConstants.PENDING_CLAIMS_ACKNOWLEDGEMENT,model);
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processCancel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), new PendingClaimsForm());
		return new ModelAndView("pendingClaims/ProcessClaims", model);
	}
	
}
