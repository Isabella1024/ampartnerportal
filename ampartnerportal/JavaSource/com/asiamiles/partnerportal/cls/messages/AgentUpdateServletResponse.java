/*
 * Created on Jun 24, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

/**
 * Object class for the AgentUpdateServlet response. 
 * @author CPPBENL
 *
 */
public class AgentUpdateServletResponse extends CLSResponse {

	/**
	 * 
	 */
	public AgentUpdateServletResponse() {
		super();
	}

	public static AgentUpdateServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("AgentUpdateServlet", AgentUpdateServletResponse.class);
		Object obj = xstream.fromXML(xml);
		return (AgentUpdateServletResponse)obj;
	}
}
