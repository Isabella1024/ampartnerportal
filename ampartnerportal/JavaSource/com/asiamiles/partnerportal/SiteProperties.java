/*
 * Created on Jun 12, 2009
 *
 */
package com.asiamiles.partnerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

/**
 * Class for accessing the properties values stored in various property files.
 * 
 * If property names are formatted in the format using dot notation 
 * (e.g. <code>parentCategory.childCategory.grandChildCategory</code>)
 * then the property value lookup will cacade up the dot notation chain  
 * when the property value cannot be resolved using supplied property key.
 * 
 * For example, suppose that we have the following properties:
 * <code>
 * a=1<br/>
 * a.b=12<br/>
 * </code>
 * Then :
 * <ul>
 * 	<li><code>getProperty(a)</code> will return 1</li>
 *  <li><code>getProperty(a.b)</code> will return 12</li>
 *  <li><code>getProperty(a.b.c)</code> will return 12 (cascading the search to call <code>getProperty(a.b)</code></li>
 *  <li><code>getProperty(a.b.c.d)</code> will return 12 (cascading the search to call <code>getProperty(a.b.c)</code> and then <code>getProperty(a.b)</code></li> 
 * </ul>
 * @author CPPBENL
 *
 * 
 */
public class SiteProperties {

	// ### Platform Constants ###
	
	/** Local Developer's machine */
	public static final String PLATFORM_LOCAL = "LOCAL";
	
	/** Production Platform */
	public static final String PLATFORM_PRODUCTION = "PRD";
	
	/** Staging Platform */
	public static final String PLATFORM_STAGING = "STA";
	
	/** Shared Development Platform */
	public static final String PLATFORM_DEVELOPMENT = "DEV";

	
	public static final String HOMEPAGE_URL = "common.homepageURL";
	
	public static final String INSTANT_REDEEM_HOMEPAGE_URL = "common.instantRedeem.homepageURL";
	public static final String INSTANT_REDEEM_QR_CODE_READER_URL = "common.instantRedeem.qrCodeReaderURL";
	public static final String INSTANT_REDEEM_REDEMPTION_URL = "common.instantRedeem.redemptionURL";
	public static final String INSTANT_REDEEM_INVALID_OFFER_URL = "common.instantRedeem.invalidOfferURL";
	public static final String INSTANT_REDEEM_INVALID_CLAIM_URL = "common.instantRedeem.invalidClaimURL";
	public static final String INSTANT_REDEEM_INVALID_PACKAGE_URL = "common.instantRedeem.invalidPackageURL";
	public static final String INSTANT_REDEEM_INVALID_MEMBER_URL = "common.instantRedeem.invalidMemberURL";
	public static final String INSTANT_REDEEM_EXPIRED_OFFER_URL = "common.instantRedeem.expiredOfferURL";
	public static final String INSTANT_REDEEM_REDEEMED_OFFER_URL = "common.instantRedeem.redeemedOfferURL";
	public static final String NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL = "common.normalAgent.instantRedeem.landingURL";
	//cppjox add for AML38938 20160523 start
	public static final String ACCRUL_NORMAL_AGENT_INSTANT_REDEEM_LANDING_URL = "common.AccrulNormalAgent.instantRedeem.landingURL";
	//cppjox add for AML38938 20160523 end
	public static final String SUPERVISOR_INSTANT_REDEEM_LANDING_URL = "common.supervisorAgent.instantRedeem.landingURL";
	public static final String FINANCE_AGENT_INSTANT_REDEEM_LANDING_URL = "common.financeAgent.instantRedeem.landingURL";
	public static final String ADMINISTRATOR_INSTANT_REDEEM_LANDING_URL = "common.administrator.instantRedeem.landingURL";

	public static final String NORMAL_AGENT_LANDING_URL = "common.normalAgent.landingURL";
	//cppjox add for AML38938 20160523 start
	public static final String ACCRUL_NORMAL_AGENT_LANDING_URL = "common.AccrulNormalAgent.landingURL";
	//cppjox add for AML38938 20160523 end
	public static final String SUPERVISOR_LANDING_URL = "common.supervisorAgent.landingURL";
	public static final String FINANCE_AGENT_LANDING_URL = "common.financeAgent.landingURL";
	public static final String ADMINISTRATOR_LANDING_URL = "common.administrator.landingURL";
	
	public static final String NORMAL_AGENT_ALLOWED_URL_LIST = "common.normalAgent.allowedURLList";
	public static final String SUPERVISOR_ALLOWED_URL_LIST = "common.supervisorAgent.allowedURLList";
	public static final String FINANCE_AGENT_ALLOWED_URL_LIST = "common.financeAgent.allowedURLList";
	public static final String ADMINISTRATOR_ALLOWED_URL_LIST = "common.administrator.allowedURLList";	
	//cppjox add for AML38938 20160523 start
	public static final String ACCRUL_ADMINISTRATOR_ALLOWED_URL_LIST = "common.accrul.administrator.allowedURLList";
	public static final String ACCRUL_NORMAL_ALLOWED_URL_LIST = "common.accrul.normalAgent.allowedURLList";
	//cppjox add for AML38938 20160523 end
	public static final String INSTANT_REDEEM_GENERATE_URL = "instant.redeem.generate.url";	
	
	public static final String SUPPORTED_LANGUAGES = "common.supportedLangList";
	public static final String DEFAULT_LANGUAGE = "common.defaultLang";
	public static final String GRACE_PERIOD = "redemption.gracePeriod";
	
	public static final String ENFORCE_SSL = "common.ssl.enforceSSL";
	public static final String SSL_INTERNAL_PORT = "common.ssl.internalPort";
	
	private Resource propertiesPath;
	private String platform;
	
	private Properties cachedProperties;
	
	public SiteProperties() throws IOException {
		cachedProperties = new Properties();
	}
	
	public void reload() throws IOException{
		cachedProperties = new Properties();
		loadResource();
	}
	
	private void loadResource() throws IOException {
		cachedProperties.load(propertiesPath.getInputStream());
	}
	
	/**
	 * @param propertiesPath The propertiesPath to set.
	 */
	public void setPropertiesPath(Resource propertiesPath) throws IOException{
		this.propertiesPath = propertiesPath;
		reload();
	}
	
	/**
	 * @return Returns the platform.
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * @param platform The platform to set.
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is formatted in dot notation then the search will cascade up the
	 * dot notation chain until a value is found.
	 * The method returns null if the property is not found.
	 * @param key the hashtable key
	 * @param defaultValue the default value
	 * @return the value in this property list with the specified key value.
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	public String getProperty(String key) {
		String searchKey = key;
		if (cachedProperties.containsKey(key)) {
			return cachedProperties.getProperty(key);
		} else if (StringUtils.contains(key, '.')) {
			return getProperty(key.substring(0, key.lastIndexOf('.')));
		} else {
			return null;
		}
	}
	/**
	 * Searches for the property with the specified key in this property list.  
	 * The method returns the default value argument if the property is not found.
	 * @param key the hashtable key
	 * @param defaultValue the default value
	 * @return the value in this property list with the specified key value.
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	public String getProperty(String key, String defaultValue) {
		String searchKey = key;
		if (cachedProperties.containsKey(key)) {
			return cachedProperties.getProperty(key);
		} else if (StringUtils.contains(key, '.')) {
			return getProperty(key.substring(0, key.lastIndexOf('.')));
		} else {
			return defaultValue;
		}	
	}
	
	/**
	 * Searches for the property with the specified key in this property list, and returns
	 * the property value as a List of values. This method by default uses the comma <code>","</code>
	 * character as the delimiter for the property values.
	 * @param key
	 * @return a List&lt;String&gt; of values in this property list with the specified key value.
	 */
	public List getPropertyAsList(String key) {
		List result = new ArrayList();
		String propValue = getProperty(key);
		if (StringUtils.isNotEmpty(propValue)) {
			String[] values = propValue.split(",");
			for (int i = 0; i < values.length; i++) {
				result.add(values[i].trim());
			}
		}
		return result;
		
	}
	
	/**
	 * 
	 * @param key the hashtable key
	 * @return the value in this property list with the specified key value.
	 */
	public String getPropertyByPlatform(String key) {
		String fullKey = key + "." + platform;
		
		if (cachedProperties.containsKey(fullKey)) {
			return cachedProperties.getProperty(fullKey);
		} else if (cachedProperties.containsKey(key)) {
			return cachedProperties.getProperty(key);
		} else if (StringUtils.contains(key, '.')){
			return getPropertyByPlatform(key.substring(0, key.lastIndexOf('.')));
		}
			
		return null;
	}
}
