package com.kana.dev.springboot.contacts.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

public class SequenceInputStreamTest {

	public static void main(String[] args) throws Exception {

		FileInputStream fis1 = new FileInputStream("C:/Downloads/Eclipse/testfile1.txt");
		FileInputStream fis2 = new FileInputStream("C:/Downloads/Eclipse/testfile2.txt");
		FileInputStream fis3 = new FileInputStream("C:/Downloads/Eclipse/testfile3.txt");

		Vector<InputStream> inputStreams = new Vector<InputStream>();
		inputStreams.add(fis1);
		inputStreams.add(fis2);
		inputStreams.add(fis3);

		Enumeration<InputStream> enu = inputStreams.elements();
		SequenceInputStream sis = new SequenceInputStream(enu);

		int oneByte;
		while ((oneByte = sis.read()) != -1) {
			System.out.write(oneByte);
		}
		System.out.flush();
	}
}