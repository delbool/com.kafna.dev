package com.kana.dev.springboot.contacts.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class SequenceInputStreamTest2 {

	public static void main(final String[] args) throws Exception {

		final List<String> fileLocations = new ArrayList<String>();
		fileLocations.add("C:/temp/data/testfile1.txt");
		fileLocations.add("C:/temp/data/testfile2.txt");
		fileLocations.add("C:/temp/data/testfile3.txt");
		
		
//		final FileInputStream fis1 = new FileInputStream("C:/temp/data/testfile1.txt");
//		final FileInputStream fis2 = new FileInputStream("C:/temp/data/testfile2.txt");
//		final FileInputStream fis3 = new FileInputStream("C:/temp/data/testfile3.txt");

		final Vector<InputStream> inputStreams = new Vector<InputStream>();
		for (final String fileLocation : fileLocations){
			inputStreams.add(new FileInputStream(fileLocation));
		}
		
		
//		inputStreams.add(new FileInputStream(fileLocations.get(0)));
//		inputStreams.add(fis2);
//		inputStreams.add(fis3);


		int oneByte;
		for (int i = 0; i < inputStreams.size(); i++) {
			final Enumeration<InputStream> enu = inputStreams.elements();
			final SequenceInputStream sis = new SequenceInputStream(enu);
			while ((oneByte = sis.read()) != -1) {
				System.out.write(oneByte);
			}
			inputStreams.add(new FileInputStream(fileLocations.get(i)));
		}

		System.out.flush();
	}
}