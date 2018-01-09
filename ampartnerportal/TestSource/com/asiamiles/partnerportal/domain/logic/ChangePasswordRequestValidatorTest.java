/*
 * Created on Jun 30, 2009
 *
 */
package com.asiamiles.partnerportal.domain.logic;

import java.util.Iterator;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.asiamiles.partnerportal.domain.ChangePasswordRequest;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class ChangePasswordRequestValidatorTest extends TestCase {

	private ChangePasswordRequestValidator validator;
	private Errors errors;
	private ChangePasswordRequest form;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = new ChangePasswordRequestValidator();
		form = new ChangePasswordRequest();
		errors = new BindException(form, "changePasswordForm");
	}
	
	public void testValidateNoInput() {
		validator.validate(form, errors);
		assertEquals(3, errors.getErrorCount());
		
		for (Iterator it = errors.getAllErrors().iterator(); it.hasNext();) {
			ObjectError error = (ObjectError)it.next();
			assertEquals("required", error.getCode()); 
		}
	}
	
	public void testValidateInvalidInput() {
		form.setOldPassword("00000000");
		form.setNewPassword("abc&&123");
		form.setConfirmPassword(form.getNewPassword());
		
		validator.validate(form, errors);
		
		assertEquals(1, errors.getErrorCount());
		ObjectError error = (ObjectError)errors.getAllErrors().get(0);
		assertEquals("invalidFormat", error.getCode());
		
	}
	
	public void testValidateTooSmallPassword() {
		form.setOldPassword("00000000");
		form.setNewPassword("abcde");
		form.setConfirmPassword(form.getNewPassword());
		
		validator.validate(form, errors);
		
		assertEquals(1, errors.getErrorCount());
		ObjectError error = (ObjectError)errors.getAllErrors().get(0);
		assertEquals("invalidFormat", error.getCode());
	}
	
	public void testValidateTooBigPassword() {
		form.setOldPassword("00000000");
		form.setNewPassword("abcdef1234");
		form.setConfirmPassword(form.getNewPassword());
		
		validator.validate(form, errors);
		
		assertEquals(1, errors.getErrorCount());
		ObjectError error = (ObjectError)errors.getAllErrors().get(0);
		assertEquals("invalidFormat", error.getCode());
	}

	public void testValidateWrongConfirmPassword() {
		form.setOldPassword("00000000");
		form.setNewPassword("12345678");
		form.setConfirmPassword("98765432");
		
		validator.validate(form, errors);
		
		assertEquals(1, errors.getErrorCount());
		ObjectError error = (ObjectError)errors.getAllErrors().get(0);
		assertEquals("error_confirmPasswordMismatch", error.getCode());
	}
	
	public void testValidatePass() {
		form.setOldPassword("00000000");
		form.setNewPassword("12345678");
		form.setConfirmPassword("12345678");
		validator.validate(form, errors);
		assertEquals(0, errors.getErrorCount());

		form.setNewPassword("abcdefgh");
		form.setConfirmPassword("abcdefgh");
		validator.validate(form, errors);
		assertEquals(0, errors.getErrorCount());


		form.setNewPassword("abcd12");
		form.setConfirmPassword("abcd12");
		validator.validate(form, errors);
		assertEquals(0, errors.getErrorCount());

	}
}
