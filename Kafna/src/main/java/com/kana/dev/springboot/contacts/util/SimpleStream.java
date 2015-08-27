package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

public class SimpleStream {

	
	public List<InputStream> getStreams() {
		byte[] bytes1 = Bytes.toBytes("The quick brown fox jumps over the lazy dog.\n");
		InputStream b1 = new ByteArrayInputStream(bytes1);

		byte[] bytes2 = Bytes.toBytes("Old friends are like old wine. They taste great.\n\n");
		InputStream b2 = new ByteArrayInputStream(bytes2);

		byte[] bytes3 = Bytes.toBytes("This is my last sentence.");
		InputStream b3 = new ByteArrayInputStream(bytes3);

		final List<InputStream> inputStreams = new ArrayList<InputStream>();
		inputStreams.add(b1);
		inputStreams.add(b2);
		inputStreams.add(b3);
		inputStreams.add(null);
		return inputStreams;
	}
	
	public InputStream getNextStream(int index){
		return getStreams().get(index);
	}
		
	public InputStream getBlobStream() {
		final InputStream is = getNextStream(0);
		return new InputStream() {
			
			@Override
			public int read() throws IOException {
				byte [] buffer = new byte[50];
				int bytesRead = is.read(buffer);				
				String test = Bytes.toString(buffer);
				return bytesRead;
			}
		};
	}


}