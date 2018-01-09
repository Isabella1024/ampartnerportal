/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PendingClaimsForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.str.STRFacade;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

/**
 * Validator for the Pending Claims Form Data Object
 * @author CPPKENW
 *
 */
public class PendingClaimsFormValidator implements Validator {

	private STRFacade strFacade;
	private Logger logger = AppLogger.getAppLogger();	
	
	/**
	 * @param strFacade The strFacade to set.
	 */
	public void setStrFacade(STRFacade strFacade) {
		this.strFacade = strFacade;
	}
		
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return PendingClaimsForm.class.isAssignableFrom(clazz);
	}
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		PendingClaimsForm pendingClaimsForm = (PendingClaimsForm)target;
		//validateDetail(pendingClaimsForm, errors);
	}
	
	public void validateDetail(PendingClaimsForm pendingClaimsForm, Errors errors, HttpServletRequest request) {
		String rem1 = "Remarks";
		String rem2 = "Remarks2";
		if (request!=null)
		{	UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
			String partnerCode = userSession.getAgent().getPartnerCode();
			String lang = request.getLocale().getLanguage();
			String key1 = "label_partner_remark_1_"+partnerCode;
			String key2 = "label_partner_remark_2_"+partnerCode;
			rem1 = strFacade.getMessage("AMPARTNERPORTAL",lang,key1);
			rem2 = strFacade.getMessage("AMPARTNERPORTAL",lang,key2);
			if (StringUtils.isEmpty(rem1)){
				rem1 = strFacade.getMessage("AMPARTNERPORTAL",lang,"label_partner_remark_1");
			}
			if (StringUtils.isEmpty(rem2)){
				rem2 = strFacade.getMessage("AMPARTNERPORTAL",lang,"label_partner_remark_2");
			}		
		}
			
		if (NARClaimDetails.ACTION_COMPLETE.equals(pendingClaimsForm.getActiveClaim().getAction())) {
			errors.setNestedPath("activeClaim");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptNo", "required", "Receipt Ref No:This information must be supplied.");
			
			if (StringUtils.isNotEmpty(pendingClaimsForm.getActiveClaim().getReceiptNo()) && !StringUtils.isAsciiPrintable(pendingClaimsForm.getActiveClaim().getReceiptNo())) {
				errors.rejectValue("receiptNo", "invalidFormat", new Object[]{pendingClaimsForm.getActiveClaim().getReceiptNo()}, "Receipt Ref. No. contains invalid characters.");
			}
			if (StringUtils.isNotEmpty(pendingClaimsForm.getActiveClaim().getRemarks()) && !StringUtils.isAsciiPrintable(pendingClaimsForm.getActiveClaim().getRemarks())) {
				errors.rejectValue("remarks", "invalidFormat", new Object[]{rem1},"Remarks contain invalid characters.");
			}
			if (StringUtils.isNotEmpty(pendingClaimsForm.getActiveClaim().getRemarks2()) && !StringUtils.isAsciiPrintable(pendingClaimsForm.getActiveClaim().getRemarks2())) {
				errors.rejectValue("remarks2", "invalidFormat", new Object[]{rem2},"Remarks2 contain invalid characters.");
			}
			
			errors.setNestedPath("");
		}
		if (NARClaimDetails.ACTION_CANCEL.equals(pendingClaimsForm.getActiveClaim().getAction())) {
			errors.setNestedPath("activeClaim");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cancelReason", "required", "Cancel Reason:This information must be supplied.");
			
			if (StringUtils.isNotEmpty(pendingClaimsForm.getActiveClaim().getCancelReason()) && !StringUtils.isAsciiPrintable(pendingClaimsForm.getActiveClaim().getCancelReason())) {
				errors.rejectValue("cancelReason", "invalidFormat", new Object[]{pendingClaimsForm.getActiveClaim().getCancelReason()}, "Cancel Reason contains invalid characters.");
			}
			
			errors.setNestedPath("");
		}
	}
}
