/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.*;
import com.asiamiles.partnerportal.domain.logic.PaperlessRedemptionFormValidator;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 * Wizard Constroller for Verifying Redemptions
 * 
 * @author CPPBENL
 *
 */
public class NewRedemptionController extends AbstractWizardFormController {

	private static final String[] PAGES = new String[]{ViewConstants.REDEMPTION_ENTER_CLAIMS, ViewConstants.REDEMPTION_PROCESS_CLAIMS, ViewConstants.REDEMPTION_COMPLETE_CLAIMS}; 
	
	private CLSFacade clsFacade;
	private SiteProperties siteProperties;	
	private Logger logger = AppLogger.getAppLogger();

	public NewRedemptionController() {
		setCommandName("redemptionForm");
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
	
	protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		PaperlessRedemptionForm redemptionForm = new PaperlessRedemptionForm(true); //Construct backing redemption with the 5 empty claims
		return redemptionForm;
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) throws Exception {
		PaperlessRedemptionForm redemptionForm = (PaperlessRedemptionForm) command;
		PaperlessRedemptionFormValidator paperlessRedemptionFormValidator = (PaperlessRedemptionFormValidator) getValidator();
		
		switch (page) {
			case 0:
				paperlessRedemptionFormValidator.validateMemberInfo(redemptionForm, errors);
				paperlessRedemptionFormValidator.validateClaims(redemptionForm, errors);
				break;
			case 1:
				paperlessRedemptionFormValidator.validateCheckbox(redemptionForm, errors);
				break;
			case 2:
				paperlessRedemptionFormValidator.validateRefAndReason(redemptionForm, errors, request);
				break;
		}
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

		PaperlessRedemptionForm redemptionForm = (PaperlessRedemptionForm) command;
		int targetPage = getTargetPage(request, page);		

		switch (page) {
		case 0:
			try {
				//CPPNXU add for paperless 20120504 start
				Map claimsMap = clsFacade.retrieveClaims(agent, redemptionForm.getMemberId(), redemptionForm.getMemberEmbossedName(), redemptionForm.getClaims());
				redemptionForm.setNARDetails((List)claimsMap.get("NARDetails"));
				redemptionForm.setMemberEmbossedName((String)claimsMap.get("MemberName"));
				//CPPNXU add for paperless 20120504 end
			} catch (CLSException e) {
				errors.reject(e.getErrorMessageCode(), e.getMessage());
			}
			break;
		case 1:
			if (targetPage == 1) {
				//refresh claim status when a claim has been collected or completed already 
				try {
					Map claimsMap = clsFacade.retrieveClaims(agent, redemptionForm.getMemberId(), redemptionForm.getMemberEmbossedName(), redemptionForm.getClaims());
					redemptionForm.setNARDetails((List)claimsMap.get("NARDetails"));
				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			else {
				//complete or cancel the claims 
				List claimList = redemptionForm.getClaims();
				try {
					for (Iterator it = redemptionForm.getNARDetails().iterator(); it.hasNext();) {
						NARClaimDetails claimDetails = (NARClaimDetails) it.next();
						if (claimDetails.isToBeProcessed()) {
							clsFacade.updateCollection(agent, ActionCode.NAR_CONFIRM, redemptionForm.getMemberId(), new Integer(claimDetails.getPaperlessClaim().getClaimNumber()), 
									claimDetails.getPaperlessClaim().getSecurityCode(), "", "", "", "");
						}
					}
				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			break;
		case 2:
			break;
		}
	}
	
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = userSession.getAgent();
		Map refData = new HashMap();
		refData.put("agentType",agent.getAdministratorIndicator());
		
		int gracePeriod = Integer.parseInt(siteProperties.getProperty(SiteProperties.GRACE_PERIOD));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -gracePeriod);
		refData.put("expiryDate", c.getTime()); //note that the expiry date = system date - grace period
		
		return refData;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), command);
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();
		PaperlessRedemptionForm redemptionForm = (PaperlessRedemptionForm) command;
		
		List claimList = redemptionForm.getClaims();

		try{
			for (Iterator it = redemptionForm.getNARDetails().iterator(); it.hasNext();) {
				NARClaimDetails claimDetails = (NARClaimDetails)it.next();
				PaperlessClaim claim = claimDetails.getPaperlessClaim();
	
				if (claimDetails.isToBeProcessed()){
					if (claimDetails.getAction().equals(NARClaimDetails.ACTION_COMPLETE)) {
						clsFacade.updateCollection(agent,ActionCode.NAR_COMPLETE,redemptionForm.getMemberId(),new Integer(claim.getClaimNumber()),claim.getSecurityCode(),claimDetails.getReceiptNo(),claimDetails.getRemarks(),claimDetails.getRemarks2(),"");
					} else if (claimDetails.getAction().equals(NARClaimDetails.ACTION_CANCEL)){
						clsFacade.updateCollection(agent,ActionCode.NAR_VOID,redemptionForm.getMemberId(),new Integer(claim.getClaimNumber()),claim.getSecurityCode(),claimDetails.getReceiptNo(),claimDetails.getRemarks(),claimDetails.getRemarks2(),"");
					} 
				}
			}
		} catch (CLSException e) {
			errors.reject(e.getErrorMessageCode(),e.getMessage());
		}
		
		return new ModelAndView (ViewConstants.REDEMPTION_ACKNOWLEDGEMENT, model);
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processCancel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), new PaperlessRedemptionForm(true));
		return new ModelAndView(ViewConstants.REDEMPTION_ENTER_CLAIMS, model);
	}
}
