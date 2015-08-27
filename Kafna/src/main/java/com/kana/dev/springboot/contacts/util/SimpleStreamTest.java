package com.kana.dev.springboot.contacts.util;

import java.io.InputStream;

import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class SimpleStreamTest {

	@Test
	public void testGetBlob() throws Exception {
		SimpleStream stream = new SimpleStream();
		InputStream is = stream.getBlobStream();
		
		int bytesRead;
		byte[] buf = new byte[50];
		while ((bytesRead = is.read(buf)) != -1) {
			System.out.write(buf, 0, bytesRead);
			System.out.println(Bytes.toString(buf));
//			break;
		}
		
		System.out.flush();
		
	}

}