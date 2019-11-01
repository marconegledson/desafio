package br.com.desafio.utilities;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class Criptografia {
	
	private static final String RSA = "RSA";
	private KeyPair keyPair;
	
	public Criptografia() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
		generator.initialize(2048);
		this.keyPair = generator.generateKeyPair();
	}
	
	public String encrypt(String string) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return new String(cipher.doFinal(string.getBytes()));
    }
	
	public String decrypt(String string) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return new String(cipher.doFinal(string.getBytes()));
    }

}
