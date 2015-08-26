package com.kana.dev.springboot.contacts.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

public class SequenceInputStreamTest {

	public static void main(final String[] args) throws Exception {

//		final FileInputStream fis1 = new FileInputStream("C:/Downloads/Eclipse/testfile1.txt");
//		final FileInputStream fis2 = new FileInputStream("C:/Downloads/Eclipse/testfile2.txt");
//		final FileInputStream fis3 = new FileInputStream("C:/Downloads/Eclipse/testfile3.txt");
		
		final FileInputStream fis1 = new FileInputStream("C:/temp/data/testfile1.txt");
		final FileInputStream fis2 = new FileInputStream("C:/temp/data/testfile2.txt");
		final FileInputStream fis3 = new FileInputStream("C:/temp/data/testfile3.txt");
		
		final Vector<InputStream> inputStreams = new Vector<InputStream>();
		inputStreams.add(fis1);
		inputStreams.add(fis2);
		inputStreams.add(fis3);

		final Enumeration<InputStream> enu = inputStreams.elements();
		final SequenceInputStream sis = new SequenceInputStream(enu);

		int oneByte;
		while ((oneByte = sis.read()) != -1) {
			System.out.write(oneByte);
		}
		System.out.flush();
	}
}