package com.kana.dev.springboot.contacts.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseHandlerStream extends InputStream {

	private HBaseStream hbaseStream = null;
	SequenceInputStream is = null;
	byte[] collector = new byte[100];
	private int index = 0;

	public HBaseHandlerStream(final HBaseStream hbaseStream) {
		this.hbaseStream = hbaseStream;
	}

	@Override
	public int read() throws IOException {
		int byteRead = 0;
		if (is == null) {
			is = hbaseStream.getSequenceInputStream();
			if (is == null) {
				return -1;
			}
		}

		byteRead = is.read();
		
		if (byteRead == -1) {
			if (index == 0) {
				index++;
				byte[] bytes3 = Bytes.toBytes("This is data from cell3.");
				is = hbaseStream.addSequenceInputStream(bytes3, hbaseStream.getHbaseStreams());
				return read();
			}
		}

		return byteRead;
	}

	// @Override
	// public int read(final byte[] buffer) throws IOException {
	// if (is == null) {
	// is = hbaseStream.getNextStream(index);
	// if (is == null) {
	// return -1;
	// }
	// }
	//
	// if (is == null) {
	// return -1;
	// }
	//
	// final int bytesRead = is.read(buffer);
	// return bytesRead;
	// }
	//
	// @Override
	// public int read(final byte[] b, final int off, final int len) throws IOException {
	// if (is == null) {
	// is = hbaseStream.getNextStream(index);
	// if (is == null) {
	// return -1;
	// }
	// }
	//
	// if (is == null) {
	// return -1;
	// }
	//
	// final int bytesRead = is.read(b, off, len);
	// return bytesRead;
	// }

}
