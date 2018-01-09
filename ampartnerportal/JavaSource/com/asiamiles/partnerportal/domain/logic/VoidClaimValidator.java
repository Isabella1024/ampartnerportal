/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;



import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.ReportForm;

/**
 * @author CPPKENW
 *
 */
public class VoidClaimValidator implements Validator {

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
		validateReason(reportForm, errors);
	}
	
	
	public void validateReason(ReportForm reportForm, Errors errors) {
		// void reason is required if action is claim void
		// (void reason is not required if action is return to previous page)
		if (reportForm.getVoidedClaim() != null && !reportForm.getVoidedClaim().equals("")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "required", "Reason:This information must be supplied.");
			
			if (StringUtils.isNotEmpty(reportForm.getReason()) && !StringUtils.isAsciiPrintable(reportForm.getReason())) {
				errors.rejectValue("reason", "invalidFormat", new Object[]{reportForm.getReason()}, "Void Reason contains invalid characters");
			}
		}
	}	
}
