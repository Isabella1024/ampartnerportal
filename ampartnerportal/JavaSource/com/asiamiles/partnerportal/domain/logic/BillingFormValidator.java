/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import java.util.Iterator;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.apache.commons.lang.StringUtils;

import com.asiamiles.partnerportal.domain.BillingForm;
import com.asiamiles.partnerportal.domain.NARClaimDetails;

/**
 * Validator for Billing Form data object
 * 
 * @author CPPKENW
 *  
 */
public class BillingFormValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return BillingForm.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		BillingForm billingForm = (BillingForm) target;
		validateDetail(billingForm, errors);
	}

	public void validateDetail(BillingForm billingForm, Errors errors) {
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invoiceNo", "required_invoiceNo", "Invoice No:This information must be supplied.");
		if (StringUtils.isEmpty(billingForm.getInvoiceNo())) {
			errors.rejectValue("invoiceNo", "required", "Invoice No:This information must be supplied.");
		} else if (!StringUtils.isAsciiPrintable(billingForm.getInvoiceNo())) {
			errors.rejectValue("invoiceNo", "invalidFormat", "Invoice No contains invalid character.");
		}
	}

	public void validateCheckbox(BillingForm billingForm, Errors errors) {
		//CPPJULI UPDATE FOR AML.34188 20140918 START
		/*boolean all_Unchecked = true;
		for (Iterator it = billingForm.getClaims().iterator(); it.hasNext();) {
			NARClaimDetails claimDetails = (NARClaimDetails) it.next();
			if (claimDetails.isToBeBilled()) {
				all_Unchecked = false;
			}
		}
		if (all_Unchecked) {
			errors.reject("nothingToProcess", "Please select a claim to bill.");
		}*/
		List c = billingForm.getClaims();
		for(int i = 0; i < c.size(); i++){
			NARClaimDetails claim = (NARClaimDetails) c.get(i);
			if(!claim.isToBeBilled()){
				if(!billingForm.getUnbillList().contains(claim.getClaimNo())){
					billingForm.getUnbillList().add(claim.getClaimNo());
				}
			}else{//选中的从unbilllist移除
				if(billingForm.getUnbillList().contains(claim.getClaimNo())){
					for(int j=0; j<billingForm.getUnbillList().size(); j++){
						if(billingForm.getUnbillList().get(j).toString().equals(claim.getClaimNo().toString())){
							billingForm.getUnbillList().remove(j);
						}
					}
				}
			}
		}
		String totalNum = billingForm.getTotalNum();
		int totalNumInt = 0;
		if(null != totalNum && !"".equals(totalNum)){
			totalNumInt = Integer.parseInt(totalNum);
		}
		if(billingForm.getUnbillList().size() == totalNumInt){
			errors.reject("nothingToProcess", "Please select a claim to bill.");
		}
		//CPPJULI UPDATE FOR AML.34188 20140918 END
	}
}
