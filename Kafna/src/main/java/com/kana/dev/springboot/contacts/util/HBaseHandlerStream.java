package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HBaseHandlerStream extends InputStream {

	private HBaseStream hbaseStream = null;
	ByteArrayInputStream is = null;
	byte[] collector = new byte[100];
	private int index = 0;

	public HBaseHandlerStream(final HBaseStream hbaseStream) {
		this.hbaseStream = hbaseStream;
	}

	@Override
	public int read() throws IOException {
		int byteRead = 0;
		if (is == null) {
			is = hbaseStream.getNextStream(index);
			if (is == null) {
				return -1;
			}
		}

		byteRead = is.read();
		if (byteRead == -1) {
			is = null;
			index++;
			byteRead = 0;
		}
		return byteRead;
	}

	@Override
	public int read(final byte[] buffer) throws IOException {
		if (is == null) {
			is = hbaseStream.getNextStream(index);
			if (is == null) {
				return -1;
			}
		}

		if (is == null) {
			return -1;
		}

		final int bytesRead = is.read(buffer);
		return bytesRead;
	}

	@Override
	public int read(final byte[] b, final int off, final int len) throws IOException {
		if (is == null) {
			is = hbaseStream.getNextStream(index);
			if (is == null) {
				return -1;
			}
		}

		if (is == null) {
			return -1;
		}

		final int bytesRead = is.read(b, off, len);
		return bytesRead;
	}

}
