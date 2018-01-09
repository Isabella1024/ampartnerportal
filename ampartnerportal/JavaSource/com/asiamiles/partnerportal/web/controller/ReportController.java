/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.ReportForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.domain.logic.ReportFormValidator;
import com.asiamiles.partnerportal.util.DateConvertor;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;
import com.asiamiles.partnerportal.cls.ActionCode;
/**
 * Controller for Report Functions
 * 
 * @author CPPKENW
 *
 */
public class ReportController extends AbstractWizardFormController {

	private static final String[] PAGES = new String[]{
			ViewConstants.REPORT_REPORT, 
			ViewConstants.REPORT_REPORT_EXPORT}; 
	
	private CLSFacade clsFacade;
	private SiteProperties siteProperties;
	
	public static Map chooseMap = new HashMap();
	static {
		chooseMap.put("A", ActionCode.NAR_RETRIEVAL_ALL);
		chooseMap.put("B", ActionCode.NAR_RETRIEVAL_BILLED);
		chooseMap.put("O", ActionCode.NAR_RETRIEVAL_OUTSTANDING);
		chooseMap.put("U", ActionCode.NAR_RETRIEVAL_UNBILLED);
	}

	private Logger logger = AppLogger.getAppLogger();
	
	/**
	 * 
	 */
	public ReportController() {
		setCommandName("reportForm");
		setPages(PAGES);
		setAllowDirtyBack(false);  
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
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {
		logger.info("Enter to ReportController.formBackingObject()...");
		UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
		Agent agent = userSession.getAgent();
		
		//Steven ADD FOR AML.34188 20140730 START
		int currentPage = 1;
		String nextPageCode=request.getParameter("nextPageCode");
		String prePageCode=request.getParameter("prePageCode");
		//Steven ADD FOR AML.34188 20140730 END
		
		logger.info("nextpagecode :	"+nextPageCode);
		logger.info("prePageCode :	"+prePageCode);
		logger.info("AgentID :		"+agent.getAgentID());
		logger.info("Session.getNextPageCode :	"+userSession.getNextPackageCode());
		logger.info("Session.getNextClaimNum :	"+userSession.getNextClaimNum());
		logger.info("Session.getPrePageCode :	"+userSession.getPrePackageCode());
		logger.info("Session.getPreClaimNum :	" +userSession.getPreClaimNum());

		ReportForm reportForm = new ReportForm(); //Construct backing object
		
		try {
			// if it is redirected from voidClaim.do, retrieve the search criteria stored in the user session. 
			if (StringUtils.isNotEmpty(userSession.getFromDay())){
				//Steven ADD FOR AML.34188 20140730 START
				logger.info("userSession.getFromDay() is not empty...");
				//Steven ADD FOR AML.34188 20140730 END
				reportForm.setFromDay(userSession.getFromDay());
				reportForm.setFromMonth(userSession.getFromMonth());
				reportForm.setFromYear(userSession.getFromYear());
				reportForm.setToDay(userSession.getToDay());
				reportForm.setToMonth(userSession.getToMonth());
				reportForm.setToYear(userSession.getToYear());
				
				//Yalin DEL FOR AML.34188 20140827  START
				//reportForm.setProgressOption(userSession.isProgressOption());
				//reportForm.setCompleteOption(userSession.isCompleteOption());
				//reportForm.setBillOption(userSession.isBillOption());
				//Yalin DEL FOR AML.34188 20140827  END
				
				//Yalin ADD FOR AML.34188 20140827  START
				reportForm.setChoose(userSession.getChoose());
				//Yalin ADD FOR AML.34188 20140827  END
				
				reportForm.setKeyword(userSession.getKeyword());
				reportForm.setVoidedClaim(userSession.getVoidedClaim());
				
				//Yalin UPDATE FOR AML.34188 20140807 START
				String requestPackageCode = null;
				String requestClaimNum = null;
				if(null != nextPageCode && !"".equals(nextPageCode)){
					logger.info("NEXT PAGE");
					requestPackageCode = userSession.getNextPackageCode();
					requestClaimNum = userSession.getNextClaimNum();
					if(null==requestPackageCode || "".equals(requestPackageCode)){
						currentPage = 1;
					}else{
						currentPage = userSession.getCurrentPage()+1;
					}
				}else if(null != prePageCode && !"".equals(prePageCode)){
					logger.info("PRE PAGE");
					requestPackageCode = userSession.getPrePackageCode();
					requestClaimNum = userSession.getPreClaimNum();
					if(null==requestPackageCode || "".equals(requestPackageCode)){
						currentPage = 1;
					}else{
						currentPage = userSession.getCurrentPage()-1;
					}
				}
				//Yalin UPDATE FOR AML.34188 20140807 END
				
				// set the date field to current date in case any of the date field is missing.
				setDateToCurrent(reportForm);
				Date fromDate = DateConvertor.parseDate(reportForm.getFromYear(), reportForm.getFromMonth(), reportForm.getFromDay());
				Date toDate = DateConvertor.parseDate(reportForm.getToYear(), reportForm.getToMonth(), reportForm.getToDay());
				
				//Yalin ADD FOR AML.34188 20140731 START
				ActionCode actionCode =ActionCode.NAR_RETRIEVAL_ALL;
				String choose = userSession.getChoose();
				logger.info("choose: "+choose);
				if(null!=choose &&!"".equals(choose)){
					actionCode = (ActionCode) chooseMap.get(choose);
				}
				logger.info("ActionCode: "+actionCode);
				//Yalin ADD FOR AML.34188 20140731 END
				
				
				//Yalin UPDATE FOR AML.34188 20140731 START
				
				//Re-fetch the claims
				//List allClaims = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_ALL, userSession, agent.getTimeZone(), fromDate, toDate);
				//List claims = getFilteredClaims(reportForm, allClaims);
				List allClaims = clsFacade.retrieveCollectionList(agent, actionCode, "ALL", agent.getTimeZone(), fromDate, toDate, requestClaimNum, requestPackageCode, "1");

				if(null != allClaims && allClaims.size()>0){
					setPaginationProperty(reportForm, (Map) allClaims.get(0));
					allClaims.remove(0);//remove map
				}
				reportForm.setClaims(allClaims);
				reportForm.setCurrentPage(currentPage);
				
				//Yalin UPDATE FOR AML.34188 20140731 END
				
			}else {	
				// set up default search criteria if it's the first time getting in the report page for the user session.
				//Steven ADD FOR AML.34188 20140730 START
				logger.info("userSession.getFromDay() is empty...");
				//Steven ADD FOR AML.34188 20140730 END
				Calendar cal = Calendar.getInstance();
				String year = "" + cal.get(Calendar.YEAR);
				String month = "" + (cal.get(Calendar.MONTH) + 1);
				String day = "" + cal.get(Calendar.DAY_OF_MONTH);
				Date today = cal.getTime();
		
				reportForm.setFromYear(year);
				reportForm.setFromMonth(month);
				reportForm.setFromDay(day);
				reportForm.setToYear(year);
				reportForm.setToMonth(month);
				reportForm.setToDay(day);
				
				//Yalin UPDATE FOR AML.34188 20140731 START
                reportForm.setChoose("A");
                //List allClaims = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_ALL, "ALL", agent.getTimeZone(), today, today);
				//List claims = getFilteredClaims(reportForm, allClaims);
			
                List allClaims = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_ALL, "ALL", agent.getTimeZone(), today, today, null, null, "1");

                if(null != allClaims && allClaims.size()>0){
					setPaginationProperty(reportForm, (Map) allClaims.get(0));
					allClaims.remove(0);//remove map
				}	
				reportForm.setClaims(allClaims);
				reportForm.setCurrentPage(1);
				//Yalin UPDATE FOR AML.34188 20140731 END
			}
		}catch (CLSException e) {
			throw new SystemException(SystemException.CLS_REQUEST_FAILURE, "Cannot retrieve claims: " + e.getMessage(), e);
		}
		return reportForm;
	}
	/**
	 * Set pagination Property
	 * @param reportForm
	 * @param map
	 */
	private void setPaginationProperty(ReportForm reportForm, Map map) {
		logger.info("Map: "+map);
		reportForm.setPrePackageCode(map.get("prePageCode").toString());
		reportForm.setPreClaimNum(map.get("preClaimNum").toString());
		reportForm.setNextPackageCode(map.get("nextPageCode").toString());
		reportForm.setNextClaimNum(map.get("nextClaimNum").toString());
		reportForm.setTotalNum(map.get("totalNum").toString());
		reportForm.setTotalPage(map.get("totalPage").toString());
	}

	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#referenceData(javax.servlet.http.HttpServletRequest, int)
	 */
	protected Map referenceData(HttpServletRequest request, int page) throws Exception {
		Map referenceData = new HashMap();
		referenceData.put("years", DateConvertor.exportYears());
		referenceData.put("months", DateConvertor.exportMonths());
		referenceData.put("days", DateConvertor.exportDays());

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
	
	/*
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#onBindAndValidate(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException, int)
	 */
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) throws Exception{
		//Steven ADD FOR AML.34188 20140730 START
		logger.info("Enter to ReportController.onBindAndValidate()...");
		//Steven ADD FOR AML.34188 20140730 End
		if (errors.hasErrors()) {
			return;
		}		
		ReportForm r = (ReportForm) command;
		//Steven ADD FOR AML.34188 20140730 START
		if(null != r.getChoose() && !"".equals(r.getChoose())){
			logger.info("ReportForm.choose = " + r.getChoose());
		}
		if(null != request.getParameter("choose") && !"".equals(request.getParameter("choose"))){
			logger.info("request.getParameter(choose) = " + request.getParameter("choose"));
		}
		
		//Steven ADD FOR AML.34188 20140730 End
		ReportFormValidator reportFormValidator = (ReportFormValidator) getValidator();
		
		int targetPage = getTargetPage(request, page);
		
		switch (page) {
		case 0:
			if (targetPage==0){
				//Steven DEL FOR AML.34188 20140730 START
				//if finds the parameter value is not equal to true, set the boolean to false 
				//r.setProgressOption(ServletRequestUtils.getBooleanParameter(request, "progressOption", false));
				//r.setCompleteOption(ServletRequestUtils.getBooleanParameter(request, "completeOption", false));
				//r.setBillOption(ServletRequestUtils.getBooleanParameter(request, "billOption", false));
				//Steven DEL FOR AML.34188 20140730 END
				reportFormValidator.validateDate(r, errors);
				reportFormValidator.validateKeyword(r,errors);
			}
			break;		
		}
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#postProcessPage(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors, int)
	 */
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		//Steven ADD FOR AML.34188 20140730 START
		logger.info("Enter to ReportController.postProcessPage()...");
		//Steven ADD FOR AML.34188 20140730 END
		if (errors.hasErrors()) {
			return;
		}
		UserSession session = (UserSession) request.getSession().getAttribute("userSession");
	
		Agent agent = session.getAgent();
		
		ReportForm reportForm = (ReportForm) command;
		reportForm.setPrePackageCode(null);
		reportForm.setPreClaimNum(null);
		
		int targetPage = getTargetPage(request, page);
		//int index = reportForm.getClaimIndex();

        logger.info("page:"+page);
		logger.info("targetPage:"+targetPage);
		logger.info("choose:"+request.getParameter("choose"));
		logger.info("choose:"+reportForm.getChoose());
		logger.info("choose:"+session.getChoose());
		session.setChoose(reportForm.getChoose());
		
		//Yalin ADD FOR AML.34188 20140731 START
		ActionCode actionCode =ActionCode.NAR_RETRIEVAL_ALL;
		String choose = session.getChoose();
		if(null!=choose &&!"".equals(choose)){
			actionCode = (ActionCode) chooseMap.get(choose);
		}
		//Yalin ADD FOR AML.34188 20140731 END
		
		switch (page) {
		case 0:
			if (targetPage == 0) {
				try {
					Date fromDate = DateConvertor.parseDate(reportForm.getFromYear(), reportForm.getFromMonth(), reportForm.getFromDay());
					Date toDate = DateConvertor.parseDate(reportForm.getToYear(), reportForm.getToMonth(), reportForm.getToDay());
					
					//Yalin UPDATE FOR AML.34188 20140731 START
					
					//List allClaims = clsFacade.retrieveCollectionList(agent, ActionCode.NAR_RETRIEVAL_ALL, session, agent.getTimeZone(), fromDate, toDate);
					//List claims = getFilteredClaims(reportForm, allClaims);
				
					List allClaims = clsFacade.retrieveCollectionList(agent, actionCode, "ALL", agent.getTimeZone(), fromDate, toDate, null, null, "1");
					
					if(null != allClaims && allClaims.size()>0){
						setPaginationProperty(reportForm, (Map) allClaims.get(0));
						allClaims.remove(0);//remove map
					}
					reportForm.setClaims(allClaims);
					reportForm.setCurrentPage(1);
					//Yalin UPDATE FOR AML.34188 20140731 END

				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			break;
		}
	}

	protected int getCurrentPage(HttpServletRequest request){
		int currentPage = super.getCurrentPage(request);
		if (currentPage == 1) {
			currentPage = 0;
		}
		return currentPage;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), command);
		return new ModelAndView(ViewConstants.REPORT_REPORT, model);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processCancel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), new ReportForm());
		return new ModelAndView(ViewConstants.REPORT_REPORT, model);
	}
	
	//Yalin DEL FOR AML.34188 20140827 START
	/*protected List getFilteredClaims(ReportForm reportForm, List allClaims) {
		List claims = new ArrayList();
		if (reportForm.getKeyword() != null) {
			reportForm.setKeyword(reportForm.getKeyword().trim());
		}
		f (reportForm.isProgressOption()) {
			CollectionUtils.select(allClaims, new InProgressNARClaimDetailsPredicate(), claims);
		}
		if (reportForm.isCompleteOption()) {
			CollectionUtils.select(allClaims, new CompletedNARClaimDetailsPredicate(), claims);
		}
		if (reportForm.isBillOption()) {
			CollectionUtils.select(allClaims, new BilledNARClaimDetailsPredicate(), claims);
		}
		if ("progressOption".equals(reportForm.getChoose())) {
			CollectionUtils.select(allClaims, new InProgressNARClaimDetailsPredicate(), claims);
		}
		if ("completeOption".equals(reportForm.getChoose())) {
			CollectionUtils.select(allClaims, new CompletedNARClaimDetailsPredicate(), claims);
		}
		if ("billOption".equals(reportForm.getChoose())) {
			CollectionUtils.select(allClaims, new BilledNARClaimDetailsPredicate(), claims);
		}
		if ("all".equals(reportForm.getChoose())) {
			CollectionUtils.select(allClaims, new BilledNARClaimDetailsPredicate(), claims);
		}
		if (StringUtils.isNotEmpty(reportForm.getKeyword())) {
			CollectionUtils.filter(claims, new HandledByAgentNARClaimDetailsPredicate(reportForm.getKeyword()));
		}
		return claims;
	}*/
	//Yalin DEL FOR AML.34188 20140827 END
	
	protected void setDateToCurrent(ReportForm reportForm) {
		// from voidClaim.do back to report.do, set to current date in case if
		// any date field is empty
		Calendar c = Calendar.getInstance();
		if (reportForm.getFromYear().equals("")) {
			reportForm.setFromYear(Integer.toString(c.get(Calendar.YEAR)));
		}
		if (reportForm.getFromMonth().equals("")) {
			reportForm.setFromMonth(Integer.toString(c.get(Calendar.MONTH) + 1));
		}
		if (reportForm.getFromDay().equals("")) {
			reportForm.setFromDay(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
		}
		if (reportForm.getToYear().equals("")) {
			reportForm.setToYear(Integer.toString(c.get(Calendar.YEAR)));
		}
		if (reportForm.getToMonth().equals("")) {
			reportForm.setToMonth(Integer.toString(c.get(Calendar.MONTH) + 1));
		}
		if (reportForm.getToDay().equals("")) {
			reportForm.setToDay(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
		}
	}
}
