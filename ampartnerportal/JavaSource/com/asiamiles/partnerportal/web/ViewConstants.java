/*
 * Created on Jul 21, 2009
 *
 */
package com.asiamiles.partnerportal.web;

/**
 * Constants class for storing View names
 * @author CPPBENL
 *
 */
public class ViewConstants {

	public static final String LOGIN_FORM = "LoginForm";
	public static final String INSTANT_REDEEM_LOGIN_FORM = "instantRedeem/LoginForm";
	public static final String FORGOT_PASSWORD_FORM = "ForgotPasswordForm";
	
	public static final String AGENT_ADMIN_LIST_AGENTS = "agentAdmin/ListAgents";
	public static final String AGENT_ADMIN_CHANGE_PASSWORD_FORM = "agentAdmin/ChangePasswordForm";
	public static final String AGENT_ADMIN_UPDATE_AGENT_FORM = "agentAdmin/UpdateAgentForm";
	public static final String AGENT_ADMIN_NEW_AGENT_FORM = "agentAdmin/NewAgentForm";

	public static final String REDEMPTION_ENTER_CLAIMS = "redemption/EnterClaims";
	public static final String REDEMPTION_PROCESS_CLAIMS = "redemption/ProcessClaims";
	public static final String REDEMPTION_COMPLETE_CLAIMS = "redemption/CompleteClaims";
	public static final String REDEMPTION_ACKNOWLEDGEMENT = "redemption/Acknowledgement";
	
	public static final String INSTANT_REDEEM_QRCODE_READER = "instantRedeem/QRCodeReader";
	public static final String INSTANT_REDEEM_COLLECT_CLAIMS = "instantRedeem/CollectClaims";
	public static final String INSTANT_REDEEM_ACKNOWLEDGEMENT = "instantRedeem/Acknowledgement";
	public static final String INSTANT_REDEEM_INVALID_OFFER = "instantRedeem/InvalidOffer";
	public static final String INSTANT_REDEEM_EXPIRED_OFFER = "instantRedeem/ExpiredOffer";
	public static final String INSTANT_REDEEM_REDEEMED_OFFER = "instantRedeem/RedeemedOffer";
	public static final String INSTANT_NOT_ALLOW_OFFER = "instantRedeem/NotAllowOffer";
	public static final String INSTANT_REDEEM_ERROR = "instantRedeem/Error";
	
	public static final String INSTANT_REDEEM_PARTNER_GROUP_ADD = "instantRedeem/PartnerGroupAdd";
	public static final String INSTANT_REDEEM_GROUP_ASSIGN = "instantRedeem/GroupAssign";
	public static final String INSTANT_REDEEM_PACKAGE_GROUP_ASSIGN = "instantRedeem/PackageCodeGroupAssign";
	public static final String INSTANT_REDEEM_GROUP_ADMIN = "instantRedeem/GroupAdmin";
	public static final String INSTANT_REDEEM_REDEMPTION_PRODUCTS = "instantRedeem/RedemptionProducts";
	public static final String INSTANT_REDEEM_QRCODE_IMAGE = "instantRedeem/QrCodeImage";
	public static final String INSTANT_REDEEM_LIST_GROUP_FOR_PACKAGE = "instantRedeem/ListGroupForPackage";
	public static final String INSTANT_REDEEM_ASSIGN_GROUP_TO_PACKAGE = "instantRedeem/AssignGroupToPackage";
	
	public static final String PENDING_CLAIMS_PROCESS_CLAIMS = "pendingClaims/ProcessClaims";
	public static final String PENDING_CLAIMS_COMPLETE_COLLECTION = "pendingClaims/CompleteCollection";
	public static final String PENDING_CLAIMS_ACKNOWLEDGEMENT = "pendingClaims/Acknowledgement";
	
	public static final String BILLING_ESTABLISHMENT = "billing/Establishment";
	public static final String BILLING_DETAILS = "billing/Details";
	public static final String BILLING_CONFIRMATION = "billing/Confirmation";
	public static final String BILLING_ACKNOWLEDGEMENT = "billing/Acknowledgement";
	
	public static final String BILLING_UNBILLED_EXPORT = "billing/UnbilledExport";
	public static final String BILLING_BILLED_EXPORT = "billing/BilledExport";
	
	public static final String REPORT_REPORT = "report/Report";
	public static final String REPORT_VOID_CLAIM = "report/VoidClaim";
	public static final String REPORT_REPORT_EXPORT = "report/ReportExport";
	public static final String CLS_ADMIN_NEW_ADMIN_AGENT_FORM = "clsAdmin/NewAdminAgentForm";
    
     //CPPSEZ ADD FOR AMPARTNERPORT 20151225 START  
    public static final String VERIFY_MEMBERSHIP_FORM = "amMembership/verifyAMMembership";
    public static final String VERIFY_MEMBERSHIP_ACKNOWLEDGEMENT = "amMembership/Acknowledgement";
//  CPPSEZ ADD FOR AMPARTNERPORT 20151225 END  
	/**
	 * Private constructor to prevent accidental instantiation
	 */
	private ViewConstants() {}
	
	
}
