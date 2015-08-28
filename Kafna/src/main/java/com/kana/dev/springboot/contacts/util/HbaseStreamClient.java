package com.kana.dev.springboot.contacts.util;

import org.junit.Test;

public class HbaseStreamClient {

	@Test
	public void testStreamFromHbase() throws Exception {
		final HBaseHandlerStream stream = new HBaseHandlerStream(new HBaseStream());
		
		int bytesRead;
		while ((bytesRead = stream.read()) != -1) {
			System.out.write(bytesRead);
		}
		
		System.out.flush();
		
	}

	@Test
	public void testStreamFromHbaseWithBufer() throws Exception {
		final HBaseHandlerStream stream = new HBaseHandlerStream(new HBaseStream());
		
		int bytesRead;
		byte[] buffer = new byte[100];
		while ((bytesRead = stream.read(buffer)) != -1) {
			System.out.write(buffer);
		}
		
		System.out.flush();
		System.out.println("WOW");
		
	}

}