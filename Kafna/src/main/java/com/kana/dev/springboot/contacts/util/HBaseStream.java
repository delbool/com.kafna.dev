package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseStream {

	private final byte[] data = null;
	private final byte[] bytes1 = Bytes.toBytes("This is data from cell1.\n");

	// assume HbaseService returns ByteArrayInputStreams
	public List<ByteArrayInputStream> getHbaseStreams() {
		final byte[] bytes1 = Bytes.toBytes("This is data from cell1.\n");
		final ByteArrayInputStream b1 = new ByteArrayInputStream(bytes1);

		final byte[] bytes2 = Bytes.toBytes("This is data from cell2.\n\n");
		final ByteArrayInputStream b2 = new ByteArrayInputStream(bytes2);

		final byte[] bytes3 = Bytes.toBytes("This is data from cell3.");
		final ByteArrayInputStream b3 = new ByteArrayInputStream(bytes3);

		final List<ByteArrayInputStream> inputStreams = new ArrayList<ByteArrayInputStream>();
		inputStreams.add(b1);
		inputStreams.add(b2);
		inputStreams.add(b3);
		inputStreams.add(null);
		return inputStreams;
	}

	public ByteArrayInputStream getNextStream(final int index) {
		return getHbaseStreams().get(index);
	}

	// Assume HbaseService returns cell data in bytes
	public List<byte[]> getBytesFromHbase() {
		final byte[] bytes1 = Bytes.toBytes("This is data from cell1.\n");
		final byte[] bytes2 = Bytes.toBytes("This is data from cell2.\n\n");
		final byte[] bytes3 = Bytes.toBytes("This is data from cell3.");

		final List<byte[]> hbaseCellData = new ArrayList<byte[]>();
		hbaseCellData.add(bytes1);
		hbaseCellData.add(bytes2);
		hbaseCellData.add(bytes3);
		hbaseCellData.add(null);
		return hbaseCellData;
	}

	public byte[] getNextCellData(final int index) {
		return getBytesFromHbase().get(index);
	}

	// public InputStream getBlobStream() {
	// return new InputStream() {
	//
	// @Override
	// public int read() throws IOException {
	// byte[] buffer = getBytesFromHbase().get(0);
	// String test = Bytes.toString(buffer);
	// int bytesRead = buffer.length;
	// return bytesRead;
	// }
	// };
	// }

}