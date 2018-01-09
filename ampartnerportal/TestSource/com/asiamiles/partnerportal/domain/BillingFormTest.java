/*
 * Created on Aug 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class BillingFormTest extends TestCase {

	private BillingForm form;
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		form = new BillingForm();
		
	}
	
	public void testGetCutOffDateNormalCases() throws Exception {
		Calendar c = Calendar.getInstance();
		
		setToday(c, 2009, Calendar.AUGUST, 11);
		form.setToday(c.getTime());
		form.setCutOffDay("10");
		assertEquals(getDate("20090810235959"), form.getCutOffDate());
		
		setToday(c, 2009, Calendar.AUGUST, 11);
		form.setToday(c.getTime());
		form.setCutOffDay("12");
		assertEquals(getDate("20090712235959"), form.getCutOffDate());		
	}
	
	public void testGetCutOffDateBoundaryCases() throws Exception {
		Calendar c = Calendar.getInstance();
		
		setToday(c, 2009, Calendar.MARCH, 29);
		form.setToday(c.getTime());
		form.setCutOffDay("30");
		assertEquals(getDate("20090228235959"), form.getCutOffDate());

		setToday(c, 2008, Calendar.MARCH, 1);
		form.setToday(c.getTime());
		form.setCutOffDay("30");
		assertEquals(getDate("20080229235959"), form.getCutOffDate());

		setToday(c, 2008, Calendar.MARCH, 1);
		form.setToday(c.getTime());
		form.setCutOffDay("1");
		assertEquals(getDate("20080201235959"), form.getCutOffDate());
	}

	private void setToday(Calendar c, int year, int month, int day) {
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
	}
	
	private Date getDate(String dateString) throws Exception {
		return format.parse(dateString);
	}
}
