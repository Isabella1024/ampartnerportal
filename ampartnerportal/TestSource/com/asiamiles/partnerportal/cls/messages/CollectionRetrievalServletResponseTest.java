/*
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


public class CollectionRetrievalServletResponseTest extends TestCase {

	private static Logger logger = AppLogger.getAppLogger();
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/CollectionRetrievalServletResponse.xml");
		CollectionRetrievalServletResponse response = CollectionRetrievalServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());

		assertEquals(50,response.getNARDetails().size());
		
		assertEquals("com.asiamiles.partnerportal.cls.messages.CollectionRetrievalServletResponse$NARDetails",response.getNARDetails().get(0).getClass().getName());
		CollectionRetrievalServletResponse.NARDetails nar = (CollectionRetrievalServletResponse.NARDetails)(response.getNARDetails().get(0));
		assertEquals("3432274",nar.getClaimNo());
		assertEquals("1535104469",nar.getMemberID());
		assertEquals("TESTER",nar.getHolderName());
		assertEquals("TEST",nar.getHolderFirstName());
		assertEquals("20090629 12:18:27",nar.getCollectionTime());
		assertEquals("TEST12",nar.getCollectionHandledBy());		
		assertEquals("20090629 12:18:36",nar.getCompletionTime());
		assertEquals("TEST12",nar.getCompletionHandledBy());
		assertEquals("20090709", nar.getBilledDate());
	}
}
