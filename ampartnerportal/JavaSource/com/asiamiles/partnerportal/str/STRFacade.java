/*
 * Created on Dec 4, 2007
 *
 */
package com.asiamiles.partnerportal.str;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemConfigException;
import com.asiamiles.partnerportal.SystemNotAccessibleException;
import com.cathaypacific.str.MessageMapHandler;
import com.cathaypacific.str.utility.StrException;

/**
 * @author CPPHOWC
 *
 */
public class STRFacade {

	public static final String APP_CODE_AMPARTNERPORTAL = "AMPARTNERPORTAL"; 
	
	private final String[] ALL_APP_CODES = {
			APP_CODE_AMPARTNERPORTAL
	};  
	
	private SiteProperties siteProperties;
	
	private MessageMapHandler messageMapHandler;
	
	public STRFacade() {}

	/**
	 * @param messageMapHandler The messageMapHandler to set.
	 */
	public void setMessageMapHandler(MessageMapHandler messageMapHandler) {
		this.messageMapHandler = messageMapHandler;
		if (this.messageMapHandler != null) {
			reloadSTR();
		}
	}
	
	/**
	 * @param siteProperties The siteProperties to set.
	 */
	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}
	// ------------------
	// load config method
	// ------------------
	
	public void reloadSTR() {
		try {
			String[] appCodeList = {};
			String defaultLanguage = siteProperties.getProperty(SiteProperties.DEFAULT_LANGUAGE);
			List supportedLanguages = siteProperties.getPropertyAsList(SiteProperties.SUPPORTED_LANGUAGES);
			
			if (supportedLanguages != null && supportedLanguages.size() > 0) {
				for (int i = 0; i < supportedLanguages.size(); i++) {
					String nextLangCode = (String) supportedLanguages.get(i);
					
					// remove cached data first and add again
					// --------------------------------------
					
					for (int j = 0; j < ALL_APP_CODES.length; j++) {
						messageMapHandler.remove(ALL_APP_CODES[j], nextLangCode);
						messageMapHandler.get(ALL_APP_CODES[j], nextLangCode);
					}
				}
			} else {
				//String defaultLang = AMSiteProperties.getCachedDefaultLang();
				
				// remove cached data first and add again
				// --------------------------------------
				
				for (int j = 0; j < ALL_APP_CODES.length; j++) {
					messageMapHandler.remove(ALL_APP_CODES[j], defaultLanguage);
					messageMapHandler.get(ALL_APP_CODES[j], defaultLanguage);
				}
			}			
		} catch (Exception e) {
			throw new SystemConfigException(SystemConfigException.STR_LOAD_ERROR, "Fail to Load Static Text Repository (" + APP_CODE_AMPARTNERPORTAL + ")", e);
		}
	}

	/*
	 * overload
	 * reload specific messageID
	 *
	 * @param	applicationCode	code of the application
	 * @param	language	language of the message to be retrieved
	 * @param	messageId	code of the message
	 */
	//public  void reload(String applicationCode, String language, String messageID) {
	//	messageMapHandler.remove(applicationCode, language, messageID);
	//	messageMapHandler.get(applicationCode, language, messageID);
	//}
	
	/**
	 * overload
	 * reload all messages under specified appCode and language
	 *
	 * @param	applicationCode	code of the application
	 * @param	language	language of the message to be retrieved
	 */
	public void reloadSTR(String applicationCode, String language) {
		try {
			messageMapHandler.remove(applicationCode, language);
			messageMapHandler.get(applicationCode, language);
		} catch (Exception e) {
			throw new SystemConfigException(SystemConfigException.STR_LOAD_ERROR, "Fail to Load Static Text Repository (" + APP_CODE_AMPARTNERPORTAL + ")", e);
		}
	}
	
	
	
	
	// -----------------------
	// message retrieal method
	// -----------------------
	
	public  String getMessage(String appCode, String lang, String msgCode) {
		return getMessage(appCode, lang, msgCode, (ArrayList) null);
	} 
	public  String getMessage(String appCode, String lang, String msgCode, Object param0) {
		List msgParams = new ArrayList();
		msgParams.add(param0);
		
		return getMessage(appCode, lang, msgCode, msgParams);
	}
	public  String getMessage(String appCode, String lang, String msgCode, List msgParams) {
		// 1. get message from Static Text Repository
		String msg = messageMapHandler.get(appCode, lang, msgCode);
		StringBuffer msgParsed = new StringBuffer();
		if (msg != null) {
			msgParsed.append(msg); 

			// 2. replace variable
			if (msgParams != null && msgParams.size() > 0) {
				for (int i = 0; i < msgParams.size(); i++) {
					Object nextMsgParam = msgParams.get(i);
					replaceMsgParam(msgParsed, "{" + i + "}", nextMsgParam);
				}
			}			
		}

		return msgParsed.toString();
	}
	
	/**
	 * 
	 * @param appCode
	 * @param lang
	 * @param msgCode
	 * @param msgParams Map key={string pattern to replace (e.g. "&lt;memberId&gt;")}, value={value to replace the pattern}
	 * @return parsed string
	 * @throws SystemNotAccessibleException
	 */
	public  String getMappedParamMessage(String appCode, String lang, String msgCode, Map msgParams) {
		// 1. get message from Static Text Repository
		String msg = messageMapHandler.get(appCode, lang, msgCode);
		StringBuffer msgParsed = new StringBuffer();
		if (msg != null) {
			msgParsed.append(msg); 

			// 2. replace variable
			if (msgParams != null && ( ! msgParams.isEmpty())) {
				Iterator msgParamIter = msgParams.keySet().iterator();
				
				while (msgParamIter.hasNext()) {
					String msgParamPattern = (String) msgParamIter.next(); // says "<ParamName>"
					Object msgArg = msgParams.get(msgParamPattern);
					
					replaceMsgParam(msgParsed, msgParamPattern, msgArg);
				}
			}			
		}

		return msgParsed.length() > 0 ? msgParsed.toString() : null;
	}

	
	
	
	// --------------------
	// other public utility
	// --------------------

	/**
	 * @param appCode
	 * @param msgCodePrefix
	 * @param lang
	 * @return return empty List nothing found
	 * 
	 * @throws SystemNotAccessibleException
	 * @throws IllegalArgumentException if appCode and/or lang is null
	 */
	public  List getMsgCodeList(String appCode, String msgCodePrefix, String lang) throws SystemNotAccessibleException {
		List msgCodeList = getMsgCodeList(appCode, lang);
		List resultList = new ArrayList();
		
		Iterator msgCodeIter = msgCodeList.iterator();
		while (msgCodeIter.hasNext()) {
			String nextKey = (String) msgCodeIter.next();
			
			if (nextKey.startsWith(msgCodePrefix)) {
				resultList.add(nextKey);	
			}
		}
			
		return resultList;
	}
	
	/**
	 * @param appCode
	 * @param lang
	 * @return return empty List nothing found
	 * 
	 * @throws SystemNotAccessibleException
	 * @throws IllegalArgumentException if appCode and/or lang is null
	 */
	public  List getMsgCodeList(String appCode, String lang) {
		List resultList = null;
		
		if (appCode == null) {
			throw new IllegalArgumentException("parameter \"appCode\" must not be null");
		}
		if (lang == null) {
			throw new IllegalArgumentException("parameter \"lang\" must not be null");
		}
		
		try {	
			Map msgMap = messageMapHandler.get(appCode, lang);
			
			if (msgMap != null) {
				resultList = new ArrayList(msgMap.keySet());
			} else {
				resultList = new ArrayList(0);
			} // end if (msgMap != null)
			
			return resultList;
		} catch (StrException se) {
			throw new SystemNotAccessibleException(SystemNotAccessibleException.STR_INACCESSIBLE, "STR not accessible", se); 
		}
	}
	
	/**
	 * @param appCode
	 * @param lang
	 * @return
	 * @throws SystemNotAccessibleException
	 */
	public  List getMsgValueList(String appCode, String msgCodePrefix, String lang) {
		final String methodName = "getMsgValueList";
		
	    List msgValueList = new ArrayList();
	    try {
            Map msgMap = messageMapHandler.get(appCode, lang);
            List msgCodeList = null;
            Iterator it = null;
            if (msgMap!=null) {
                msgCodeList = getMsgCodeList(appCode, lang);
                Collections.sort(msgCodeList);
                it = msgCodeList.iterator();
            }
            while (it.hasNext()) {
                String strKey = it.next().toString();
                if (msgMap != null && strKey.startsWith(msgCodePrefix)) {
            	    msgValueList.add(msgMap.get(strKey));
            	}
            }
        } catch (SystemNotAccessibleException e) {
        	throw e;
        } catch (StrException e) {
        	throw new SystemNotAccessibleException(SystemNotAccessibleException.STR_INACCESSIBLE, "STR not accessible", e);
        }
        return msgValueList;
	}
	
	
	// ---------------
	// internal helper
	// ---------------
	
    private  void replaceMsgParam(StringBuffer strBuffer, String variable, Object replace) {
        int s = 0;
        int e = 0;
    
        while ((e = strBuffer.indexOf(variable, s)) >= 0) {
        	strBuffer.delete(e, e + variable.length());
        	strBuffer.insert(e, replace);
        	
            s = e + variable.length();
        }
    }
}
