/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.Unbilled;
import com.asiamiles.partnerportal.domain.BillingForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.domain.Unbilled.Establishment;
import com.asiamiles.partnerportal.domain.logic.BillingFormValidator;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;
import com.asiamiles.partnerportal.cls.ActionCode;
import com.asiamiles.partnerportal.common.BillingDAO;
/**
 * Wizard Controller for Billing functions.
 * @author CPPKENW
 *
 */
public class BillingController extends AbstractWizardFormController {

	private static final String[] PAGES = new String[]{
			ViewConstants.BILLING_ESTABLISHMENT, 
			ViewConstants.BILLING_DETAILS,
			ViewConstants.BILLING_CONFIRMATION,
			ViewConstants.BILLING_ACKNOWLEDGEMENT,
			ViewConstants.BILLING_UNBILLED_EXPORT,
			ViewConstants.BILLING_BILLED_EXPORT}; 
	
	private CLSFacade clsFacade;
	//private SiteProperties siteProperties;

	private Logger logger = AppLogger.getAppLogger();
	
	/**
	 * 
	 */
	public BillingController() {
		setCommandName("billingForm");
		setPages(PAGES);
	}

	/**
	 * @param clsFacade The clsFacade to set.
	 */
	public void setClsFacade(CLSFacade clsFacade) {
		this.clsFacade = clsFacade;
	}
	
/*	*//**
	 * @param siteProperties The siteProperties to set.
	 *//*
	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}	*/
	
	/*
	 *  (non-Javadoc)-----------------------------------------展示表单时的表单数据
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {//----------------------------------------------------1
		logger.info("start formBackingObject");
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);	
		//Steven ADD for AML34188 clear cache 20140813 START
		String post=request.getParameter("post");
		if(null==post){
			clearUserSession(userSession);
		}
		//Steven ADD for AML34188 clear cache 20140813 END
		Agent agent = userSession.getAgent();  
		BillingForm billingForm = new BillingForm(); //Construct backing object				
		//CPPJULI UPDATE FOR AML.34188 20141023 START
		//billingForm.setTimeZone((int)Math.round(agent.getTimeZone().doubleValue()/100.0));
		billingForm.setTimeZone(agent.getTimeZone().intValue());
		//CPPJULI UPDATE FOR AML.34188 20141023 END
		billingForm.setAgent(agent);
		try{
			Unbilled unbilledNAR = clsFacade.retrieveInvoice(agent);
			billingForm.setCutOffDay(unbilledNAR.getMonthlyCutoffDay());
			billingForm.setEstablishments(unbilledNAR.getEstablishments());
			logger.info("unbilledNAR.getEstablishments().size():"+unbilledNAR.getEstablishments().size());
			//Steven ADD for AML34188 20140730 START
			filterBilledEstablistment(unbilledNAR,agent.getAgentID(),billingForm.getCutOffDate());
			//Steven ADD for AML34188 20140730 END
			logger.info("billingForm.getUnbilledEstablishments().size():"+billingForm.getUnbilledEstablishments().size());
			if (billingForm.getUnbilledEstablishments().size() == 1) {
				
				billingForm.setSelectedUnbilledEstablishment((Unbilled.Establishment)billingForm.getUnbilledEstablishments().get(0));
				billingForm.setEstablishmentIndex(0);
												
				try {
					Unbilled.Establishment e = billingForm.getSelectedUnbilledEstablishment();
					userSession.setEstablishmentCode(e.getEstablishmentCode());
					logger.info("agent:"+agent);
					logger.info("e.getEstablishmentCode():"+e.getEstablishmentCode());
					logger.info("agent.getTimeZone():"+agent.getTimeZone());
					logger.info("e.getOldestCollectionTimestamp():"+e.getOldestCollectionTimestamp());
					logger.info("billingForm.getCutOffDate():"+billingForm.getCutOffDate());

					List list = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_UNBILLED,e.getEstablishmentCode(),agent.getTimeZone(),e.getOldestCollectionTimestamp(),billingForm.getCutOffDate(),userSession.getNextClaimNum(),userSession.getNextPackageCode(),"1");

					//Steven ADD for AML34188 20140730 START
					if(null!= list && list.size()>0){
						billingForm=setMapToBillingForm((Map)list.get(0),billingForm);
						list.remove(0);
					}
					
					filterClaims(userSession, billingForm, e.getEstablishmentCode(), list);
				    billingForm.setIndex(1);	
				    //Steven ADD for AML34188 20140730 END
					billingForm.setClaims(list);
				} catch (CLSException e) {
					throw new SystemException(SystemException.CLS_REQUEST_FAILURE, "Cannot retrieve unbilled claims: " + e.getMessage(), e);
				}

			}
		}catch (CLSException e){
			throw new SystemException(SystemException.CLS_REQUEST_FAILURE, "Cannot retrieve invoice: " + e.getMessage(), e);
		}
		return billingForm;
	}

	private BillingForm setMapToBillingForm(Map map, BillingForm billingForm) {
		// TODO Auto-generated method stub
		billingForm.setNextPageCode(map.get("nextPageCode").toString());
		billingForm.setNextClaimNum(map.get("nextClaimNum").toString());
		billingForm.setPrePageCode(map.get("prePageCode").toString());
		billingForm.setPreClaimNum(map.get("preClaimNum").toString());
		billingForm.setTotalNum(map.get("totalNum").toString());
		billingForm.setTotalPage(map.get("totalPage").toString());
		
		return billingForm;
	}

	public void clearUserSession(UserSession userSession) {
		logger.info("clear user session!!!!!");
		if(userSession.getClaims()!=null)
		logger.info("-------------"+userSession.getClaims().toString()+"-----------");
		userSession.getClaims().clear();
		userSession.getUnbillList().clear();
		userSession.setNextPackageCode(null);
		userSession.setNextClaimNum(null);
		userSession.setPrePackageCode(null);
		userSession.setPreClaimNum(null);
	}

	//Steven ADD for AML.34188 filter claims 20140716 START
	  /**
	 * filter billed establishment code
	 * @param unbilledNAR
	 * @param agentID
	 * @param cutoffDate
	 */
	public void filterBilledEstablistment(Unbilled unbilledNAR,String agentID,Date cutoffDate) {
		List establishlist = unbilledNAR.getEstablishments();
		if(null == establishlist || establishlist.size() < 1){
			logger.info("There is no establishment filter...");
			return;
		}else{
			//print log for check data
			for(int j=0;j<establishlist.size();j++){
				Establishment est=(Establishment)establishlist.get(j);
				logger.info("establishment.getOldestCollectionTimestamp() = " + est.getOldestCollectionTimestamp());
				logger.info("cutOffDate = " + cutoffDate);
				logger.info("establishment.getLastBillingDate() = " + est.getLastBillingDate());
			}
		}
		logger.info("before filter establishment size: " + unbilledNAR.getEstablishments().size());
		BillingDAO dao = BillingDAO.getInstance();
		List billedEstlist = dao.getEstablishmentCode(agentID);
		if(null == billedEstlist || billedEstlist.size() < 1){
			logger.info("after filter establishment size: " + unbilledNAR.getEstablishments().size());
			return;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		for(int j=0;j<establishlist.size();j++){
			Establishment est=(Establishment)establishlist.get(j);
			StringBuffer estCompare = new StringBuffer();
			estCompare.append(est.getEstablishmentCode()).append("-_-");
			estCompare.append(format.format(cutoffDate));
			logger.info("billedEstlist:"+billedEstlist);
			logger.info("estCompare.toString():"+estCompare.toString());
			if(null!=billedEstlist&&0!=billedEstlist.size()&&billedEstlist.contains(estCompare.toString())){
				unbilledNAR.getEstablishments().remove(est);
				
			}
		}
		logger.info("after filter establishment size: "+unbilledNAR.getEstablishments().size());
	}
	//Steven ADD for AML.34188 filter claims 20140716 END

	
	//Steven ADD for AML.34188 set unbilled claims to billingForm 20140716 START
	/**
	 * get claims which need not to be billed by steven 20140730
	 * @param userSession
	 * @param post
	 * @param billingForm
	 */
	public void setUnbillListToBillingForm(UserSession userSession, String post,//取出usersession中的claims和unbillist，claims-->unbillist-->billingForm
			BillingForm billingForm) {
		logger.info("setUnbillListToBillingForm start.....");
		List lastClaims=userSession.getClaims();
		List unbilllist=userSession.getUnbillList();
		if(lastClaims!=null && lastClaims.size()>0){
			logger.info("lastClaims:"+lastClaims.toString());
		}
		if(unbilllist!=null && unbilllist.size()>0){
			logger.info("unbilllist:"+unbilllist.toString());
		}
		logger.info("post:"+post);//-----------------------------------11111000000000000000000000
		if(null!=post&&!"".equals(post)){
			String str[]=post.split("|");
			for(int i=0;i<str.length;i++){
				if(str[i].equals("0")){
					NARClaimDetails obj=(NARClaimDetails)lastClaims.get(i-1);
					if(!unbilllist.contains(obj.getClaimNo())){
						unbilllist.add(obj.getClaimNo());	
						logger.info("add to unbilllist, claimNO = " + obj.getClaimNo().toString());
					}				
				}else if(str[i].equals("1")){//选中的从unbilllist移除
					NARClaimDetails obj=(NARClaimDetails)lastClaims.get(i-1);
					if(unbilllist.contains(obj.getClaimNo())){
						for(int j=0; j<unbilllist.size(); j++){
							if(unbilllist.get(j).toString().equals(obj.getClaimNo().toString())){
								unbilllist.remove(j);
								logger.info("remove from unbilllist, claimNO = " + obj.getClaimNo().toString());
							}
						}
					}
				}
			}
			billingForm.setUnbillList(unbilllist);
		}
		if(billingForm.getUnbillList()!=null && billingForm.getUnbillList().size()>0){
			logger.info("unbilllist:"+unbilllist.toString());
		}
		logger.info("setUnbillListToBillingForm end.....");
	}	
	//Steven ADD for AML.34188 set unbilled claims to billingForm 20140716 END

	//Steven ADD for AML.34188 filter claims 20140716 START
	/**
	 * pre-check all claims as being billed
	 * @param userSession
	 * @param billingForm
	 * @param e
	 * @param claims
	 */
	public void filterClaims(UserSession userSession, BillingForm billingForm,String estcode, List claims) {
		List unlist=billingForm.getUnbillList();
		billingForm.getClaims().clear();
		for (Iterator it = claims.iterator(); it.hasNext();) {
			NARClaimDetails claim = (NARClaimDetails)it.next();
			if(unlist.contains(claim.getClaimNo())){
				claim.setToBeBilled(false);
			}else{
				claim.setToBeBilled(true);
			}
			billingForm.getClaims().add(claim);
		}
		billingForm.setEstablishmentCode(estcode);
	}
	
	//Steven ADD for AML.34188 filter claims 20140716 END

	//Steven ADD for AML.34188 set billingForm and index 20140716 START
	/**
	 * set billingForm and index by Steven 20140730
	 * @param index
	 * @param userSession
	 * @param nextPageCode
	 * @param prePageCode
	 * @param billingForm
	 * @return
	 */
	public int setBillingFormAndIndex(int currentPage, UserSession userSession, String nextPageCode,
			String prePageCode, BillingForm billingForm,String com) {
		if((null!=nextPageCode&&!"".equals(nextPageCode))
				&&(null==prePageCode||"".equals(prePageCode))){
			logger.info("NEXT PAGE");
			userSession.setNextPackageCode(userSession.getNextPackageCode());
			userSession.setNextClaimNum(userSession.getNextClaimNum());
			if(null==userSession.getNextPackageCode() || "".equals(userSession.getNextPackageCode())){
				currentPage = 1;
			}else{
				currentPage = userSession.getCurrentPage()+1;
			}
		}else if((null!=prePageCode&&!"".equals(prePageCode))
				&&(null==nextPageCode||"".equals(nextPageCode))) {
			logger.info("PRE PAGE");
			userSession.setNextPackageCode(userSession.getPrePackageCode());
			userSession.setNextClaimNum(userSession.getPreClaimNum());
			
			if(null==userSession.getPrePackageCode() || "".equals(userSession.getPrePackageCode())){
				currentPage = 1;
			}else{
				currentPage = userSession.getCurrentPage()-1;
			}
		}
		return currentPage;
	}
	//Steven ADD for AML.34188 set billingForm and index 20140716 END
	
	//Steven ADD for AML34188 20140715 START
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
				
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String com=request.getParameter("com");
		String post=request.getParameter("post");
		if (isFormSubmission(request)||"1".equals(com)||null!=post) {
		      try
		      {
		        Object command = getCommand(request);
		        ServletRequestDataBinder binder = bindAndValidate(request, command);
		        BindException errors = new BindException(binder.getBindingResult());
		        return processFormSubmission(request, response, command, errors);
		      }
		      catch (HttpSessionRequiredException ex)
		      {
		        return handleInvalidSubmit(request, response);
		      }

		    }

		    return showNewForm(request, response);
	}
	
	//Steven ADD for AML34188 20140715 END

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#getInitialPage(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	protected int getInitialPage(HttpServletRequest request, Object command) {
		BillingForm billingForm = (BillingForm)command;
		if (billingForm.getUnbilledEstablishments().size()== 1) {
			return 1;
		}
		return 0;
		
	}
	/* (non-Javadoc)-------------------------提供给每一页完成时的后处理方法
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#postProcessPage(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors, int)
	 */
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {//-------------------------------------2
		logger.info("enter postProcessPage()");
		if (errors.hasErrors()) {
			return;
		}
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();

		BillingForm billingForm = (BillingForm) command;		
		int targetPage = getTargetPage(request, page);	//---------------------------返回请求中指定的目标页面。page当前页面
		//Steven ADD for AML34188 20140712 START

		logger.info("invoiceNo: "+billingForm.getInvoiceNo());
		billingForm.setUnbillList(session.getUnbillList());	
		
		Unbilled.Establishment establishment;
		List c=billingForm.getClaims();
		if(c!=null && c.size()>0){
			logger.info("billingForm.getClaims():"+c.toString());
		}
		if(billingForm.getUnbillList()!=null && billingForm.getUnbillList().size()>0){
			logger.info("billingForm.getUnbillList():"+billingForm.getUnbillList().toString());
		}
		for(int i=0;i<c.size();i++){
			NARClaimDetails claim = (NARClaimDetails)c.get(i);
			if(!claim.isToBeBilled()){//-----------------------------不是billed的添加到unbilllist
				if(!billingForm.getUnbillList().contains(claim.getClaimNo())){
					billingForm.getUnbillList().add(claim.getClaimNo());
					logger.info("add to billingForm,claimNO = " + claim.getClaimNo().toString());
				}
			}else{//选中的从unbilllist移除
				if(billingForm.getUnbillList().contains(claim.getClaimNo())){
					for(int j=0; j<billingForm.getUnbillList().size(); j++){
						if(billingForm.getUnbillList().get(j).toString().equals(claim.getClaimNo().toString())){
							billingForm.getUnbillList().remove(j);
							logger.info("remove from billingForm,claimNO = " + claim.getClaimNo().toString());
						}
					}
				}
			}
		}
		if(billingForm.getUnbillList()!=null && billingForm.getUnbillList().size()>0){
			logger.info("billingForm.getUnbillList():"+billingForm.getUnbillList().toString());
		}
			
		logger.info("page = " + page);
		logger.info("targetPage = " + targetPage);
		logger.info("Unbilllist size = " + billingForm.getUnbillList().size());
		logger.info("Claim List size = " + billingForm.getClaims().size());
		logger.info("Total num = " + billingForm.getTotalNum());
		logger.info("BillingForm.PrePageCode = " + billingForm.getPrePageCode() + ", PreClaimNum = " + billingForm.getPreClaimNum());
		logger.info("BillingForm.NextPageCode = " + billingForm.getNextPageCode() + ", NextClaimNum = " + billingForm.getNextClaimNum());
		logger.info("request.getParameter.nextPageCode = "+request.getParameter("nextPageCode"));
		logger.info("request.getParameter.prePageCode = "+request.getParameter("prePageCode"));
		//Steven ADD for AML34188 20140712 END
		
		switch (page) {
		case 0:
			List list = billingForm.getUnbilledEstablishments();
			int i = billingForm.getEstablishmentIndex();
			establishment = (Unbilled.Establishment)list.get(i);
			billingForm.setSelectedUnbilledEstablishment(establishment);
			try {
				List claims = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_UNBILLED,establishment.getEstablishmentCode(),agent.getTimeZone(),establishment.getOldestCollectionTimestamp(),billingForm.getCutOffDate(),null,null,"1");
				if(null!= claims && claims.size()>0){
					claims.remove(0);
				}
				//pre-check all claims as being billed
				for (Iterator it = claims.iterator(); it.hasNext();) {
					NARClaimDetails claim = (NARClaimDetails)it.next();
					claim.setToBeBilled(true);
				}
				
				billingForm.setClaims(claims);
			} catch (CLSException e) {
				errors.reject(e.getErrorMessageCode(), e.getMessage());
			}
			break;
			//Steven ADD for AML34188 20140812 START	
		case 1:
			if(targetPage==1){
				int currentPage = 1;
				String nextPageCode=request.getParameter("nextPageCode");
				String prePageCode=request.getParameter("prePageCode");
				String post=request.getParameter("post");
				logger.info("post = "+post);//------------------------post = 0111111111---------------选中是1没选中是0
				if(null == post){
					clearUserSession(session);
				}
				setUnbillListToBillingForm(session, post, billingForm);//--------------------------------取出usersession中的claims和unbillist，claims-->unbillist-->billingForm
				/*Unbilled unbilledNAR = clsFacade.retrieveInvoice(agent);
				billingForm.setCutOffDay(unbilledNAR.getMonthlyCutoffDay());
				billingForm.setEstablishments(unbilledNAR.getEstablishments());*/
				currentPage = setBillingFormAndIndex(currentPage, session, nextPageCode, prePageCode, billingForm, "");
				Unbilled.Establishment e = billingForm.getSelectedUnbilledEstablishment();
				session.setEstablishmentCode(e.getEstablishmentCode());
				List claim = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_UNBILLED,e.getEstablishmentCode(),agent.getTimeZone(),e.getOldestCollectionTimestamp(),billingForm.getCutOffDate(),session.getNextClaimNum(),session.getNextPackageCode(),"1");
				
				if(null!= claim && claim.size()>0){
					billingForm=setMapToBillingForm((Map)claim.get(0),billingForm);
					claim.remove(0);
				}
				
				filterClaims(session, billingForm, e.getEstablishmentCode(), claim);
				
			    billingForm.setIndex(currentPage);
			    
				billingForm.setClaims(claim);
				logger.info("after PrePageCode = " + billingForm.getPrePageCode() + ", PreClaimNum = " + billingForm.getPreClaimNum());
				logger.info("after NextPageCode = " + billingForm.getNextPageCode() + ", NextClaimNum = " + billingForm.getNextClaimNum());
			}
			break;
			//Steven ADD for AML34188 20140812 END
		case 2:
			//Steven ADD for AML34188 20140712 START
			if(targetPage==2){
				int currentPage = 1;
				
				String nextPageCode=request.getParameter("nextPageCode");
				String prePageCode=request.getParameter("prePageCode");
				String com=request.getParameter("com");
				if("1".equals(com)){
					billingForm.setAgent(agent);
					Unbilled unbilledNAR = clsFacade.retrieveInvoice(agent);
					billingForm.setCutOffDay(unbilledNAR.getMonthlyCutoffDay());
					billingForm.setEstablishments(unbilledNAR.getEstablishments());
					billingForm.setSelectedUnbilledEstablishment((Unbilled.Establishment)billingForm.getUnbilledEstablishments().get(0));
					billingForm.setEstablishmentIndex(0);

					currentPage = setBillingFormAndIndex(currentPage, session, nextPageCode, prePageCode,
							billingForm,com);
					Unbilled.Establishment e = billingForm.getSelectedUnbilledEstablishment();
					
					session.setEstablishmentCode(e.getEstablishmentCode());
					List claim = clsFacade.retrieveCollectionList(agent,ActionCode.NAR_RETRIEVAL_UNBILLED,e.getEstablishmentCode(),agent.getTimeZone(),e.getOldestCollectionTimestamp(),billingForm.getCutOffDate(),session.getNextClaimNum(),session.getNextPackageCode(),"1");
					
					if(null!= claim && claim.size()>0){
						billingForm=setMapToBillingForm((Map)claim.get(0),billingForm);
						claim.remove(0);
					}
					
					filterClaims(session,billingForm,e.getEstablishmentCode(),claim);
				    billingForm.setIndex(currentPage);
				    billingForm.getClaims().clear();
				    
				    for(int j=0;j<claim.size();j++){
				    	NARClaimDetails cl = (NARClaimDetails)claim.get(j);
				    	if(billingForm.getUnbillList().contains(cl.getClaimNo())){
				    		cl.setToBeBilled(false);				    		
				    	}else{
				    		cl.setToBeBilled(true);
				    	}				    	
				    	billingForm.getClaims().add(cl);
				    }				    
				}
			}
			//Steven ADD for AML34188 20140712 END
			if (targetPage == 3) {
				establishment = billingForm.getSelectedUnbilledEstablishment();
				// comment below for testing purpose
				try {
					printUnbillList(billingForm, "Before");
					List billedClaims = clsFacade.billClaims(agent, establishment.getEstablishmentCode(),agent.getTimeZone(), establishment.getOldestCollectionTimestamp(), billingForm.getCutOffDate(), billingForm.getInvoiceNo().toString(), billingForm.getUnbillList());
					billingForm.setClaims(billedClaims);
					printUnbillList(billingForm, "After");
					String billedCount=request.getParameter("billedCount");
					BillingDAO dao=BillingDAO.getInstance();
					dao.insertLog(billingForm,billedCount);
				} catch (CLSException e) {
					errors.reject(e.getErrorMessageCode(), e.getMessage());
				}
			}
			break;
		}
		
	}
	
	/**
	 * print unbill claim list information
	 * @param billingForm
	 * @param position
	 */
	public void printUnbillList(BillingForm billingForm, String position){
		List unbillList = billingForm.getUnbillList();
		logger.debug(position + " CLS process, the excluded list size: " + unbillList.size());
		String unbillStr = "";
		for(int j =0; j < unbillList.size(); j++){
			unbillStr = unbillStr + (Integer)unbillList.get(j) + ",";
		}
		logger.debug(position + " CLS process, the excluded claim list: " + unbillStr);
	}

	/*
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#onBindAndValidate(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException, int)
	 */
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) throws Exception{
		if (errors.hasErrors()) {
			return;
		}
		BillingForm billingForm = (BillingForm) command;
		BillingFormValidator billingFormValidator = (BillingFormValidator) getValidator();

		int targetPage = getTargetPage(request, page);

		switch (page) {
		case 1:
			if (targetPage == 2) {
				billingFormValidator.validateDetail(billingForm, errors);
				billingFormValidator.validateCheckbox(billingForm, errors);
			}
			break;
		}
	}
	
	protected int getCurrentPage(HttpServletRequest request){
		int currentPage = super.getCurrentPage(request);
		if (currentPage == 4) {
			currentPage = 1;
		}
		return currentPage;
	}
	
	
	/* (non-Javadoc)-------------------------------填写成功后的处理方法
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {	
		Map model = new HashMap();
		model.put(getCommandName(), command);
		return new ModelAndView (ViewConstants.BILLING_ACKNOWLEDGEMENT,model);//---------------------// 重定向到成功页面
	}


	/* (non-Javadoc)-------------------------------取消填写后的处理方法
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processCancel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = new HashMap();
		model.put(getCommandName(), new BillingForm());
		return new ModelAndView(ViewConstants.BILLING_ESTABLISHMENT, model);
	}
		
}
