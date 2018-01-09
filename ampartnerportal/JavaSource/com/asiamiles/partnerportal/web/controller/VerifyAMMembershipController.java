package com.asiamiles.partnerportal.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.asiamiles.partnerportal.SiteProperties;
import com.asiamiles.partnerportal.cls.CLSException;
import com.asiamiles.partnerportal.cls.CLSFacade;
import com.asiamiles.partnerportal.cls.messages.MembershipProfileRetrievalServletResponse;
import com.asiamiles.partnerportal.domain.Membership;
import com.asiamiles.partnerportal.util.AppLogger;
import com.asiamiles.partnerportal.util.URLHelper;
import com.asiamiles.partnerportal.web.ViewConstants;
import com.cathaypacific.utility.Logger;

/**
 *creating VerifyAMMembershipController .
 * @author CPPSEZ
 *
 */

public class VerifyAMMembershipController extends  SimpleFormController  {
	

    private static final String  FULLNAMECHECK = "F";
    private static final String  SPECIFICNAMECHECK = "S";
    private static final String  NEEDSWAPNAME = "NeedSwapName";
    String firstNameChar;
    String lastNameChar;
    
    private CLSFacade clsFacade;
    private Logger logger = AppLogger.getAppLogger();
    private SiteProperties siteProperties;  
    public VerifyAMMembershipController() {
        setCommandName("membership");
    }
    
    /**
     * @param clsFacade The clsFacade to set.
     */
    public void setClsFacade(CLSFacade clsFacade) {
        this.clsFacade = clsFacade;
    }
    
    /**
     * @param siteProperties The siteProperties to set.
     */
    public void setSiteProperties(SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }       

    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Map model = new HashMap();
        Membership membership = (Membership)command;
        boolean flag = false;
        String firstNameChar = membership.getFirstNameChar();
        String lastNameChar = membership.getLastNameChar();
            
        MembershipProfileRetrievalServletResponse respamar = new MembershipProfileRetrievalServletResponse();
        logger.info("VerifyAMMembershipController>formBackingObject  membership:"+ membership.toString());
        try {
//            respamar.setFirstName("asdfghjkljkiut");
//            respamar.setLastName("asdfd");
//            respamar.setMemberID("123456789");
            respamar = clsFacade.retrieveMembership(membership.getMemberId());
            respamar.setFirstName(respamar.getFirstName().toUpperCase());
            respamar.setLastName(respamar.getLastName().toUpperCase());
            membership = perfectNameChar(membership);
            logger.info("perfectedFirstNameChar:"+membership.getFirstNameChar());
            logger.info("perfectedLastNameChar:"+membership.getLastNameChar());
            if(SPECIFICNAMECHECK.equals(membership.getCheckingMethod())){
                if(NEEDSWAPNAME.equals(membership.getIsSwapName())){
                	if(respamar.getFirstName().length() < Integer.parseInt(membership.getLastNameChar().trim()) || respamar.getLastName().length() < Integer.parseInt(membership.getFirstNameChar().trim())){
                		flag = false;
                	}
                	else if((respamar.getLastName().substring(0,Integer.parseInt(membership.getFirstNameChar().trim()))).equals(membership.getFirstName().substring(0,Integer.parseInt(membership.getFirstNameChar().trim())).toUpperCase())&&
                        (respamar.getFirstName().substring(0,Integer.parseInt(membership.getLastNameChar().trim()))).equals(membership.getLastName().substring(0,Integer.parseInt(membership.getLastNameChar().trim())).toUpperCase())    
                    ){
                        flag=true;
                 //       logger.info("flag=true");
                    }
                }else{
                	if(respamar.getFirstName().length() < Integer.parseInt(membership.getFirstNameChar().trim()) || respamar.getLastName().length()<Integer.parseInt(membership.getLastNameChar().trim())){
                		flag=false;
                	}
                	else if((respamar.getFirstName().substring(0,Integer.parseInt(membership.getFirstNameChar().trim()))).equals(membership.getFirstName().substring(0,Integer.parseInt(membership.getFirstNameChar().trim())).toUpperCase())&&
                        (respamar.getLastName().substring(0,Integer.parseInt(membership.getLastNameChar().trim()))).equals(membership.getLastName().substring(0,Integer.parseInt(membership.getLastNameChar().trim())).toUpperCase())    
                    ){
                            flag=true;
                    }
                }
            }else if(FULLNAMECHECK.equals(membership.getCheckingMethod())){
                if(membership.getFirstName().toUpperCase().equals(respamar.getFirstName()) && membership.getLastName().toUpperCase().equals(respamar.getLastName())){
                    flag=true;
                }
            }
            membership.setFirstNameChar(firstNameChar);
            membership.setLastNameChar(lastNameChar);
            logger.info("flag:"+flag);
            if(flag==true){
            	model.put("result", "name_match");
            	model.put(getCommandName(), membership);
            }else{
              //  logger.info(" model.put");
            	model.put("result", "name_mismatch");
//                model.put("errormessage","name_mismatch");
//                model.put("respamar", respamar);
//                membership.setFirstNameChar(firstNameChar);
//                membership.setLastNameChar(lastNameChar);
                model.put(getCommandName(), membership);
            }
            return new ModelAndView(ViewConstants.VERIFY_MEMBERSHIP_FORM,model);
        }catch(CLSException e){
            logger.info("VerifyAMMembershipController>CLSException "+  e.getErrorMessageCode());
            if("16".equals(e.getErrorCode())){
            	 model.put("clsexception","member_not_enrolled");
            }else if("2134".equals(e.getErrorCode())){
            	model.put("clsexception","member_inactive");
            }else if("6204".equals(e.getErrorCode())){
            	model.put("clsexception","invalid_memberId");
            }else{
            	model.put("clsexception", e.getErrorMessageCode());
//            	model.put("clsexception","system error!");
            }
//            model.put("clsexception", e.getErrorMessageCode());
            model.put(getCommandName(), membership);
            //errors.reject(e.getErrorMessageCode(), e.getMessage());
            return new ModelAndView(ViewConstants.VERIFY_MEMBERSHIP_FORM, model);
        }catch(Exception ex){
            logger.info("VerifyAMMembershipController>exception "+ ex.getMessage());
            model.put("exception", ex);
            model.put(getCommandName(), membership);
            return new ModelAndView(ViewConstants.VERIFY_MEMBERSHIP_FORM,model);
        }
        
    }
        
//    private Membership perftctNameChar(MembershipProfileRetrievalServletResponse respamar, Membership membership) {
//    	if(Integer.parseInt(membership.getFirstNameChar())>membership.getFirstName().length()){
//    		firstNameChar = membership.getFirstName();
//    	}
//    	if(Integer.parseInt(membership.getLastNameChar())>membership.getLastName().length()){
//    		lastNameChar = membership.getLastName();
//    	}
//        if(NEEDSWAPNAME.equals(membership.getIsSwapName())){
//            if(respamar.getFirstName().length()<Integer.parseInt(membership.getFirstNameChar())||membership.getLastName().length()<Integer.parseInt(membership.getFirstNameChar())){
//                if(respamar.getFirstName().length()>membership.getLastName().length()){
//                    membership.setFirstNameChar(String.valueOf(respamar.getFirstName().length()));
//                    membership.setFirstName(StringUtils.rightPad(membership.getLastName(), respamar.getFirstName().length(), " "));
//                }else{
//                    membership.setFirstNameChar(String.valueOf(respamar.getFirstName().length()));
//                }
//            }
//            if(respamar.getLastName().length()<Integer.parseInt(membership.getLastNameChar())||membership.getFirstName().length()<Integer.parseInt(membership.getLastNameChar())){
//                if(respamar.getLastName().length()>membership.getFirstName().length()){
//                    membership.setLastNameChar(String.valueOf(respamar.getLastName().length()));
//                    membership.setLastName(StringUtils.rightPad(membership.getFirstName(), respamar.getLastName().length(), " "));
//                }else{
//                    membership.setLastNameChar(String.valueOf(respamar.getLastName().length()));
//                }
//            }
//            
//            
//        }else {
//            if(respamar.getFirstName().length()<Integer.parseInt(membership.getFirstNameChar())||membership.getFirstName().length()<Integer.parseInt(membership.getFirstNameChar())){
//                if(respamar.getFirstName().length()>membership.getFirstName().length()){
//                	//cppjox update start
//                   // membership.setFirstNameChar(String.valueOf(respamar.getFirstName().length()));
//                	  membership.setFirstNameChar(String.valueOf(membership.getFirstName().length()));
//                	//cppjox update end  
//                    membership.setFirstName(StringUtils.rightPad(membership.getFirstName(), respamar.getFirstName().length(), " "));
//                }else{
//                    membership.setFirstNameChar(String.valueOf(respamar.getFirstName().length()));
//                }
//            }
//            if(respamar.getLastName().length()<Integer.parseInt(membership.getLastNameChar())||membership.getLastName().length()<Integer.parseInt(membership.getLastNameChar())){
//                if(respamar.getLastName().length()>membership.getLastName().length()){
//                	//cppjox uodate start 
//                    //membership.setLastNameChar(String.valueOf(respamar.getLastName().length()));
//                	  membership.setLastNameChar(String.valueOf(membership.getLastName().length()));
//                	//cppjox update end
//                    membership.setLastName(StringUtils.rightPad(membership.getLastName(), respamar.getLastName().length(), " "));
//                }else{
//                    membership.setLastNameChar(String.valueOf(respamar.getLastName().length()));
//                }
//            }
//            
//        }
//        
//        return membership;
//    }

    private Membership perfectNameChar(Membership membership) {
//    	 if(!NEEDSWAPNAME.equals(membership.getIsSwapName())){
    		 if(membership.getFirstName().length() < Integer.parseInt(membership.getFirstNameChar())){
    			 membership.setFirstNameChar(String.valueOf(membership.getFirstName().length()));
    		 }
    		 if(membership.getLastName().length() < Integer.parseInt(membership.getLastNameChar())){
    			 membership.setLastNameChar(String.valueOf(membership.getLastName().length()));
    		 }
//    	 }else{
//    		 if(membership.getLastName().length() < Integer.parseInt(membership.getFirstNameChar())){
//    			 membership.setFirstNameChar(String.valueOf(membership.getLastName().length()));
//    		 }
//    		 if(membership.getFirstName().length() < Integer.parseInt(membership.getLastNameChar())){
//    			 membership.setLastNameChar(String.valueOf(membership.getFirstName().length()));
//    		 }
//    	 }
    	return membership;
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception{
        logger.info("VerifyAMMembershipController>formBackingObject");
        Membership membership = new Membership();
        membership.setCheckingMethod("S");
        membership.setFirstNameChar("4");
        membership.setLastNameChar("12");
        return membership;
    }


}