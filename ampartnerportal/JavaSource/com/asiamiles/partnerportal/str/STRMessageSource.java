/*
 * Created on Jul 6, 2009
 *
 */
package com.asiamiles.partnerportal.str;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.AbstractMessageSource;

/**
 * MessageSource class for resolving messages stored in the STR.
 * @author CPPBENL
 *
 */
public class STRMessageSource extends AbstractMessageSource {

	private STRFacade strFacade;
	
	/**
	 * @param strFacade The strFacade to set.
	 */
	public void setStrFacade(STRFacade strFacade) {
		this.strFacade = strFacade;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)
	 */
	protected MessageFormat resolveCode(String code, Locale locale) {
		String format = strFacade.getMessage("AMPARTNERPORTAL", locale.getLanguage(), code);
//		System.out.println("*** resolveCode("+code+","+ locale + "): " + format);
		if (StringUtils.isEmpty(format)) {
			return null;
		}
		return new MessageFormat(format); //XXX Consider caching these MessageFormat objects
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractMessageSource#resolveCodeWithoutArguments(java.lang.String, java.util.Locale)
	 */
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String result = strFacade.getMessage("AMPARTNERPORTAL", locale.getLanguage(), code);
//		System.out.println("*** resolveCodeWithoutArguments("+code+","+ locale + "): " + result);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		return result;
	}
}
