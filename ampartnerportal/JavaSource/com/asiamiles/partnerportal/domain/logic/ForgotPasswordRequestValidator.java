/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.ForgotPasswordRequest;

/**
 * Validator for validating forgot password requests
 * @author CPPBENL
 *
 */
public class ForgotPasswordRequestValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return ForgotPasswordRequest.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		ForgotPasswordRequest form = (ForgotPasswordRequest)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentID", "required", "Agent ID required");
		
		if (StringUtils.isNotEmpty(form.getAgentID()) && !StringUtils.isAsciiPrintable(form.getAgentID())) {
			errors.rejectValue("agentID", "invalidFormat", new Object[]{form.getAgentID()}, "Agent ID contains invalid characters");
		}
	}

}
