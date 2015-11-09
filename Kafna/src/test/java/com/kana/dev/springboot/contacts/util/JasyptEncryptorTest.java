package com.kana.dev.springboot.contacts.util;

import java.util.Arrays;
import java.util.List;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.Test;

public class JasyptEncryptorTest {

	@Test
	public void testEncyptDecryptUsingJasypt(){
		
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("mister123");
		
		String password = "MyPass123";  //password to be protected;
		String encryptedPassword = encryptor.encrypt(password);
//		String encryptedPassword = "tjK/hooP60fjsVc+tD7EGdNenGqRkOPt";
//		String encryptedPassword = "dpZZdsUJ7nbfSekw0q9IpteaOqJemghq";
//		String encryptedPassword = "/0GvdZiFMMMG3vSjGY4PHrTulnqSSo4s";
//		String encryptedPassword = "lt1adwDaIFR5YQOji2lhDQcvFbwzMPVL";
		String decryptedPassword = encryptor.decrypt(encryptedPassword);
		
		System.out.println("Plain password before Encyption: " + password);
		System.out.println("Encrypted password Encyption: " + encryptedPassword);
		System.out.println("Decrypted password: " + decryptedPassword);
		
		Assert.assertNotEquals("MyPass123", encryptedPassword);
		Assert.assertEquals("MyPass123", decryptedPassword);
		
		List<String> encryptedPasswords = Arrays.asList("lt1adwDaIFR5YQOji2lhDQcvFbwzMPVL", "/0GvdZiFMMMG3vSjGY4PHrTulnqSSo4s", "tjK/hooP60fjsVc+tD7EGdNenGqRkOPt", "dpZZdsUJ7nbfSekw0q9IpteaOqJemghq");
		for (String ep : encryptedPasswords){
			String plainPass = encryptor.decrypt(ep);
			Assert.assertEquals(password, plainPass);
			System.out.println("Encrypted: " + ep +  "\tPlain : "  + plainPass);
		}
		
		
		// -----  A diferent Approach -------------
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPasswordEnvName("JASYPT_PASSWORD");
		
		StandardPBEStringEncryptor se = new StandardPBEStringEncryptor();
		se.setConfig(config);
	}
}
