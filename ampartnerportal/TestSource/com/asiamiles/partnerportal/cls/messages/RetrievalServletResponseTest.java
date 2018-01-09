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


public class RetrievalServletResponseTest extends TestCase {

	private static Logger logger = AppLogger.getAppLogger();
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/RetrievalServletResponse.xml");
		RetrievalServletResponse response = RetrievalServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
		assertEquals("1535104469",response.getMemberID());

		assertEquals(1,response.getNARDetails().size());
		
		assertEquals("com.asiamiles.partnerportal.cls.messages.RetrievalServletResponse$NARDetails",response.getNARDetails().get(0).getClass().getName());
		RetrievalServletResponse.NARDetails nar = (RetrievalServletResponse.NARDetails)(response.getNARDetails().get(0));
		
		assertEquals("3432603",nar.getClaimNo());
		assertEquals("TESTER",nar.getHolderName());
		assertEquals("TEST",nar.getHolderFirstName());
		assertEquals("20090720 09:48:10",nar.getCollectionTime());
		assertEquals("20090720 09:48:33",nar.getCompletionTime());
		
	}
}
