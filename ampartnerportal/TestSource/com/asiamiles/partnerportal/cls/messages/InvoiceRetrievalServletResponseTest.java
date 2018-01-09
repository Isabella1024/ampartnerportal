/**
 * @author CPPKENW
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import java.net.URL;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.asiamiles.partnerportal.test.TestUtils;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

import junit.framework.TestCase;


public class InvoiceRetrievalServletResponseTest extends TestCase {

	private static Logger logger = AppLogger.getAppLogger();
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/InvoiceRetrievalServletResponse.xml");
		InvoiceRetrievalServletResponse response = InvoiceRetrievalServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
		assertEquals("196", response.getPartnerCode());
		assertEquals("LANGHAM PLACE HOTEL MONGKOK HK - REGULAR", response.getPartnerName());
		assertEquals("15", response.getMonthlyCutoffDay());
		assertEquals("800", response.getTimeZone());
		
		assertEquals(6,response.getEstablishment().size());
		
		assertEquals("com.asiamiles.partnerportal.cls.messages.InvoiceRetrievalServletResponse$Establishment",response.getEstablishment().get(0).getClass().getName());
		InvoiceRetrievalServletResponse.Establishment e = (InvoiceRetrievalServletResponse.Establishment)(response.getEstablishment().get(0));
		assertEquals("196",e.getEstablishmentCode());
		assertEquals("LANGHAM PLACE HOTEL, MONGKOK, HONG KONG",e.getEstablishmentName());
		assertEquals("20090720 09:48:33",e.getOldestCollectionTimestamp());
		assertEquals("20090720 09:48:33",e.getLatestCollectionTimestamp());
	}
}
