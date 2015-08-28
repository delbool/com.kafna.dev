package com.kana.dev.springboot.contacts.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class SequenceInputStreamTest23 {

	public static void main(final String[] args) throws Exception {

		final List<String> fileLocations = new ArrayList<String>();
		fileLocations.add("C:/temp/data/testfile1.txt");
		fileLocations.add("C:/temp/data/testfile2.txt");
		fileLocations.add("C:/temp/data/testfile3.txt");
		
		final Vector<InputStream> inputStreams = new Vector<InputStream>();
		inputStreams.add(new FileInputStream(fileLocations.get(0)));
		final Enumeration<InputStream> enu = inputStreams.elements();
		SequenceInputStream sis = new SequenceInputStream(enu);

		int oneByte;
		int index = 0;
		while ((oneByte = sis.read()) != -1) {
			final char c = (char) oneByte;
			System.out.write(oneByte);
			if (sis.available() < 3 ) {
				index++;
				if (index < fileLocations.size()){
					sis = addInputStreamToSequenceInputStream(fileLocations.get(index), inputStreams);
				}
			}
		}
		System.out.flush();
	}

	private static SequenceInputStream addInputStreamToSequenceInputStream(final String fileLocation,
			final Vector<InputStream> inputStreams) throws FileNotFoundException {		
		inputStreams.add(new FileInputStream(fileLocation));
		final Enumeration<InputStream> enu = inputStreams.elements();
		final SequenceInputStream sis = new SequenceInputStream(enu);
		
		return sis;
	}
	
	public InputStream getMyStream() {
		return new InputStream() {

			@Override
			public int read() throws IOException {
				return 0;
			}
		};
	}

}