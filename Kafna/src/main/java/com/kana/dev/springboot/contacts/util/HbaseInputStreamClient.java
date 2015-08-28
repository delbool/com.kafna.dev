package com.kana.dev.springboot.contacts.util;

import org.junit.Test;

public class HbaseInputStreamClient {

	@Test
	public void testStreamFromHbase() throws Exception {
		final HBaseInputStream stream = new HBaseInputStream(new HBaseStreamService());
		
		int bytesRead;
		while ((bytesRead = stream.read()) != -1) {
			System.out.write(bytesRead);
		}
		
		System.out.flush();
		
	}

	@Test
	public void testStreamFromHbaseWithBufer() throws Exception {
		final HBaseInputStream stream = new HBaseInputStream(new HBaseStreamService());
		
		int bytesRead;
		byte[] buffer = new byte[1024];
		while ((bytesRead = stream.read(buffer)) != -1) {
			System.out.write(buffer);
		}
		
		System.out.flush();
		
	}

}