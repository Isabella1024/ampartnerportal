/*
 * Created on Jul 28, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import org.springframework.core.io.ClassPathResource;

import junit.framework.TestCase;

/**
 * @author CPPBENL
 *
 */
public class CryptoHelperTest extends TestCase {

	CryptoHelper helper;
	
	protected void setUp() throws Exception {
		super.setUp();
		helper = new CryptoHelper();
		helper.setCryptoKeyPath(new ClassPathResource("/webclsWAS6.ser"));
	}
	
	/*
	 * Class under test for byte[] encrypt(byte[])
	 */
	public void testEncryptbyteArray() {
		byte[] plaintext = new byte[]{1,2,3,4,5,6,7,8};
		byte[] ciphertext = helper.encrypt(plaintext);
		
		assertNotNull(ciphertext);
		assertTrue(ciphertext.length > 0);
	}

	/*
	 * Class under test for String encrypt(String)
	 */
	public void testEncryptString() {
		String plaintext = "Hello World";
		String ciphertext = helper.encrypt(plaintext);
		
		assertNotNull("ciphertext is null",ciphertext);
		System.out.println("CipherText: " + ciphertext);
	}

	/*
	 * Class under test for byte[] decrypt(byte[])
	 */
	public void testDecryptbyteArray() {
		byte[] plaintext = new byte[]{1,2,3,4,5,6,7,8};
		byte[] ciphertext = helper.encrypt(plaintext);
		
		assertNotNull(ciphertext);
		assertTrue(ciphertext.length > 0);
		
		byte[] decrypted = helper.decrypt(ciphertext);
		assertEquals(plaintext.length, decrypted.length);
		for (int i = 0 ; i < plaintext.length; i++) {
			assertEquals(plaintext[i], decrypted[i]);
		}
	}

	/*
	 * Class under test for String decrypt(String)
	 */
	public void testDecryptString() {
		String plaintext = "Hello World";
		String ciphertext = helper.encrypt(plaintext);
		
		assertNotNull("ciphertext is null",ciphertext);
		
		String decrypted = helper.decrypt(ciphertext);
		assertEquals(plaintext, decrypted);
	}

}
