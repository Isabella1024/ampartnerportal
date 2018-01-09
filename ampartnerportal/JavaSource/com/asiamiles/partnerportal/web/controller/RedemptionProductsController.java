/**
 * 
 */
package com.asiamiles.partnerportal.web.controller;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.dao.InstantRedeemGroupDao;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.barQrCodeUtil.code.CodeGenerator;
import com.cathaypacific.iRedeem.bean.PersonalOfferBean;
import com.cathaypacific.iRedeem.client.proxy.PersonlOfferProxyBean;
import com.cathaypacific.iRedeem.client.request.PersonalOfferRequestBean;
import com.cathaypacific.iRedeem.client.response.PersonalOffersResponseBean;
import com.cathaypacific.utility.Logger;

/**
 * @author le.gao
 *
 */
public class RedemptionProductsController extends MultiActionController {
	
	private static final String QRCODE_PATH = "/images/instantRedeem/qrCode/";
	
	private Logger logger = AppLogger.getAppLogger();
	private SiteProperties siteProperties;
	
	private InstantRedeemGroupDao instantRedeemGroupDao;
	
	private PersonalOfferRequestBean personalOfferRequest;
	
	private PersonlOfferProxyBean offerProxy;
	
	
	
	
	public ModelAndView listPackage(final HttpServletRequest pRequest, final HttpServletResponse response){
		UserSession session = (UserSession)WebUtils.getSessionAttribute(pRequest, UserSession.SESSION_ATTRIBUTE_NAME);
		Agent loggedInAgent = session.getAgent();
		Map instantRedeemMap = new HashMap();
		List packageCodes = instantRedeemGroupDao.listInstantPackageCode(loggedInAgent.getPartnerCode());
		List packageCodeList = new ArrayList();
		for (int i=0;i<packageCodes.size();i++) {
			Map packageCodeMap = (Map)packageCodes.get(i);
			packageCodeList.add(packageCodeMap.get("PACKAGE_CODE"));
		}
		logger.debug("start to call IF004");
//		String prfLanguageStr = pRequest.getHeader("Accept-Language");
//	    String languages[] = prfLanguageStr.split(",");
//	    String language = languages[0];
//	    if (language.indexOf("zh") > -1) {
//	        if (language.equalsIgnoreCase("zh-CN")) {
//	            language = "sc";
//	        } else {
//	            language = "zh";
//	        }
//	    } else {
//	        language = "en";
//	    }
		String language = pRequest.getParameter("lang") != null ? pRequest.getParameter("lang") : "en";
        getPersonalOfferRequest().setLanguage(language.toUpperCase());
		getPersonalOfferRequest().setSkus(packageCodeList);
		PersonalOffersResponseBean personalOffersRes = this.getOfferProxy().execute(this.getPersonalOfferRequest());
		if (null != personalOffersRes) {
			Iterator it = personalOffersRes.getResponseObject().iterator();
			List packageList = new ArrayList();
            while (it.hasNext()) {
                Object ob = it.next();
                PersonalOfferBean pob = (PersonalOfferBean) ob;               
                PersonalOfferBean pb = new PersonalOfferBean();
                pb.setItemSku(pob.getItemSku());
                pb.setItemName(pob.getItemName());
                if (pob.getItemPoints().indexOf(".") > -1) {
                	pb.setItemPoints(pob.getItemPoints().substring(0, pob.getItemPoints().indexOf(".")));
                } else {
                	 pb.setItemPoints(pob.getItemPoints());
                }
                packageList.add(pb);
            }
        	instantRedeemMap.put("packageList", packageList);
        } else {
        	logger.debug("return null from IF004");
        }
		logger.debug("end to call IF004");
		
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_REDEMPTION_PRODUCTS, instantRedeemMap);
	}
	
	public ModelAndView generateQrCode(final HttpServletRequest pRequest, final HttpServletResponse response){
		String packageCode = pRequest.getParameter("packageCode");
		logger.debug("packageCode:" + packageCode);
		String serverPath = pRequest.getSession().getServletContext().getRealPath("/");
		String filePath = serverPath + QRCODE_PATH;
		String lang = pRequest.getParameter("lang");
		logger.debug("lang:" + lang);
		StringBuffer qrCodeContentUrl = new StringBuffer(MessageFormat.format(siteProperties.getProperty(SiteProperties.INSTANT_REDEEM_GENERATE_URL), new Object[]{lang}));
		qrCodeContentUrl.append("?packageCode=" + packageCode);
		logger.debug("qrCodeContentUrl:" + qrCodeContentUrl);
		String fileName = packageCode + "_" +lang;
		File file = new File(filePath+fileName+".jpeg");
		if (!file.exists()) {
			CodeGenerator.generateCode("IRQRCode", filePath, fileName, qrCodeContentUrl.toString(), "jpeg");
		}
		Map instantRedeemMap = new HashMap();
		instantRedeemMap.put("qrCodeImagePath", QRCODE_PATH + fileName + ".jpeg");
		return new ModelAndView(ViewConstants.INSTANT_REDEEM_QRCODE_IMAGE, instantRedeemMap);
	}
	
	public InstantRedeemGroupDao getInstantRedeemGroupDao() {
		return instantRedeemGroupDao;
	}

	public void setInstantRedeemGroupDao(InstantRedeemGroupDao instantRedeemGroupDao) {
		this.instantRedeemGroupDao = instantRedeemGroupDao;
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

	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}
	
	
	
	
}
