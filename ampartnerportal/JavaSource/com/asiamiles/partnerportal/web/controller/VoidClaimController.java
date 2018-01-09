/*
 * Created on Jul 3, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
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
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.ReportForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.cathaypacific.utility.Logger;
import com.asiamiles.partnerportal.util.DateConvertor;
import com.asiamiles.partnerportal.web.ViewConstants;

/**
 * Form Controller for voiding claim.
 * 
 * 
 * @author CPPKENW
 *
 */
public class VoidClaimController extends SimpleFormController {
	private CLSFacade clsFacade;
	private URLHelper urlHelper;
	private Logger logger = AppLogger.getAppLogger();
	
	public VoidClaimController() {
		setCommandName("reportForm");
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
		
		ReportForm reportForm = (ReportForm)command;
		NARClaimDetails nar = reportForm.getActiveClaim();
		try{
			clsFacade.updateCollection(agent, ActionCode.NAR_VOID, nar.getMemberID(), nar.getClaimNo(), "", nar.getReceiptNo(), "", "", reportForm.getReason());
			session.setVoidedClaim(nar.getClaimNo().toString());
			String securedURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), "/report.do", null, urlHelper.isSSL(request));
			response.sendRedirect(securedURL);			
		}catch(CLSException e){
			Map model = new HashMap();
			model.put("clsexception", e);
			model.put(getCommandName(), reportForm);
			return new ModelAndView(ViewConstants.REPORT_VOID_CLAIM, model);			
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		String claimNo = request.getParameter("claimNo");
		if (StringUtils.isBlank(claimNo)) {
			throw new SystemException(SystemException.REQUEST_MISSING_PARAMETERS);
		}
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();
		ReportForm reportForm = new ReportForm();
		
		List dateList = DateConvertor.exportYears();
		String startYear = (String)(dateList.get(0));
		String endYear = (String)(dateList.get(dateList.size()-1));
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Calendar.YEAR,Integer.parseInt(startYear));
		startDate.set(Calendar.MONTH,0);
		startDate.set(Calendar.DATE,1);
		endDate.set(Calendar.YEAR,Integer.parseInt(endYear));
		endDate.set(Calendar.MONTH,11);
		endDate.set(Calendar.DATE,31);		
		
		List claimList = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_ALL, "ALL", agent.getTimeZone(), startDate.getTime(), endDate.getTime());
		NARClaimDetails nar = new NARClaimDetails();
		for (int i = 0; i < claimList.size(); i++) {
			nar = (NARClaimDetails) (claimList.get(i));
			if (claimNo.equals(nar.getClaimNo().toString())) {
				reportForm.setActiveClaim(nar);
				return reportForm;
			}
		}
		
		throw new SystemException(SystemException.INVALID_REQUEST, "Claim not found: " + claimNo);
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#referenceData(javax.servlet.http.HttpServletRequest, int)
	 */
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap();
		
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = userSession.getAgent();
		Integer timeZone = new Integer((int)Math.round(agent.getTimeZone().doubleValue()/100.0));
		String tZone = timeZone.toString();
		if (timeZone.intValue() > 0) {
			tZone = "+" + tZone;
		}
		referenceData.put("timeZone", tZone); 		
		
		return referenceData;
	}
		
	
	/**
	 * @param urlHelper The urlHelper to set.
	 */
	public void setUrlHelper(URLHelper urlHelper) {
		this.urlHelper = urlHelper;
	}	
}
