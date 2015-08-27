package com.kana.dev.springboot.contacts.util;

import org.junit.Test;

public class HbaseStreamClient {

	@Test
	public void testStreamFromHbase() throws Exception {
		final HBaseHandlerStream stream = new HBaseHandlerStream(new HBaseStream());
		
		int bytesRead;
		final byte[] buffer = new byte[25];
		while ((bytesRead = stream.read()) != -1) {
			System.out.write(bytesRead);
		}
		
		System.out.flush();
		
	}

}