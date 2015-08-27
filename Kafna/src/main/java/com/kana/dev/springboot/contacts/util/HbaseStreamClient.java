package com.kana.dev.springboot.contacts.util;

import org.junit.Test;

public class HbaseStreamClient {

	@Test
	public void testStreamFromHbase() throws Exception {
		final HBaseHandlerStream stream = new HBaseHandlerStream(new HBaseStream());
		
		int oneByte;
		while ((oneByte = stream.read()) != -1) {
			System.out.write(oneByte);
		}
		
		System.out.flush();
		
	}

}