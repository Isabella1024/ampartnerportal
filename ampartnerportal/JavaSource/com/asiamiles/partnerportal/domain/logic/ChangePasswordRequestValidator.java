/*
 * Created on Jun 29, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.ChangePasswordRequest;
import com.asiamiles.partnerportal.util.RegexUtils;

/**
 * Validator for the Change Password Request
 * @author CPPBENL
 *
 */
public class ChangePasswordRequestValidator implements Validator {

	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return ChangePasswordRequest.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		ChangePasswordRequest form = (ChangePasswordRequest)target;
		
		errors.setNestedPath("");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "required", "You must supply the old password"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "required", "You must supply the new password"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required", "You must confirm the new password"); 
		
		if (StringUtils.isNotBlank(form.getNewPassword())) {
			
			Pattern pattern = RegexUtils.getPasswordPattern();
			Matcher matcher = pattern.matcher(form.getNewPassword());
			
			if (!matcher.matches()) {
				errors.rejectValue("newPassword", "invalidFormat",  "New password must be alphanumerics only");
			} 
		}
		if(StringUtils.isNotBlank(form.getNewPassword())
				&& StringUtils.isNotBlank(form.getConfirmPassword())
				&& !form.getNewPassword().equals(form.getConfirmPassword()) ) {
			errors.rejectValue("confirmPassword", "error_confirmPasswordMismatch", "Confirmation password and new password does not match");
		}
		
		errors.setNestedPath("");

	}

}
