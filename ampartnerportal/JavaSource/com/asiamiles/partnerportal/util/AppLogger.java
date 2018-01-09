/*
 * Created on Jun 15, 2009
 *
  */
package com.asiamiles.partnerportal.util;


import com.cathaypacific.utility.Logger;


/**
 * Singleton Factory class for retrieving Logger objects.
 * @author CPPBENL
 *
 */
public class AppLogger {

	
	private static final String AM_PARTNER_PORTAL = "amPartnerPortal"; // /repos/prop/logger/[application server name]/amPartnerPortal.xml
	private static final String AM_PARTNER_PORTAL_WA = "amPartnerPortalWA"; // /repos/prop/logger/[application server name]/amPartnerPortal.xml
	
	private static final Logger appLogger = Logger.getLogger(AM_PARTNER_PORTAL);
	private static final Logger waLogger = Logger.getLogger(AM_PARTNER_PORTAL_WA);
	
	private AppLogger() {}
	
	public static Logger getAppLogger() {
		return appLogger;
	}
	
	public static Logger getWALogger() {
		return waLogger;
	}
}
