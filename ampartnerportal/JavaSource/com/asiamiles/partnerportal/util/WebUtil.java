package com.asiamiles.partnerportal.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.http.Cookie;


/**
 * User: CPPCLIC
 * Date: Sep 2, 2009
 * Time: 12:34:06 PM
 */
public class WebUtil {

    public static String getDivContent(String tagId, String inHTMLStr) {
        String returnStr;
        String matchStr = "<div id=\"" + tagId.toLowerCase() + "\">";
        String tempStr = inHTMLStr.toLowerCase();
        int index = tempStr.indexOf(matchStr);

        //If not found, return whole string
        if (index == -1) {
            returnStr = inHTMLStr;
        } else {
            int start = index + matchStr.length();
            int end = findEndTagPos(start, tempStr, "<div", "</div>", 1);
            returnStr = inHTMLStr.substring(index, end);
        }
        return returnStr;
    }

    private static int findEndTagPos(int start, String tempStr, String startTag, String endTag, int unClosedCount) {
        int endPos;
        //Look for close tag
        int index = tempStr.indexOf(endTag, start);
        if (index == -1) {
            return start;
        } else {
            unClosedCount--;
        }
        //Look for open tag
        Pattern p = Pattern.compile(startTag);
        Matcher m = p.matcher(tempStr.substring(start, index));
        while (m.find()) {
            unClosedCount++;
        }
        if (unClosedCount == 0) {
            endPos = index + endTag.length();
        } else {
            endPos = findEndTagPos(index + endTag.length(), tempStr, startTag, endTag, unClosedCount);
        }
        return endPos;
    }
    
    public static Cookie getCookieByName(Cookie[] cookies, String cookieName) {
		if(cookies!=null){
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie!=null && cookieName!=null){
					if (cookieName.equals(cookie.getName())){
					    if(cookie.getValue()==null){
					        return null;
					    } else if(cookie.getValue().equalsIgnoreCase("")){
					        return null;
						} else 
						    return cookie;
					}
				}
			}
	  	}
		return null;
	}
}
