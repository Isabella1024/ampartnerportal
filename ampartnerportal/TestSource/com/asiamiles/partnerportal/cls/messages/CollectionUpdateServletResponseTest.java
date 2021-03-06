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


public class CollectionUpdateServletResponseTest extends TestCase {

	private static Logger logger = AppLogger.getAppLogger();
	
	public void testDecodeFromXML() throws Exception{
		URL resource = this.getClass().getResource("/com/asiamiles/partnerportal/cls/messages/test/CollectionUpdateServletResponse.xml");
		CollectionUpdateServletResponse response = CollectionUpdateServletResponse.decodeFromXML(TestUtils.fetchXML(resource));
		
		assertEquals(CLSResponse.STATUS_SUCCESS, response.getStatusCode());
	}
}
