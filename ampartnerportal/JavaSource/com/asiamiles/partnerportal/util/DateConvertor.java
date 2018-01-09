/*
 * Created on Jun 15, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.lang.Integer;
import java.util.ArrayList;
/**
 * @author CPPKENW
 *
 */
public class DateConvertor {

	private DateConvertor() {
	}

	
	/**
	 * export the years in the future
	 * i.e. if current year is 2009, then the exported would be 2008,2009
	 * @return List&lt;String&gt; of the years 
	 */ 
	public static List exportYears() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(new Date());
		ArrayList list = new ArrayList();
		list.add(dateFormat.format(tempCalendar.getTime()));
		tempCalendar.add(Calendar.YEAR, -1);
		list.add(0, dateFormat.format(tempCalendar.getTime()));
		return list;
	}

	/**
	 * export the 12 months
	 * @return A Map&lt;Integer, String&gt; of the 12 months
	 */ 
	public static Map exportMonths() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(new Date());
		tempCalendar.set(Calendar.DATE, 1);
		Map map = new TreeMap();
		for (int i = 0; i < 12; i++) {
			tempCalendar.set(Calendar.MONTH, i);
			map.put(new Integer(i+1), dateFormat.format(tempCalendar.getTime()));
		}
		return map;
	}
	
	/**
	 * export the all possible 31 days in any month 
	 * @return List&lt;Integer&gt; of the 31 days in any month
	 */
	public static List exportDays() {
		List list = new ArrayList(); 
		for (int i = 1; i <= 31; i++) {
			list.add(new Integer(i));
		}
		return list;
	}
	
	public static Date parseDate(String year, String month, String day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.MONTH, Integer.parseInt(month)-1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		return c.getTime();
	}	
	
}
