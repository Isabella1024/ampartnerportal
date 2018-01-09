/*
 * Created on Jun 22, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

/**
 * @author CPPKENW
 *
 */
public class CollectionUpdateServletResponse extends CLSResponse {

	private String SMSNotification;
	private String deliveryPhoneCountry;
	private String deliveryPhoneZone;
	private String deliveryPhoneNo;

	public CollectionUpdateServletResponse() {}	

	public static CollectionUpdateServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("PaperlessCollectionServlet", CollectionUpdateServletResponse.class);
		Object obj = xstream.fromXML(xml);
		return (CollectionUpdateServletResponse)obj;
	}


}
