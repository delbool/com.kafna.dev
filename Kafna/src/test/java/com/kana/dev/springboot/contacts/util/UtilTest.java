package com.kana.dev.springboot.contacts.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class UtilTest {

	@Test
	public void ioTest() throws IOException {

		long start = System.currentTimeMillis();
		IOUtil3.readBytesFromFile2("/downloads/eclipse/e.zip");
//		IOUtil2.writeBytesToFile(new File("/downloads/eclipse/e2.zip"), bytes);
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));
		
		start = System.currentTimeMillis();
		IOUtil3.readBytesFromFile("/downloads/eclipse/e.zip");
//		IOUtil2.writeBytesToFile(new File("/downloads/eclipse/e2.zip"), bytes);
		end = System.currentTimeMillis();
		System.out.println("Time taken: " + (end - start));
		
	}

	@Test
	public void testJava7IoTry()  {
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt"))) {

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
