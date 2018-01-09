/*
 * Created on Jun 25, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

/**
 * @author CPPBENL
 *
 */
public class ChangePasswordServletResponse extends CLSResponse {

	/**
	 * 
	 */
	public ChangePasswordServletResponse() {
		super();
	}

	public static ChangePasswordServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("ChangePwdServlet", ChangePasswordServletResponse.class);
		Object obj = xstream.fromXML(xml);
		return (ChangePasswordServletResponse)obj;
	}
}
