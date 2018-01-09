/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.Agent;
import com.asiamiles.partnerportal.util.RegexUtils;

/**
 * Validator for Agent objects
 * @author CPPBENL
 *
 */
public class AgentValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return Agent.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		Agent agent = (Agent)target;
		errors.setNestedPath("");

		//Check for empty fields
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentID", "required", "Agent ID is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "familyName", "required", "Family Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required", "First Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "required", "Email Address required");

		//Check for invalid characters/formats
		EmailValidator emailValidator = EmailValidator.getInstance();
		if (StringUtils.isNotBlank(agent.getEmailAddress()) && !emailValidator.isValid(agent.getEmailAddress())) {
			errors.rejectValue("emailAddress", "invalidFormat",new Object[]{agent.getEmailAddress()}, "Email Address is invalid");
		}
		
		Pattern namePattern = RegexUtils.getAgentNamePattern();
		Matcher familyNameMatcher = namePattern.matcher(agent.getFamilyName());
		Matcher firstNameMatcher = namePattern.matcher(agent.getFirstName());
		
		if (StringUtils.isNotBlank(agent.getFamilyName()) && !familyNameMatcher.matches()) {
			errors.rejectValue("familyName", "invalidFormat", new Object[]{agent.getFamilyName()}, "Family Name contains invalid characters");
		}
		if (StringUtils.isNotBlank(agent.getFirstName()) && !firstNameMatcher.matches()) {
			errors.rejectValue("firstName", "invalidFormat", new Object[]{agent.getFirstName()}, "First Name contains invalid characters");
		}
		
		if (StringUtils.isNotBlank(agent.getAgentID()) && !StringUtils.isAsciiPrintable(agent.getAgentID())) {
			errors.rejectValue("agentID", "invalidFormat", new Object[] {agent.getAgentID()}, "Agent ID contains invalid characters");
		}
		if (StringUtils.isNotBlank(agent.getRemarks()) && !StringUtils.isAsciiPrintable(agent.getRemarks())) {
			errors.rejectValue("remarks", "invalidFormat", new Object[] {agent.getRemarks()}, "Remarks contains invalid characters");
		}
		
		errors.setNestedPath("");
	}

}
