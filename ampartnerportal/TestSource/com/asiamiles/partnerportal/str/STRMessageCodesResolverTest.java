/*
 * Created on Jul 6, 2009
 *
 */
package com.asiamiles.partnerportal.str;

import junit.framework.TestCase;

/**
 * 
 * @author CPPBENL
 *
 */
public class STRMessageCodesResolverTest extends TestCase {

	private STRMessageCodesResolver resolver;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		resolver = new STRMessageCodesResolver();
		resolver.setPrefix("validation_");
	}

	public void testPostProcessMessageCode() {
		String[] codes = resolver.resolveMessageCodes("required", "agent", "agentID", String.class);
		
		for (int i = 0; i < codes.length; i++) {
			assertTrue(codes[i].indexOf('.') < 0);
		}
	}
	
	
	
}
