/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.asiamiles.partnerportal.domain.NARClaimDetails;
import com.asiamiles.partnerportal.domain.PaperlessClaim;
import com.asiamiles.partnerportal.domain.PaperlessRedemptionForm;
import com.asiamiles.partnerportal.domain.UserSession;
import com.asiamiles.partnerportal.str.STRFacade;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.utility.Logger;

/**
 * Validator for Paperless Redemption Form object
 * 
 * @author CPPBENL
 *
 */
public class PaperlessRedemptionFormValidator implements Validator {
	private STRFacade strFacade;
	private Logger logger = AppLogger.getAppLogger();
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return PaperlessRedemptionForm.class.isAssignableFrom(clazz);
	}
	
	/**
	 * @param strFacade The strFacade to set.
	 */
	public void setStrFacade(STRFacade strFacade) {
		this.strFacade = strFacade;
	}	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		PaperlessRedemptionForm redemptionForm = (PaperlessRedemptionForm)target;
		validateMemberInfo(redemptionForm, errors);
		validateClaims(redemptionForm, errors);
	}

	public void validateRefAndReason(PaperlessRedemptionForm redemptionForm, Errors errors, HttpServletRequest request) {
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
		
		errors.setNestedPath("");
		int num=0;
		for (Iterator it = redemptionForm.getNARDetails().iterator(); it.hasNext();) {
			NARClaimDetails claimDetails = (NARClaimDetails)it.next();
			PaperlessClaim claim = claimDetails.getPaperlessClaim();
			
			if (claimDetails.isToBeProcessed()){
				errors.setNestedPath("NARDetails[" + num + "]");
				num++;
			}

			if (claimDetails.isToBeProcessed() && claimDetails.getAction().equals(NARClaimDetails.ACTION_COMPLETE)) {

				if (StringUtils.isNotEmpty(claimDetails.getReceiptNo()) && !StringUtils.isAsciiPrintable(claimDetails.getReceiptNo())) {
					errors.rejectValue("receiptNo", "invalidFormat", new Integer[]{new Integer(num)}, "Receipt Ref. No. contains invalid characters.");
				}

				if (StringUtils.isNotEmpty(claimDetails.getRemarks()) && !StringUtils.isAsciiPrintable(claimDetails.getRemarks())) {
					errors.rejectValue("remarks", "invalidFormat", new Object[]{new Integer(num), rem1}, "Remarks contain invalid characters.");
				}
				if (StringUtils.isNotEmpty(claimDetails.getRemarks2()) && !StringUtils.isAsciiPrintable(claimDetails.getRemarks2())) {
					errors.rejectValue("remarks2", "invalidFormat", new Object[]{new Integer(num), rem2}, "Remarks2 contain invalid characters.");
				}
			}
			if (claimDetails.isToBeProcessed() && claimDetails.getAction().equals(NARClaimDetails.ACTION_CANCEL)) { 
				if (StringUtils.isEmpty(claimDetails.getCancelReason())) {
					errors.rejectValue("cancelReason", "required", new Integer[]{new Integer(num)}, "Cancel reason must be supplied.");
				}				
				
				if (StringUtils.isNotEmpty(claimDetails.getCancelReason()) && !StringUtils.isAsciiPrintable(claimDetails.getCancelReason())) {
					errors.rejectValue("cancelReason", "invalidFormat", new Integer[]{new Integer(num)}, "Cancel reason contain invalid characters.");
				}
			}			
			
		}
		
		errors.setNestedPath("");
	}
	
	public void validateMemberInfo(PaperlessRedemptionForm redemptionForm, Errors errors) {
		errors.setNestedPath("");
		if (redemptionForm.getMemberId() == null || StringUtils.isEmpty(redemptionForm.getMemberId())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberId", "required", "Member ID required.");
		}
		//CPPYOW DEL for iRedeem 20120314
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberEmbossedName", "required", "Member Embossed Name required.");
		if (!StringUtils.isEmpty(redemptionForm.getMemberId()) && !StringUtils.isNumeric(redemptionForm.getMemberId())) {
			errors.rejectValue("memberId", "invalidFormat", "Member ID: Contains invalid characters. Please use valid characters from 0 to 9.");
		}
		//CPPYOW DEL for iRedeem 20120314
//		if (!StringUtils.isEmpty(redemptionForm.getMemberEmbossedName()) && !StringUtils.isAsciiPrintable(redemptionForm.getMemberEmbossedName())) {
//			errors.rejectValue("memberEmbossedName", "invalidFormat", "Member Embossed Name: Contains invalid characters. Please use valid alphanumeric characters.");
//		}
		errors.setNestedPath("");
	}
	
	public void validateClaims(PaperlessRedemptionForm redemptionForm, Errors errors) {
		int populatedClaims = 0;
		String prevPath = errors.getNestedPath();
		boolean[] rowIsFilled = new boolean[5];
		boolean emptyRowFound = false;
		
		for (int i = 0; i < redemptionForm.getClaims().size();i++) {
			PaperlessClaim claim = (PaperlessClaim)redemptionForm.getClaims().get(i);
			
			errors.setNestedPath("claims["+i+"]");
			if ( StringUtils.isNotBlank(claim.getClaimNumber()) || StringUtils.isNotBlank(claim.getSecurityCode())) {
				populatedClaims++;
				
				// check if claim number exists; and check format if it exists
				if (StringUtils.isBlank(claim.getClaimNumber())){
					errors.rejectValue("claimNumber", "required", new Integer[]{new Integer(populatedClaims)}, "Claim Number Required");
				}
				else if (!StringUtils.isNumeric(claim.getClaimNumber())){
					errors.rejectValue("claimNumber","invalidFormat",new Integer[]{new Integer(populatedClaims)},"One of the claims is in invalid format");
				}
				//check if security code exists; and check format if it exists
				if (StringUtils.isBlank(claim.getSecurityCode())) {
					errors.rejectValue("securityCode", "required", new Integer[]{new Integer(populatedClaims)}, "Security Code Required");
				} else if (!StringUtils.isNumeric(claim.getSecurityCode()) || (claim.getSecurityCode().length()!=11 && claim.getSecurityCode().length()!=12)) {
					errors.rejectValue("securityCode", "invalidFormat", new Integer[]{new Integer(populatedClaims)}, "One of the claims has security code in invalid format"); 
				}
				rowIsFilled[i] = true;
			}
			else {
				rowIsFilled[i] = false;
			}
			errors.setNestedPath("");
		}
		
		if(!rowIsFilled[0])
		{	errors.reject("invalidSequence", "First row is mandatory. Please input first row.");
		}
		else
		{	//check if there is any empty row in between filled rows.
			//for example: User inputs 4 claims. Check if the first 4 rows has any empty row 
			for (int i = 0 ; i < populatedClaims; i++)
			{	if (!rowIsFilled[i])
				{	emptyRowFound = true;	
				}
			}
		}
		if (emptyRowFound)
		{	errors.reject("invalidSequence", "Invalid input sequence. Please fill in the claims from top to bottom.");
		}
		errors.setNestedPath(prevPath);
		if (populatedClaims == 0) {
			errors.reject("nothingToVerify", "No Claims to Verify");
		}
	}
	
	public void validateCheckbox(PaperlessRedemptionForm redemptionForm, Errors errors) {
		boolean noOpenClaims = true;
		boolean noCheckedClaims = true;
		for (Iterator it = redemptionForm.getNARDetails().iterator(); it.hasNext();) {
			NARClaimDetails claimDetails = (NARClaimDetails) it.next();
			if (claimDetails.getCompletionTime() == null && claimDetails.getCollectionTime() == null && claimDetails.getClaimStatusCode().equals("00")) {
				noOpenClaims = false;
			}
		}
		
		if (noOpenClaims) {
			errors.reject("noOpenClaims", "There are no open claims.");
		} else {
			for (Iterator it = redemptionForm.getNARDetails().iterator(); it.hasNext();) {
				NARClaimDetails claimDetails = (NARClaimDetails) it.next();
				if (claimDetails.isToBeProcessed()) {
					noCheckedClaims = false;
				}
			}
			if (noCheckedClaims) {
				errors.reject("nothingToProcess", "Please select an open claim to proceed.");
			}
		}
	}
}
