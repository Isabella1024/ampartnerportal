/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.ReportForm;
import com.asiamiles.partnerportal.util.DateConvertor;

/**
 * @author CPPKENW
 *
 */
public class ReportFormValidator implements Validator {
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return ReportForm.class.isAssignableFrom(clazz);
	}
		
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		ReportForm reportForm = (ReportForm)target;
		validateDate(reportForm, errors);
	}
	
	public void validateDate(ReportForm reportForm, Errors errors) {
		Date fromDate = null;
		Date toDate = null;
		if (StringUtils.isEmpty(reportForm.getFromDay())
				|| StringUtils.isEmpty(reportForm.getFromMonth())
				|| StringUtils.isEmpty(reportForm.getFromYear())) {
			errors.rejectValue("fromDay", "required", "From Date:This information must be supplied.");
		} else {
			fromDate = DateConvertor.parseDate(reportForm.getFromYear(), reportForm.getFromMonth(), reportForm.getFromDay());
		}

		if (StringUtils.isEmpty(reportForm.getToDay())
				|| StringUtils.isEmpty(reportForm.getToMonth())
				|| StringUtils.isEmpty(reportForm.getToYear())) {
			errors.rejectValue("toDay", "required", "To Date:This information must be supplied.");
		} else {
			toDate = DateConvertor.parseDate(reportForm.getToYear(), reportForm.getToMonth(), reportForm.getToDay());
		}
		
		if (fromDate != null && toDate != null && fromDate.compareTo(toDate)>0){
			errors.reject("error_invalid_date_range","From Date past To Date. Please select a proper date range.");
		}
	}

	public void validateKeyword(ReportForm reportForm, Errors errors) {
		if (StringUtils.isNotBlank(reportForm.getKeyword()) && !StringUtils.isAsciiPrintable(reportForm.getKeyword())) {
			errors.rejectValue("keyword","invalidFormat", new Object[]{reportForm.getKeyword()}, "Agent ID Filter: Contains invalid characters. Please use valid alphanumeric characters.");
		}
	}
}
