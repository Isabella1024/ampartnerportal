package com.asiamiles.partnerportal.domain.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.Membership;
import com.asiamiles.partnerportal.domain.PaperlessRedemptionForm;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.RegexUtils;
import com.cathaypacific.utility.Logger;

public class MembershipValidator implements Validator {
	 private Logger logger = AppLogger.getAppLogger();
    
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return Membership.class.isAssignableFrom(clazz);
    }

    
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    	logger.info("MembershipValidator>>validate()");
        Membership membership = (Membership)target;
        errors.setNestedPath("");
        
//        Pattern pattern = Pattern.compile("[0-9]*");
//        if(!pattern.matcher(membership.getMemberId()).matches()){
//        	errors.rejectValue("memberid", "invalidFormat", "memberid is not valid.");
//        }
        
        //      Check for empty fields
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberId", "non_emepty", "Please input memberId.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "non_emepty", "Please input lastName.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "non_emepty", "Please input firstName.");
        if("S".equals(membership.getCheckingMethod())){
//        	logger.info("checkingMethod:s   next check first/last nameChar.");
//        	logger.info("firstNameChar:"+membership.getFirstNameChar());
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastNameChar", "required", "lastNameChar is required");
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstNameChar", "required", "firstNameChar is required");
        	
            if("".equals(membership.getLastNameChar())||!StringUtils.isNumeric(membership.getLastNameChar())||0==Integer.parseInt(membership.getLastNameChar())||Integer.parseInt(membership.getLastNameChar())>40){
                errors.rejectValue("lastNameChar","invalidFormat","Invalid lastNameChar.");
            }
            if(!"NeedSwapName".equals(membership.getIsSwapName())){
	            if("".equals(membership.getFirstNameChar())||!StringUtils.isNumeric(membership.getFirstNameChar())||0==Integer.parseInt(membership.getFirstNameChar())||Integer.parseInt(membership.getFirstNameChar())>25){
	                errors.rejectValue("firstNameChar","invalidFormat","Invalid firstNameChar.");
	            }
            }else{
            	if("".equals(membership.getFirstNameChar())||!StringUtils.isNumeric(membership.getFirstNameChar())||0==Integer.parseInt(membership.getFirstNameChar())||Integer.parseInt(membership.getFirstNameChar())>40){
	                errors.rejectValue("firstNameChar","invalidFormat","Invalid firstNameChar.");
	            }
            }
        }
        validateMemberInfo(membership, errors);
        validateNameLength( membership, errors);
        validateNameFormat(membership, errors);
        //      Check for invalid characters/formats
       
//        Pattern namePattern = RegexUtils.getAgentNamePattern();
//        Matcher familyNameMatcher = namePattern.matcher(membership.getLastName());
//        Matcher firstNameMatcher = namePattern.matcher(membership.getFirstName());
//        
//        if (StringUtils.isNotBlank(membership.getLastName()) && !familyNameMatcher.matches()) {
//            errors.rejectValue("lastName", "invalidFormat", new Object[]{membership.getLastName()}, "Last Name contains invalid characters");
//        }
//        if (StringUtils.isNotBlank(membership.getFirstName()) && !firstNameMatcher.matches()) {
//            errors.rejectValue("firstName", "invalidFormat", new Object[]{membership.getFirstName()}, "First Name contains invalid characters");
//        }
        errors.setNestedPath("");
    }

    
    public void validateMemberInfo(Membership membership, Errors errors) {
    	if(errors.hasErrors()){
        	return;
        }
//        if (membership.getMemberId() == null || StringUtils.isEmpty(membership.getMemberId())) {
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberId", "required", "Member ID required.");
//        }
        
    	char memberidchar[] = membership.getMemberId().toCharArray();
        int arraysize = memberidchar.length;
        if (StringUtils.isEmpty(membership.getMemberId()) || !StringUtils.isNumeric(membership.getMemberId()) || memberidchar.length!=10) {
            errors.rejectValue("memberId", "invalid", "0003 - Invalid CX FFP Number.");
        }else{
            char firstchar = memberidchar[0];
            char lastchar = memberidchar[9];
            int sum=0;
        	 for(int i=0;i<9;i++){
             	int value = Character.getNumericValue(memberidchar[i]);
                 sum =sum+(i+1)*value;
             }
             if(firstchar==0 ||(sum%10)!=Character.getNumericValue(lastchar)){
             	errors.rejectValue("memberId", "invalid", "0003 - Invalid CX FFP Number.");
             }
        }
    }
    
    public void validateNameFormat(Membership membership, Errors errors) {
    	//name check, name liked "..AAA.." and "..AABBCC.." is not valid,and only allow char'a-z','A-Z','`','-',' ',last name not allowed '`',' ','-' start or end with.
    	if(errors.hasErrors()){
        	return;
        }
    	boolean flagForFirstName = false;
    	boolean flagForLastName = false;
    	char firstName[]= membership.getFirstName().toUpperCase().toCharArray();
    	char lastName[] = membership.getLastName().toUpperCase().toCharArray();
    	
    	for(int i=0;i<lastName.length;i++){
    		if(!((Character.getNumericValue(lastName[i])>=10&&Character.getNumericValue(lastName[i])<=35)||lastName[i]=='\''||lastName[i]==' '||lastName[i]=='-')
    				||lastName[0]=='\''||lastName[0]==' '||lastName[0]=='-'||lastName[lastName.length-1]=='\''||lastName[lastName.length-1]==' '||lastName[lastName.length-1]=='-'){
    			flagForLastName = true;
    		}
    	}
    	
    	for(int i=0;i<firstName.length;i++){
    		if(!((Character.getNumericValue(firstName[i])>=10&&Character.getNumericValue(firstName[i])<=35)||firstName[i]=='\''||firstName[i]==' '||firstName[i]=='-')){
    			flagForFirstName = true;
    		}
    	}
    	
    	for(int i=0;i<firstName.length-2;i++){
    		if(firstName[i]==firstName[i+1] && firstName[i]==firstName[i+2] && firstName[i]!='I'){
    			flagForFirstName =true;
    		}
    		if(firstName[i]== firstName[i+1]&& i<firstName.length-5){
    			if(firstName[i+2]==firstName[i+3]&&firstName[i+4]==firstName[i+5]){
    				flagForFirstName = true;
    			}
    		}
    	}
    	
    	for(int i=0;i<lastName.length-2;i++){
    		if(lastName[i]==lastName[i+1] && lastName[i]==lastName[i+2] && lastName[i]!='I'){
    			flagForLastName =true;
    		}
    		if(lastName[i]== lastName[i+1]&& i<lastName.length-5){
    			if(lastName[i+2]==lastName[i+3]&&lastName[i+4]==lastName[i+5]){
    				flagForLastName = true;
    			}
    		}
    	}
    	if(flagForFirstName){
    		errors.rejectValue("firstName","invalid","Given Name contains Invalid characters.");
    	}
    	if(flagForLastName){
    		errors.rejectValue("lastName","invalid","Surname contains invalid characters.");
    	}
    	
    	
    }
    
    public void validateNameLength(Membership membership, Errors errors) {
    	logger.info("MembershipValidator>>validateNameLength()");
    	logger.info("membership.getIsSwapName():"+ membership.getIsSwapName());
    	//name length check
    	if(errors.hasErrors()){
        	return;
        }
    	if(membership.getLastName().length()>40){
    		errors.rejectValue("lastName","invalidLength","Surname should not be greater than 40.");
    	}
//    	if(membership.getFirstName().length()>25){
//    		if(!"NeedSwapName".equals(membership.getIsSwapName())){
//    			errors.rejectValue("firstName","invalidLengthsample2","Given Name should not be greater than 25.");
//    		}else{
//    			if(membership.getFirstName().length()>40){
//    				errors.rejectValue("firstName","invalidLength","Given Name should not be greater than 40.");
//    			}
//    		}
//    	} 
    	if(!"NeedSwapName".equals(membership.getIsSwapName()) && membership.getFirstName().length()>25){
    		logger.info("enable swap name check is not selected and length of Given name is more than 25 characters.");
    		errors.rejectValue("firstName","invalidLengthsample2","Given Name should not be greater than 25.");
    	}else if("NeedSwapName".equals(membership.getIsSwapName()) && membership.getFirstName().length()>40){
    		logger.info("enable swap name check is selected and length of Given name is more than 40 characters.");
    		errors.rejectValue("firstName","invalidLength","Given Name should not be greater than 40.");
    	}
    	
    	if(membership.getFirstName().length()+membership.getLastName().length()>65){
    		errors.rejectValue("lastName","invalidlength_fullName_not","Exceed maximum allowed length of member’s name.");
    		errors.rejectValue("firstName","blank_error","");
    		errors.rejectValue("lastName","blank_error","");
    	}
    }
}
