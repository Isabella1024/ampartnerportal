/*
 * Created on Jul 27, 2009
 *
 */
package com.asiamiles.partnerportal.util;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.SystemConfigException;

/**
 * @author CPPBENL
 *
 */
public class URLHelper {

	private SiteProperties siteProperties;
	
	public URLHelper() {}
	

	/**
	 * @param siteProperties The siteProperties to set.
	 */
	public void setSiteProperties(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}
	
	private int getInternalSSLPort() {
		int internalSSLPort;
		try {
			internalSSLPort = Integer.parseInt(siteProperties.getPropertyByPlatform(SiteProperties.SSL_INTERNAL_PORT));
			return internalSSLPort;
		} catch (Exception e) {
			throw new SystemConfigException(SystemConfigException.SSL_INVALID_PORT, e);
		}
	}
	
	public boolean isSSL(HttpServletRequest request) {
		return (request.isSecure() || request.getServerPort() == 443 || request.getServerPort() == getInternalSSLPort());
	}
	
	public String sslFilter(String url) {
		String secureURL = url.replaceFirst("http:", "https:");
		secureURL = secureURL.replaceFirst(":443", "").replaceFirst(":" + getInternalSSLPort(), "");
		return secureURL;
	}
	
	public String getSiteURL(String serverName, int serverPort, String servletPath, Map parameters, boolean ssl) {
		StringBuffer sb = new StringBuffer();
		if (ssl) {
			sb.append("https://");
		} else {
			sb.append("http://");
		}
		sb.append(serverName);
		
		//Append port information for 'local' environment
		if (SiteProperties.PLATFORM_LOCAL.equals(siteProperties.getPlatform())) {
			sb.append(':').append(""+serverPort);
		}
		
		if (!servletPath.startsWith("/")) {
			sb.append('/');
		}
		sb.append(servletPath);
		sb.append(getParameterString(parameters));
		
		return sb.toString();
		
	}
	
	public String getSiteURL(String serverName, int serverPort, String contextPath, String page, Map parameters, boolean ssl) {
		StringBuffer sb = new StringBuffer();
		if (ssl) {
			sb.append("https://");
		} else {
			sb.append("http://");
		}
		sb.append(serverName);

		//Append port information for 'local' environment
		if (SiteProperties.PLATFORM_LOCAL.equals(siteProperties.getPlatform())) {
			sb.append(':').append(""+serverPort);
		}

		if (!contextPath.startsWith("/")) {
			sb.append('/');
		}
		sb.append(contextPath);
		if (!page.startsWith("/")) {
			sb.append('/');
		}
		sb.append(page);
		sb.append(getParameterString(parameters));
		
		return sb.toString();
	}
	
	protected StringBuffer getParameterString(Map parameters) {
		StringBuffer sb = new StringBuffer();
		if (parameters != null && parameters.size() > 0) {
			sb.append('?');
			for (Iterator it = parameters.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next();
				sb.append(entry.getKey()).append('=').append(entry.getValue());
				if (it.hasNext()) {
					sb.append('&');
				}
			}
		}
		return sb;
	}
}
