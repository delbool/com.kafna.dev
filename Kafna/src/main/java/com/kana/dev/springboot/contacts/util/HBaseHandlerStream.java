package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HBaseHandlerStream extends InputStream {

	private HBaseStream hbaseStream = null;
	private final int bytesToRead = 100;
	private final int offset = 0;
	ByteArrayInputStream is = null;
	byte[] collector = new byte[100];

	public HBaseHandlerStream(final HBaseStream hbaseStream) {
		this.hbaseStream = hbaseStream;
	}

	@Override
	public int read() throws IOException {
		if (is == null) {
			is = hbaseStream.getNextStream(0);
		}

		final int oneByte = is.read();
		// collector[offset] = (byte) oneByte;
		// offset++;
		return oneByte;

	}

}
