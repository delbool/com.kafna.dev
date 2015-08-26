package com.kana.dev.springboot.contacts.util;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class BulkHBaseServiceTest  {
	
	@Test
	public void testRead() throws IOException{
		final BulkHBaseService service = new BulkHBaseService();
		final InputStream is = service.getMyStream();
		
		int bytesRead = 0;
		final byte[] myBuf = new byte[10];
		while ((bytesRead = is.read(myBuf)) != -1) {

			System.out.write(myBuf);
		}
		System.out.flush();
	}

}