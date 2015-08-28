package com.kana.dev.springboot.contacts.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseInputStream extends InputStream {

	private HBaseStreamService hbaseStream = null;
	SequenceInputStream is = null;

	public HBaseInputStream(final HBaseStreamService hbaseStream) {
		this.hbaseStream = hbaseStream;
	}

	@Override
	public int read() throws IOException {
		if (is == null) {
			is = hbaseStream.getNextSequenceInputStream();
			if (is == null) {
				return -1;
			}
		}

		int byteRead = is.read();
		
		if (byteRead == -1) {
			is = hbaseStream.getNextSequenceInputStream();
			if (is == null) {
				return -1;
			}
			return read();
		}

		return byteRead;
	}
}
