/*
 * Created on Jun 5, 2009
 *
 */
package com.asiamiles.partnerportal.cls;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.asiamiles.partnerportal.RecoverableSystemException;
import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemConfigException;
import com.asiamiles.partnerportal.SystemException;
import com.asiamiles.partnerportal.cls.messages.AgentLoginServletResponse;
import com.asiamiles.partnerportal.cls.messages.AgentRetrievalServletResponse;
import com.asiamiles.partnerportal.cls.messages.CollectionRetrievalServletResponse;
import com.asiamiles.partnerportal.cls.messages.CollectionUpdateServletResponse;
import com.asiamiles.partnerportal.cls.messages.InvoiceRetrievalServletResponse;
import com.asiamiles.partnerportal.cls.messages.BillingServletResponse;
import com.asiamiles.partnerportal.cls.messages.MembershipProfileRetrievalServletResponse;
import com.asiamiles.partnerportal.cls.messages.RetrievalServletResponse;
import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.Unbilled;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.HTTPConnectionHelper;
import com.cathaypacific.utility.Logger;
import com.asiamiles.partnerportal.cls.messages.AgentUpdateServletResponse;
import com.asiamiles.partnerportal.cls.messages.ChangePasswordServletResponse;
import com.asiamiles.partnerportal.cls.messages.ForgotPasswordServletResponse;


/**
 * Facade class for communicating with the CLS API.
 * @author CPPBENL
 *
 */
public class CLSFacade {
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String TIMESTAMP_FORMAT = "yyyyMMdd HH:mm:ss";
	
	private static Logger logger = AppLogger.getAppLogger();
	
	private HTTPConnectionHelper httpConnectionHelper;
	private SiteProperties clsProperties;
	
	/**
	 * @param httpConnectionHelper The httpConnectionHelper to set.
	 */
	public void setHttpConnectionHelper(HTTPConnectionHelper httpConnectionHelper) {
		this.httpConnectionHelper = httpConnectionHelper;
	}
	
	/**
	 * @param clsInterfaceProperties The clsInterfaceProperties to set.
	 */
	public void setClsProperties(SiteProperties clsProperties) {
		this.clsProperties = clsProperties;
	}
	protected Map getCredentials(String appId) {
		String[] propertyComponents = new String[] {CLSInterfaceProperties.HTTP_PARAM_APP_ID, appId};
		Map result = new HashMap();
		result.put(CLSInterfaceProperties.HTTP_PARAM_APP_ID, clsProperties.getPropertyByPlatform(CLSInterfaceProperties.constructPropertyName(propertyComponents)));
		
		propertyComponents = new String[] {CLSInterfaceProperties.HTTP_PARAM_PASSWORD, appId};
		result.put(CLSInterfaceProperties.HTTP_PARAM_PASSWORD, clsProperties.getPropertyByPlatform(CLSInterfaceProperties.constructPropertyName(propertyComponents)));
		
		return result;
	}
	
	protected URL getURLForApp(String appId) throws CLSException{
		String keyProtocol = CLSInterfaceProperties.constructPropertyName(new String[]{CLSInterfaceProperties.COMMON_PROTOCOL, appId});
		String keyHost = CLSInterfaceProperties.constructPropertyName(new String[]{CLSInterfaceProperties.COMMON_HOST, appId});
		String keyPort = CLSInterfaceProperties.constructPropertyName(new String[]{CLSInterfaceProperties.COMMON_PORT, appId});
		String keyContextPath = CLSInterfaceProperties.constructPropertyName(new String[]{CLSInterfaceProperties.COMMON_CONTEXT_PATH, appId});
		String keyServlet = CLSInterfaceProperties.constructPropertyName(new String[]{CLSInterfaceProperties.COMMON_SERVLET, appId});
		
		String protocol = clsProperties.getPropertyByPlatform(keyProtocol);
		String host = clsProperties.getPropertyByPlatform(keyHost);
		int port = Integer.parseInt(clsProperties.getPropertyByPlatform(keyPort));
		String contextPath = clsProperties.getPropertyByPlatform(keyContextPath);
		String servlet = clsProperties.getPropertyByPlatform(keyServlet);
		try {
			URL url = new URL(protocol,host, port, contextPath + "/" + servlet);
			return url;
		} catch (MalformedURLException e) {
			throw new SystemConfigException(SystemConfigException.CLS_MALFORMED_URL, e);
		}
	}
	
	/**
	 * http://host/paperless/AgentLoginServlet
	 * @param agentId
	 * @param password
	 * @return
	 * @throws CLSException
	 */
	public Agent loginAgent(String agentId, String password) throws CLSException {
		
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_AGENT_LOGIN));
		params.put("agentID", agentId);
		params.put("password", password);
		try {
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_AGENT_LOGIN), params, timeout);
			AgentLoginServletResponse response = AgentLoginServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_AGENT_LOGIN, response.getErrorCode(), response.getErrorMessage());
			}
			logger.debug("Agent's Partner Code:"+response.getPartnerCode());
			Agent agent = new Agent();
			BeanUtils.copyProperties(response, agent);
			logger.debug("AgentID: " + agent.getAgentID());
			
			return agent;
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_LOGIN, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_LOGIN, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e); 
		}

	}
	
	/** Paperless NAR Collection Update API
	 	http://host/paperless/PaperlessCollectionServlet
	 * @param agent
	 * @param actionCode
	 * @param memberID
	 * @param claimNo
	 * @param securityCode
	 * @param receiptNo
	 * @param remark
	 * @param remark2
	 * @param voidReason
	 * @return
	 * @throws CLSException
	 */
	public void updateCollection (Agent agent,ActionCode actionCode, String memberID, Integer claimNo, 
			String securityCode, String receiptNo, String remark, String remark2, String voidReason) throws CLSException {
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION));		
		params.put("agentID", agent.getAgentID());
		params.put("actionCode", actionCode.toString());
		params.put("memberID", memberID);
		params.put("claimNo", claimNo.toString());			
		params.put("securityCode", securityCode);
		params.put("receiptNo", receiptNo);
		params.put("remarks", remark);
		params.put("remarks2", remark2);
		params.put("voidReason", voidReason);
		
		try{
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION), params, timeout);
			CollectionUpdateServletResponse response = CollectionUpdateServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION, response.getErrorCode(), response.getErrorMessage());
			}

		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}	
	}	
	
	/** Paperless NAR Collection List API
	 	http://host/paperless/PaperlessCollectionRetrievalServlet
	 * @param agent
	 * @param actionCode
	 * @param establishmentCode
	 * @param timeZone
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CLSException
	 */
	public List retrieveCollectionList (Agent agent,ActionCode actionCode, String establishmentCode, 
			Integer timeZone, Date startDate, Date endDate) throws CLSException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL));		
		params.put("agentID", agent.getAgentID());
		params.put("actionCode", actionCode.toString());
		params.put("establishmentCode", establishmentCode);			
		params.put("timeZone", timeZone.toString());
		if (startDate != null) {
			params.put("startDate", dateFormat.format(startDate));
		}
		if (endDate != null) {
			params.put("endDate", dateFormat.format(endDate));
		}
		
		try{
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL), params, timeout);
			CollectionRetrievalServletResponse response = CollectionRetrievalServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, response.getErrorCode(), response.getErrorMessage());
			}
			List result = new ArrayList();
			
			
			String[] ignoreProperties = new String[]{"claimNo", "collectionTime", "completionTime", "billedDate"};
			if (response.getNARDetails() == null) {
				return result;
			}
			for (Iterator it = response.getNARDetails().iterator(); it.hasNext();) {
				CollectionRetrievalServletResponse.NARDetails nar = (CollectionRetrievalServletResponse.NARDetails)it.next();
				NARClaimDetails narDetails = new NARClaimDetails();
				BeanUtils.copyProperties(nar, narDetails, ignoreProperties);
				
				//Manually populating the ignored properties (due to different data types)
				narDetails.setClaimNo(Integer.valueOf(nar.getClaimNo()));
				//narDetails.setMemberID(Long.valueOf(nar.getMemberID()));
				if (StringUtils.isNotEmpty(nar.getCollectionTime())) {
					narDetails.setCollectionTime(timestampFormat.parse(nar.getCollectionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getCompletionTime())) {
					narDetails.setCompletionTime(timestampFormat.parse(nar.getCompletionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getBilledDate())) {
					narDetails.setBilledDate(dateFormat.parse(nar.getBilledDate()));
				}
				
//				narDetails.setPaperlessClaim((PaperlessClaim)claimsMap.get(narDetails.getClaimNo()));
				narDetails.setAction(NARClaimDetails.ACTION_COMPLETE);
				
				result.add(narDetails);
			}
			return result;
		
//			return response.getNARDetails();
		
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}	
	}

	//Steven ADD for AML34188 20140805 START
	/** Paperless NAR Collection List API
	 * http://host/paperless/PaperlessCollectionRetrievalServlet
	 * @param agent
	 * @param actionCode
	 * @param establishmentCode
	 * @param timeZone
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CLSException
	 */
	public List retrieveCollectionList (Agent agent,ActionCode actionCode, String establishmentCode, 
			Integer timeZone, Date startDate, Date endDate,String pageNextValue,String pageNextPkgValue,String flag) throws CLSException {
		logger.info("request data......");
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL));		
		params.put("agentID", agent.getAgentID());
		params.put("actionCode", actionCode.toString());
		params.put("establishmentCode", establishmentCode);	
		//Steven ADD for AML34188 20140806 START
		params.put("pageNextValue", pageNextValue);
		params.put("pageNextPkgValue", pageNextPkgValue);
		params.put("flag", flag);
		//Steven ADD for AML34188 20140806 END
		params.put("timeZone", timeZone.toString());
		if (startDate != null) {
			params.put("startDate", dateFormat.format(startDate));
		}
		if (endDate != null) {
			params.put("endDate", dateFormat.format(endDate));
		}
		
		try{
			logger.info("request start");
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL), params, timeout);
			CollectionRetrievalServletResponse response = CollectionRetrievalServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, response.getErrorCode(), response.getErrorMessage());
			}
			List result = new ArrayList();
			
			Map map=new HashMap();
			map.put("nextPageCode", response.getPageNextPkgValue());
			map.put("nextClaimNum", response.getPageNextValue());
			map.put("prePageCode", response.getPagePrePkgValue());
			map.put("preClaimNum", response.getPagePreValue());
			map.put("totalNum", response.getTotalNum());
			map.put("totalPage", response.getTotalPage());
			result.add(map);
			logger.info("totalNum: "+response.getTotalNum());
			logger.info("totalPage: "+response.getTotalPage());
			
			String[] ignoreProperties = new String[]{"claimNo", "collectionTime", "completionTime", "billedDate"};
			if (response.getNARDetails() == null) {
				return result;
			}
			for (Iterator it = response.getNARDetails().iterator(); it.hasNext();) {
				CollectionRetrievalServletResponse.NARDetails nar = (CollectionRetrievalServletResponse.NARDetails)it.next();
				NARClaimDetails narDetails = new NARClaimDetails();
				BeanUtils.copyProperties(nar, narDetails, ignoreProperties);
				
				//Manually populating the ignored properties (due to different data types)
				narDetails.setClaimNo(Integer.valueOf(nar.getClaimNo()));
				//narDetails.setMemberID(Long.valueOf(nar.getMemberID()));
				if (StringUtils.isNotEmpty(nar.getCollectionTime())) {
					narDetails.setCollectionTime(timestampFormat.parse(nar.getCollectionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getCompletionTime())) {
					narDetails.setCompletionTime(timestampFormat.parse(nar.getCompletionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getBilledDate())) {
					narDetails.setBilledDate(dateFormat.parse(nar.getBilledDate()));
				}
				
//				narDetails.setPaperlessClaim((PaperlessClaim)claimsMap.get(narDetails.getClaimNo()));
				narDetails.setAction(NARClaimDetails.ACTION_COMPLETE);
				
				result.add(narDetails);
			}
			return result;
		
//			return response.getNARDetails();
		
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_COLLECTION_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}	
	}

	//Steven ADD for AML34188 20140805 END

	/**	
	 * Paperless Partner Invoice API
	 * HTTP Post Request Interface for Unbilled Range Retrieval
	 * http://host/paperless/PaperlessInvoiceRetrievalervlet	
	 * @param agent
	 * @return 
	 * @throws CLSException
	 */
	public Unbilled retrieveInvoice(Agent agent) throws CLSException {
		SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL));
		params.put("agentID", agent.getAgentID());

		try {
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL), params, timeout);
			InvoiceRetrievalServletResponse response = InvoiceRetrievalServletResponse.decodeFromXML(responseXml);

			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL, response.getErrorCode(), response.getErrorMessage());
			}

			Unbilled unbilledNAR = new Unbilled();
			BeanUtils.copyProperties(response, unbilledNAR);

			List establishments = new ArrayList();
			String[] estIgnoreProperties = new String[] { "oldestCollectionTimestamp", "latestCollectionTimestamp", "lastBillingDate" };
			for (Iterator it = response.getEstablishment().iterator(); it.hasNext();) {
				InvoiceRetrievalServletResponse.Establishment establishment = (InvoiceRetrievalServletResponse.Establishment) it.next();
				Unbilled.Establishment est = unbilledNAR.new Establishment();

				BeanUtils.copyProperties(establishment, est, estIgnoreProperties);

				if (StringUtils.isNotEmpty(establishment.getOldestCollectionTimestamp())) {
					est.setOldestCollectionTimestamp(timestampFormat.parse(establishment.getOldestCollectionTimestamp()));
				}
				if (StringUtils.isNotEmpty(establishment.getLatestCollectionTimestamp())) {
					est.setLatestCollectionTimestamp(timestampFormat.parse(establishment.getLatestCollectionTimestamp()));
				}
				if (StringUtils.isNotEmpty(establishment.getLastBillingDate())) {
					est.setLastBillingDate(timestampFormat.parse(establishment.getLastBillingDate()));
				}
				establishments.add(est);
			}

			unbilledNAR.setEstablishments(establishments);

			return unbilledNAR;

		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}
	}	


	/** Paperless Partner Invoice API
 		HTTP Post Request Interface for Paperless NAR Billing
 		http://host/paperless/PaperlessPartnerInvoiceServlet 	
	 * @param agent
	 * @param establishmentCode
	 * @param timeZone
	 * @param startDate
	 * @param endDate
	 * @param invoiceNo
	 * @param excludedClaims
	 * @return a List of Billed claims
	 * @throws CLSException
	 */
	public List billClaims (Agent agent, String establishmentCode, Integer timeZone, 
			Date startDate, Date endDate, String invoiceNo, List excludedClaims) throws CLSException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_PARTNER_INVOICE));
		params.put("agentID", agent.getAgentID());
		params.put("establishmentCode", establishmentCode);
		params.put("timeZone", timeZone.toString());
		params.put("startDate", dateFormat.format(startDate));
		params.put("endDate", dateFormat.format(endDate));
		params.put("invoiceNo", invoiceNo);
		
		if (excludedClaims != null) {
			for (int i = 0; i < excludedClaims.size(); i++) {
				String p = "claimNo" + (i + 1);
				params.put(p, excludedClaims.get(i).toString());
			}
		}
		try{
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_PARTNER_INVOICE), params, timeout);
			BillingServletResponse response = BillingServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_PARTNER_INVOICE, response.getErrorCode(), response.getErrorMessage());
			}
			List result = new ArrayList();
			String[] ignoreProperties = new String[]{"claimNo", "collectionTime", "completionTime", "billedDate"};
			if (response.getNARDetails() == null) {
				return result;
			}
			for (Iterator it = response.getNARDetails().iterator(); it.hasNext();) {
				BillingServletResponse.NARDetails nar = (BillingServletResponse.NARDetails)it.next();
				NARClaimDetails narDetails = new NARClaimDetails();
				BeanUtils.copyProperties(nar, narDetails, ignoreProperties);
				
				//Manually populating the ignored properties (due to different data types)
				narDetails.setClaimNo(Integer.valueOf(nar.getClaimNo()));
				//narDetails.setMemberID(Long.valueOf(nar.getMemberID()));
				if (StringUtils.isNotEmpty(nar.getCollectionTime())) {
					narDetails.setCollectionTime(timestampFormat.parse(nar.getCollectionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getCompletionTime())) {
					narDetails.setCompletionTime(timestampFormat.parse(nar.getCompletionTime()));
				}
				if (StringUtils.isNotEmpty(nar.getBilledDate())) {
					narDetails.setBilledDate(dateFormat.parse(nar.getBilledDate()));
				}
				
//				narDetails.setPaperlessClaim((PaperlessClaim)claimsMap.get(narDetails.getClaimNo()));
				narDetails.setAction(NARClaimDetails.ACTION_COMPLETE);
				
				result.add(narDetails);
			}
			return result;			
		
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_PARTNER_INVOICE, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_PARTNER_INVOICE, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_INVOICE_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}	
	}	
	
	/**
	 * http://host/paperless/PaperlessRetrievalServlet
	 * @param agent
	 * @param memberID
	 * @param embossedName
	 * @param claims <code>List&lt;PaperlessClaim&gt;</code>
	 * @return
	 * @throws CLSException
	 */
	public HashMap retrieveClaims(Agent agent, String memberID, String embossedName, List claims) throws CLSException {
		
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		Map claimsMap = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL));
		try {		
			params.put("agentID", agent.getAgentID());
			params.put("memberID", memberID);
			params.put("embossedName", embossedName);
			for (int i = 0; i < claims.size(); i++) {
				PaperlessClaim claim = (PaperlessClaim)claims.get(i);
				logger.debug("Claim " + i + "[" + claim.getClaimNumber() + "," + claim.getSecurityCode() + "]");
				if (StringUtils.isNotBlank(claim.getClaimNumber())) {
					params.put("claimNo" + (i+1), claim.getClaimNumber().toString());
					params.put("securityCode" + (i+1), claim.getSecurityCode().toString());
					claimsMap.put(new Integer(claim.getClaimNumber()), claim);
				}
			}
		
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL), params, timeout);
			RetrievalServletResponse response = RetrievalServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL, response.getErrorCode(), response.getErrorMessage());
			}
			
			List result = new ArrayList();
			SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			String[] ignoreProperties = new String[]{"claimNo", "consumptionStartDate", "consumptionEndDate", "collectionTime", "completionTime"};
			
			//CPPNXU start
			HashMap map = new HashMap();
			String memberName = response.getMemberName();
			map.put("MemberName", memberName);
			//CPPNXU end
			if (response.getNARDetails() != null) {
				for (Iterator it = response.getNARDetails().iterator(); it.hasNext();) {
					RetrievalServletResponse.NARDetails nar = (RetrievalServletResponse.NARDetails)it.next();
					NARClaimDetails narDetails = new NARClaimDetails();
					BeanUtils.copyProperties(nar, narDetails, ignoreProperties);
					
					//Manually populating the ignored properties (due to different data types)
					narDetails.setClaimNo(Integer.valueOf(nar.getClaimNo()));
					
					narDetails.setApprovalCode(nar.getSecurityCode());
					if (StringUtils.isNotEmpty(nar.getConsumptionStartDate())){
						narDetails.setConsumptionStartDate(dateFormat.parse(nar.getConsumptionStartDate()));
					}
					if (StringUtils.isNotEmpty(nar.getConsumptionEndDate())) {
						narDetails.setConsumptionEndDate(dateFormat.parse(nar.getConsumptionEndDate()));
					}
					if (StringUtils.isNotEmpty(nar.getCollectionTime())) {
						narDetails.setCollectionTime(timestampFormat.parse(nar.getCollectionTime()));
					}
					if (StringUtils.isNotEmpty(nar.getCompletionTime())) {
						narDetails.setCompletionTime(timestampFormat.parse(nar.getCompletionTime()));
					}
					
					narDetails.setPaperlessClaim((PaperlessClaim)claimsMap.get(narDetails.getClaimNo()));
					narDetails.setAction(NARClaimDetails.ACTION_COMPLETE);
					narDetails.setToBeProcessed(true);
					result.add(narDetails);
				}
			}
			map.put("NARDetails", result);
			return map;
			
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new CLSException(CLSInterfaceProperties.APP_PAPERLESS_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}
	}
	
	/**
	 * http://host/paperless/AgentRetrievalServlet
	 * @param adminID
	 * @param partnerCode
	 * @return <code>List&lt;Agent&gt;</code> of Agents
	 * @throws CLSException
	 */
	public List retrieveAgents(String adminID, String partnerCode, String partnerGroup) throws CLSException {
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_AGENT_RETRIEVAL));
		params.put("adminID", adminID);
		if (StringUtils.isNotBlank(partnerCode)) {
			params.put("partnerCode", partnerCode);
		}		
		if (StringUtils.isNotBlank(partnerCode)) {
			params.put("partnerGroup", partnerGroup);
		}
		try {
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_AGENT_RETRIEVAL), params, timeout);
			AgentRetrievalServletResponse response = AgentRetrievalServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_AGENT_RETRIEVAL, response.getErrorCode(), response.getErrorMessage());
			}
			List result = new ArrayList();
			if (response.getAgents() == null) {
				return result;
			}
			
			for (Iterator it = response.getAgents().iterator(); it.hasNext();) {
				AgentRetrievalServletResponse.AgentDetails agentDetails = (AgentRetrievalServletResponse.AgentDetails)it.next();
				Agent agent = new Agent();
				BeanUtils.copyProperties(agentDetails, agent);
				agent.setAdministratorIndicator(agentDetails.getAgentType());
				//AML38938 Start
				agent.setPartnerGroup(agentDetails.getPartnerGroup());
				//AML38938 End
				agent.setEmailAddress(agentDetails.getEmail());
				result.add(agent);
			}
			
			return result;
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_RETRIEVAL, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_RETRIEVAL, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}		
	}
	
	/**
	 * http://host/paperless/AgentUpdateServlet
	 * @param agent
	 * @param actionCode
	 * @param adminID
	 * @param partnerCode
	 * @throws CLSException
	 */
	public void updateAgent(Agent agent, ActionCode actionCode, String adminID, String partnerCode) throws CLSException {
		
		if(actionCode != ActionCode.AGENT_CREATE
				&& actionCode != ActionCode.AGENT_DELETE
				&& actionCode != ActionCode.AGENT_RESET_PASSWORD
				&& actionCode != ActionCode.AGENT_UPDATE) {
			throw new IllegalArgumentException("Invalid action code");
		}
			
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_AGENT_UPDATE));
		params.put("adminID", adminID);
		params.put("agentID", agent.getAgentID());
		params.put("actionCode", actionCode.toString());
		params.put("remarks", StringUtils.defaultString(agent.getRemarks()));
		
		if (StringUtils.isNotBlank(partnerCode)) {
			params.put("partnerCode", partnerCode);
		}
		
		if (actionCode == ActionCode.AGENT_CREATE || actionCode == ActionCode.AGENT_UPDATE || actionCode == ActionCode.AGENT_RESET_PASSWORD) {
			if(actionCode == ActionCode.AGENT_CREATE || actionCode == ActionCode.AGENT_UPDATE){
				params.put("email", agent.getEmailAddress());
				params.put("familyName", agent.getFamilyName());
				params.put("firstName", agent.getFirstName());
				params.put("agentType", agent.getAdministratorIndicator());
			}
			//AML38938 Start			
			params.put("partnerGroup", agent.getPartnerGroup());
			//AML38938 End
		}
		
		try {
			
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_AGENT_UPDATE), params, timeout);
			AgentUpdateServletResponse response = AgentUpdateServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_AGENT_UPDATE, response.getErrorCode(), response.getErrorMessage());
			}
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_UPDATE, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_AGENT_UPDATE, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}	
		
	}
	
	/**
	 * http://host/paperless/ChangePwdServlet
	 * @param agent
	 * @param oldPassword
	 * @param newPassword
	 * @throws CLSException
	 */
	public void changeAgentPassword(Agent agent, String oldPassword, String newPassword) throws CLSException {
		if (agent == null) {
			throw new IllegalArgumentException("No Agent specified");
		} else if (StringUtils.isBlank(oldPassword)) {
			throw new IllegalArgumentException("Old Password is blank");
		} else if (StringUtils.isBlank(newPassword)) {
			throw new IllegalArgumentException("New Password is blank");
		}
		
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_CHANGE_PWD));
		try {
			params.put("agentID", agent.getAgentID());
			params.put("password", newPassword);
			params.put("oldPassword", oldPassword);
			//cppjox add for AML38938 20160615 start 
			params.put("partnerGroup", agent.getPartnerGroup());
			//cppjox add for AML38938 20160615 end 

			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_CHANGE_PWD), params, timeout);
			ChangePasswordServletResponse response = ChangePasswordServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_CHANGE_PWD, response.getErrorCode(), response.getErrorMessage());
			}
		
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_CHANGE_PWD, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_CHANGE_PWD, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}
	}
	
	/**
	 * http://host/paperless/ForgotPwdServlet
	 * @param agentID
	 * @throws CLSException
	 */
	public void forgotPassword(String agentID) throws CLSException {
		if (StringUtils.isBlank(agentID)) {
			throw new IllegalArgumentException("Agent ID is blank");
		}
		int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
		
		Map params = new HashMap();
		params.putAll(getCredentials(CLSInterfaceProperties.APP_FORGOT_PWD));
		params.put("agentID", agentID);
		try {
			String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_FORGOT_PWD), params, timeout);
			ForgotPasswordServletResponse response = ForgotPasswordServletResponse.decodeFromXML(responseXml);
			if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
				throw new CLSException(CLSInterfaceProperties.APP_FORGOT_PWD, response.getErrorCode(), response.getErrorMessage());
			}
		
		} catch (RecoverableSystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_FORGOT_PWD, CLSException.UNAVAILABLE_ERROR_CODE,e);
		} catch (SystemException e) {
			throw new CLSException(CLSInterfaceProperties.APP_FORGOT_PWD, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
		}

	}
	  /**
     * http://host/paperless/MembershipProfileRetrievalServlet
     * @param memberID
     * @throws CLSException
     */
    public MembershipProfileRetrievalServletResponse retrieveMembership(String memberID) throws CLSException{
        if(StringUtils.isBlank(memberID)){
            throw new IllegalArgumentException("member ID is black");
        }
        int timeout = Integer.parseInt(clsProperties.getPropertyByPlatform(CLSInterfaceProperties.COMMON_TIMEOUT));
       // logger.info("retrieveMembership");
        
        Map params = new HashMap();
        params.putAll(getCredentials(CLSInterfaceProperties.APP_MEMBERSHIP_VERIFY));
        params.put("memberID", memberID);
        //logger.info("params:" + params.toString());
        try{
            String responseXml = httpConnectionHelper.postToSite(getURLForApp(CLSInterfaceProperties.APP_MEMBERSHIP_VERIFY),params, timeout);
            MembershipProfileRetrievalServletResponse response = MembershipProfileRetrievalServletResponse.decodeFromXML(responseXml);
         ///   logger.info("MembershipProfileRetrievalServletResponse");
            if (!CLSResponse.STATUS_SUCCESS.equals(response.getStatusCode())) {
                throw new CLSException(CLSInterfaceProperties.APP_MEMBERSHIP_VERIFY, response.getErrorCode(), response.getErrorMessage());
            }
            return response;
        } catch (RecoverableSystemException e) {
          //  logger.info("RecoverableSystemException");
            throw new CLSException(CLSInterfaceProperties.APP_MEMBERSHIP_VERIFY, CLSException.UNAVAILABLE_ERROR_CODE,e);
        }catch(SystemException e){
          //  logger.info("SystemException");
            throw new CLSException(CLSInterfaceProperties.APP_MEMBERSHIP_VERIFY, CLSException.UNKNOWN_ERROR_CODE, e.getMessage(), e);
        }
       
    }
}
 