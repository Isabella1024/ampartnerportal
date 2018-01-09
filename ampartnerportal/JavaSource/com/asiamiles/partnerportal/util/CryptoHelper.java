/*
 * Created on Jul 28, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.core.io.Resource;

import com.asiamiles.partnerportal.SystemException;

/**
 * TODO add Javadoc
 * @author CPPBENL
 *
 */
public class CryptoHelper {

	private Resource cryptoKeyPath;
	
	/**
	 * @param cryptoKeyPath The cryptoKeyPath to set.
	 */
	public void setCryptoKeyPath(Resource cryptoKeyPath) {
		this.cryptoKeyPath = cryptoKeyPath;
	}
	
	/**
	 * Attempts to retrieve the secret key from the <code>cryptoKeyPath</code> resource.
	 * @return the secret key
	 * @throws SystemException if an IO Exception occurs or the 
	 */
	protected SecretKey getSecretKey(){
		if (cryptoKeyPath == null) {
			
		}
		try {
			ObjectInputStream inStream = new ObjectInputStream(cryptoKeyPath.getInputStream());
			SecretKey key = (SecretKey)inStream.readObject();
			return key;
		} catch (IOException e) {
			throw new SystemException(SystemException.IOEXCEPTION, "Cannot load decryption key: " + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new SystemException(SystemException.SERVERCONFIG, "Cannot load class: " + e.getMessage(), e);
		}
		
	}
	
	public byte[] encrypt(byte[] plainText) {
		SecretKey key = getSecretKey();

		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return cipher.doFinal(plainText);
		} catch (Exception e) {
			throw new SystemException(SystemException.CRYPTO_FAILURE, "Cannot encrypt plaintext: " + e.getMessage(), e);
		}
		
	}

	public String encrypt(String plaintext) {
		char[] ciphertext = Hex.encodeHex(encrypt(plaintext.getBytes()));
		return new String(ciphertext);
	}

	
	public byte[] decrypt(byte[] cipherText) {
		SecretKey key = getSecretKey();

		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			return cipher.doFinal(cipherText);
		} catch (Exception e) {
			throw new SystemException(SystemException.CRYPTO_FAILURE, "Cannot decrypt ciphertext: " + e.getMessage(), e);
		}
	}
	
	public String decrypt(String hexString) {
		try {
			byte[] plaintext = decrypt(Hex.decodeHex(hexString.toCharArray()));
			return new String(plaintext);
		} catch (DecoderException e) {
			throw new SystemException(SystemException.CRYPTO_FAILURE, "Cannot decrypt ciphertext: " + e.getMessage(), e);
		}
	}
}
