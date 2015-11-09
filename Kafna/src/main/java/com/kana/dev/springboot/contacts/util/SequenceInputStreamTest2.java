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
		

		final Vector<InputStream> inputStreams = new Vector<InputStream>();
		for (final String fileLocation : fileLocations){
			inputStreams.add(new FileInputStream(fileLocation));
		}

		SequenceInputStream sis = null;
		int oneByte;
		for (int i = 0; i < inputStreams.size(); i++) {
			final Enumeration<InputStream> enu = inputStreams.elements();
			sis = new SequenceInputStream(enu);
			boolean nextAdded = false;
			while ((oneByte = sis.read()) != -1) {
				int available = sis.available();
				System.out.println("Avialble: " + available);
				if (available < 2 && !nextAdded){
					inputStreams.add(new FileInputStream(fileLocations.get(i)));
					nextAdded = true;
				}
				System.out.write(oneByte);
			}
			
		}

		System.out.flush();
	}
}