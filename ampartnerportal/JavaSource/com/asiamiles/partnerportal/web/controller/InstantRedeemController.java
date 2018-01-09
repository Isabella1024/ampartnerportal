/*
 * Created on Aug 15, 2014
 *
 */
package com.asiamiles.partnerportal.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.asiamiles.partnerportal.util.InstantRedeemGroup;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.util.WebUtil;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.iRedeem.bean.PersonalOfferBean;
import com.cathaypacific.iRedeem.client.proxy.PersonlOfferProxyBean;
import com.cathaypacific.iRedeem.client.request.PersonalOfferRequestBean;
import com.cathaypacific.iRedeem.client.response.PersonalOffersResponseBean;
import com.cathaypacific.utility.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 * Wizard Controller for Verifying Instant Redeem QR Code
 * 
 * @author CPPBENL
 *
 */
public class InstantRedeemController extends AbstractWizardFormController {

	private static final String[] PAGES = new String[]{ViewConstants.INSTANT_REDEEM_COLLECT_CLAIMS, ViewConstants.INSTANT_REDEEM_ACKNOWLEDGEMENT};
	

	
	private CLSFacade clsFacade;
	private URLHelper urlHelper;
	private SiteProperties siteProperties;
	private InstantRedeemGroup instantRedeemGroup;
	private Logger logger = AppLogger.getAppLogger();
	
	private PersonalOfferRequestBean personalOfferRequest;
	
	private PersonlOfferProxyBean offerProxy;

	public InstantRedeemController() {
		setCommandName("instantRedeemForm");
		setPages(PAGES);
	}

	
	/**
	 * @param urlHelper The urlHelper to set.
	 */
	public void setUrlHelper(URLHelper urlHelper) {
		this.urlHelper = urlHelper;
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

	public InstantRedeemGroup getInstantRedeemGroup() {
		return instantRedeemGroup;
	}

	public void setInstantRedeemGroup(InstantRedeemGroup instantRedeemGroup) {
		this.instantRedeemGroup = instantRedeemGroup;
	}
	
	public PersonalOfferRequestBean getPersonalOfferRequest() {
		return personalOfferRequest;
	}

	public void setPersonalOfferRequest(
			PersonalOfferRequestBean personalOfferRequest) {
		this.personalOfferRequest = personalOfferRequest;
	}

	public PersonlOfferProxyBean getOfferProxy() {
		return offerProxy;
	}


	public void setOfferProxy(PersonlOfferProxyBean offerProxy) {
		this.offerProxy = offerProxy;
	}


	protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {
		logger.debug("Entering InstantRedeemController formBackingObject()");

		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		String memberId = null;
		String claimNO = null;
		String securityCode = null;
		if (StringUtils.isNotEmpty(request.getParameter("memberId")) && StringUtils.isNotEmpty(request.getParameter("claimNO")) && StringUtils.isNotEmpty(request.getParameter("securityCode"))) {
			memberId = request.getParameter("memberId");
			claimNO = request.getParameter("claimNO");
			securityCode = request.getParameter("securityCode");
		} else {
			Hashtable pReqTable = parseHttpRequest(request);
			memberId = (String) pReqTable.get("memberId");
			claimNO = (String) pReqTable.get("claimNO");
			securityCode = (String) pReqTable.get("securityCode");
		}
		PaperlessRedemptionForm instantRedeemForm = new PaperlessRedemptionForm(memberId, claimNO, securityCode);

		logger.debug("Exiting InstantRedeemController formBackingObject()");

		return instantRedeemForm;
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) throws Exception {
		logger.debug("Entering InstantRedeemController onBindAndValidate()");
		PaperlessRedemptionForm instantRedeemForm = (PaperlessRedemptionForm) command;
		PaperlessRedemptionFormValidator paperlessRedemptionFormValidator = (PaperlessRedemptionFormValidator) getValidator();
		
		switch (page) {
			case 0:
				if (isFinishRequest(request)) {
					paperlessRedemptionFormValidator.validateRefAndReason(instantRedeemForm, errors, request);
				}
				break;
		}
		
		logger.debug("Exiting InstantRedeemController onBindAndValidate()");
	}
	
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		logger.debug("Entering InstantRedeemController referenceData()");
		UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = userSession.getAgent();
		Map refData = new HashMap();
		refData.put("agentType",agent.getAdministratorIndicator());
		
		int gracePeriod = Integer.parseInt(siteProperties.getProperty(SiteProperties.GRACE_PERIOD));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -gracePeriod);
		refData.put("expiryDate", c.getTime()); //note that the expiry date = system date - grace period

		switch (page) {
		case 0:
			logger.debug("Page 0 logic - isFinishRequest(request) = " + isFinishRequest(request) + "  |  isCancelRequest(request) = " + isCancelRequest(request));
			if (!isFinishRequest(request) && !isCancelRequest(request)) {
				PaperlessRedemptionForm instantRedeemForm = (PaperlessRedemptionForm) command;
				PaperlessRedemptionFormValidator paperlessRedemptionFormValidator = (PaperlessRedemptionFormValidator) getValidator();

				paperlessRedemptionFormValidator.validateMemberInfo(instantRedeemForm, errors);
				paperlessRedemptionFormValidator.validateClaims(instantRedeemForm, errors);

				String memberId = null;
				String claimNO = null;
				String securityCode = null;
				String packageCode = null;
				if (StringUtils.isNotEmpty(request.getParameter("memberId")) && StringUtils.isNotEmpty(request.getParameter("claimNO")) && StringUtils.isNotEmpty(request.getParameter("securityCode"))) {
					memberId = request.getParameter("memberId");
					claimNO = request.getParameter("claimNO");
					securityCode = request.getParameter("securityCode");
					packageCode = request.getParameter("packageCode");

				} else {
					Hashtable pReqTable = parseHttpRequest(request);
					memberId = (String) pReqTable.get("memberId");
					claimNO = (String) pReqTable.get("claimNO");
					securityCode = (String) pReqTable.get("securityCode");
					packageCode = (String) pReqTable.get("packageCode");
				}
				if (StringUtils.isNotEmpty(memberId) && StringUtils.isNotEmpty(claimNO) && StringUtils.isNotEmpty(securityCode)) {
						try {
							Map claimsMap = clsFacade.retrieveClaims(agent, instantRedeemForm.getMemberId(), instantRedeemForm.getMemberEmbossedName(), instantRedeemForm.getClaims());
							instantRedeemForm.setNARDetails((List)claimsMap.get("NARDetails"));
							instantRedeemForm.setMemberEmbossedName((String)claimsMap.get("MemberName"));
							refData.put(getCommandName(), instantRedeemForm);
							if (instantRedeemGroup.verifyPackageCodeAllowanceByAgent(agent.getPartnerCode(), agent.getAgentID(), packageCode)) {
								for (int i=0; i < instantRedeemForm.getNARDetails().size(); i++) {
									NARClaimDetails claimDetails = (NARClaimDetails) instantRedeemForm.getNARDetails().get(i);
									PaperlessClaim claim = claimDetails.getPaperlessClaim();
									
									if(!claimDetails.getClaimStatusCode().equals("00")) {
										String invalidClaimURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_INVALID_CLAIM_URL);
										String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), invalidClaimURL, null, urlHelper.isSSL(request));
										errors.reject("invalidClaim",redirectURL);
										logger.debug("Claim #" + claim.getClaimNumber() + " is invalid");	
									}

									if(claimDetails.getCollectionTime() == null && claimDetails.getCompletionTime() == null && claimDetails.getConsumptionEndDate() != null
											&& claimDetails.getConsumptionEndDate().compareTo(c.getTime()) < 0) {
										String expiredOfferURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_EXPIRED_OFFER_URL);
										String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), expiredOfferURL, null, urlHelper.isSSL(request));
										errors.reject("claimExpired",redirectURL);
										logger.debug("Claim #" + claim.getClaimNumber() + " is expired");
									}
									
									if(claimDetails.getCollectionTime() != null && claimDetails.getCompletionTime() == null) {
										errors.reject("claimInProgress","Claim #" + claim.getClaimNumber() + " is in-progress");
										logger.debug("Claim #" + claim.getClaimNumber() + " is in-progress");
									}
									
									if(claimDetails.getCollectionTime() != null && claimDetails.getCompletionTime() != null) {
										String redeemedOfferURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_REDEEMED_OFFER_URL);
										String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), redeemedOfferURL, null, urlHelper.isSSL(request));
										errors.reject("claimCompleted",redirectURL);
										logger.debug("Claim #" + claim.getClaimNumber() + " is collected already");
									}
		
									logger.debug("NARDetails[" + i + "].isToBeProcessed() = " + claimDetails.isToBeProcessed());
									logger.debug("NARDetails[" + i + "].getAction() = " + claimDetails.getAction());
									logger.debug("NARDetails[" + i + "].getClaimNumber() = " + claim.getClaimNumber());
									logger.debug("NARDetails[" + i + "].getSecurityCode() = " + claim.getSecurityCode());
									logger.debug("claimDetails.getClaimStatusCode() = " + claimDetails.getClaimStatusCode());
									logger.debug("claimDetails.getCollectionTime() = " + claimDetails.getCollectionTime());
									logger.debug("claimDetails.getCompletionTime() = " + claimDetails.getCompletionTime());
									logger.debug("claimDetails.getConsumptionEndDate() = " + claimDetails.getConsumptionEndDate());
									logger.debug("claimDetails.getReceiptNo() = " + claimDetails.getReceiptNo());
									logger.debug("claimDetails.getRemarks() = " + claimDetails.getRemarks());
									logger.debug("claimDetails.getRemarks2() = " + claimDetails.getRemarks2());
								}
							} else {
								String invalidPackageCodeURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_INVALID_PACKAGE_URL);
								String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), invalidPackageCodeURL, null, urlHelper.isSSL(request));
								errors.reject("invalidPackageCode", redirectURL);
								logger.debug("ClaimNotAllow #" + agent.getAgentID() + "is not allow for packageCode " + packageCode);
							}
						} catch (CLSException e) {
							String invalidMemberNumberURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_INVALID_MEMBER_URL);
							String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), invalidMemberNumberURL, null, urlHelper.isSSL(request));
							errors.reject("invalidMemberNo", redirectURL);
							logger.debug("CLSException:  e.getErrorMessageCode() = " + e.getErrorMessageCode());
							logger.debug("CLSException:  e.getMessage() = " + e.getMessage());
						}
						
					logger.debug("referenceData[Page 0] - errors.toString() = " + errors.toString());
				} else {
					String invalidOfferURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_INVALID_OFFER_URL);
					String redirectURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), invalidOfferURL, null, urlHelper.isSSL(request));
					errors.reject("invalidRequest",redirectURL);
					logger.debug("Request is invalid");	
				}
				if(errors.getAllErrors().size() == 0) {
					logger.debug("start to call IF004");
					String language = "en";
					if(request.getParameter("lang") != null) {
						language = request.getParameter("lang");
					} else if (null != WebUtil.getCookieByName(request.getCookies(), "clientlanguage")){
						language = WebUtil.getCookieByName(request.getCookies(), "clientlanguage").getValue();
					}
					
					
					logger.debug(" language == " + language );
	                getPersonalOfferRequest().setLanguage(language.toUpperCase());
					List packageCodes = new ArrayList();
					packageCodes.add(packageCode);
					getPersonalOfferRequest().setSkus(packageCodes);
					PersonalOffersResponseBean personalOffersRes = this.getOfferProxy().execute(this.getPersonalOfferRequest());
					if (null != personalOffersRes) {
		            	logger.debug("PersonalOffersResponse: " + personalOffersRes.getResponseObject().get(0));
		            	PersonalOfferBean pob = (PersonalOfferBean)personalOffersRes.getResponseObject().get(0);
		            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            	DateFormat formatter = null;
		            	if("en".equalsIgnoreCase(language)) {
		            		formatter = DateFormat.getDateInstance(DateFormat.YEAR_FIELD, Locale.UK);
		            	} else if ("sc".equalsIgnoreCase(language)) {
		            		formatter = DateFormat.getDateInstance(DateFormat.YEAR_FIELD, Locale.SIMPLIFIED_CHINESE);
		            	} else {
		            		formatter = DateFormat.getDateInstance(DateFormat.YEAR_FIELD, Locale.TRADITIONAL_CHINESE);
		            	}
		            	
		            	Date startDate = null;
		                if ((pob.getRedemptionStartDate() != null) && (!"".endsWith(pob.getRedemptionStartDate()))) {
		                  startDate = sdf.parse(pob.getRedemptionStartDate());
		                  pob.setRedemptionStartDate(formatter.format(startDate));
		                }

		                Date endDate = null;
		                if ((pob.getRedemptionEndDate() != null) && (!"".endsWith(pob.getRedemptionEndDate()))) {
		                  endDate = sdf.parse(pob.getRedemptionEndDate());
		                  pob.setRedemptionEndDate(formatter.format(endDate));
		                }

		                if (pob.getDeliveryCollectionStartDate() != null && !"".endsWith(pob.getDeliveryCollectionStartDate())) {
		                  startDate = sdf.parse(pob.getDeliveryCollectionStartDate());
		                  pob.setDeliveryCollectionStartDate(formatter.format(startDate));

		                  this.logger.debug("PersonalOfferBean.getDeliveryCollectionNote() = [" + pob.getDeliveryCollectionNote() + "]");
		                  this.logger.debug("PsersonalOfferBean.getDeliveryCollectionPeriod() = [" + pob.getDeliveryCollectionPeriod() + "]");
		                }

		                if (pob.getDeliveryCollectionEndDate() != null && !"".equals(pob.getDeliveryCollectionEndDate())) {
		                  endDate = sdf.parse(pob.getDeliveryCollectionEndDate());
		                  pob.setDeliveryCollectionEndDate(formatter.format(endDate));
		                }
		            	
		            	request.getSession().setAttribute("personalOffer", pob);
		            } else {
		            	logger.debug("return null from IF004");
		            }
					logger.debug("end to call IF004");
				}
			}
			break;
		}
		logger.debug("Exiting InstantRedeemController referenceData()");

		return refData;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		logger.debug("Entering InstantRedeemController processFinish()");

		Map model = new HashMap();
		model.put(getCommandName(), command);
		
		UserSession session = (UserSession)WebUtils.getSessionAttribute(request, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent agent = session.getAgent();
		PaperlessRedemptionForm instantRedeemForm = (PaperlessRedemptionForm) command;
		
		try{
			for (Iterator it = instantRedeemForm.getNARDetails().iterator(); it.hasNext();) {
				NARClaimDetails claimDetails = (NARClaimDetails)it.next();
				PaperlessClaim claim = claimDetails.getPaperlessClaim();
				if (StringUtils.isEmpty(claimDetails.getReceiptNo())) {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
					String formatAgentId = agent.getAgentID().length() > 13 ? agent.getAgentID().substring(0, 13) : agent.getAgentID();
					claimDetails.setReceiptNo(formatAgentId+sdf.format(calendar.getTime()));
				}
				logger.debug("claimDetails receiptNo:"+claimDetails.getReceiptNo());
				synchronized (this) {
					if (claimDetails.isToBeProcessed()){
						logger.debug("start to update collection");
						clsFacade.updateCollection(agent, ActionCode.NAR_CONFIRM, instantRedeemForm.getMemberId(), new Integer(claimDetails.getPaperlessClaim().getClaimNumber()), 
								claimDetails.getPaperlessClaim().getSecurityCode(), "", "", "", "");
						clsFacade.updateCollection(agent,ActionCode.NAR_COMPLETE,instantRedeemForm.getMemberId(),new Integer(claim.getClaimNumber()),claim.getSecurityCode(),claimDetails.getReceiptNo(),claimDetails.getRemarks(),claimDetails.getRemarks2(),"");
					}
				}
			}
		} catch (CLSException e) {
			errors.reject(e.getErrorMessageCode(),e.getMessage());
			logger.debug("CLSException:  e.getErrorMessageCode() = " + e.getErrorMessageCode());
			logger.debug("CLSException:  e.getMessage() = " + e.getMessage());
			model.putAll(errors.getModel());
			return new ModelAndView (ViewConstants.INSTANT_REDEEM_ERROR, model);
		}

		model.putAll(errors.getModel());
		logger.debug("Exiting InstantRedeemController processFinish()");

		return new ModelAndView (ViewConstants.INSTANT_REDEEM_ACKNOWLEDGEMENT, model);
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processCancel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		logger.debug("Entering InstantRedeemController processCancel()");

		Map model = new HashMap();
		model.put(getCommandName(), new PaperlessRedemptionForm(true));

		
		String qrCodeReaderURL = siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_QR_CODE_READER_URL);

		String secureQrCodeReaderURL = urlHelper.getSiteURL(request.getServerName(), request.getServerPort(), request.getContextPath(), qrCodeReaderURL, null, urlHelper.isSSL(request));
		logger.debug("Forwarding request to " + secureQrCodeReaderURL);
		response.sendRedirect(secureQrCodeReaderURL);
		logger.debug("Exiting InstantRedeemController processCancel()");
		return null;
	}
	
	public Hashtable parseHttpRequest(HttpServletRequest pReq) {
		Hashtable resultHash = new Hashtable();
		try {
			String encryptString = pReq.getParameter("mb");
			logger.debug("encryptString ----->>" + encryptString);
			String decryptString = null;
			if (encryptString != null) {
				decryptString = decodeStr(encryptString);
			}
			logger.debug("decryptString ==" + decryptString);
			StringTokenizer st = new StringTokenizer(
					decryptString != null ? decryptString : "", "&");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				String[] nameAndValue = token.split("=");
				if (null != nameAndValue) {
					String name = "";
					String value = "";
					if (null != nameAndValue[0]) {
						name = nameAndValue[0].trim();
					}
					if (nameAndValue.length > 1) {
						value = nameAndValue[1].trim();
					}

					resultHash.put(name, value);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			logger.debug("Exception in parseHttpRequest ex  --message: "
					+ ex.getMessage() + " \r\n" + ex.getStackTrace());
		}
		return resultHash;
	}
	
	private String decodeStr(String pwd) {
		byte[] debytes = Base64.decodeBase64(new String(pwd).getBytes());
		return new String(debytes);

		}


}
