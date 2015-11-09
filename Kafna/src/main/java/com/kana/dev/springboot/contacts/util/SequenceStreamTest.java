package com.kana.dev.springboot.contacts.util;

import java.io.InputStream;

import org.junit.Test;

public class SequenceStreamTest {

	@Test
	public void testGetBlob() throws Exception {
		SequenceStream seq = new SequenceStream();
		InputStream sis = seq.getMyStream();
		
		int oneByte;
		int index = 0;
		while ((oneByte = sis.read()) != -1) {
			final char c = (char) oneByte;
			System.out.write(oneByte);
			if (sis.available() < 3 ) {
				index++;
				if (index < seq.fileLocations.size()){
					sis = seq.addInputStreamToSequenceInputStream(seq.fileLocations.get(index), seq.inputStreams);
				}
			}
		}
		System.out.flush();
		

		
		//int i = sis.read();
	}

}