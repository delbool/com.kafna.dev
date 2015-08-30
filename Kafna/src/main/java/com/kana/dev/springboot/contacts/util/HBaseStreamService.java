package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseStreamService {

	public Vector<ByteArrayInputStream> getHbaseStreams() {
		final byte[] bytes1 = Bytes.toBytes("This is data from cell1.\n");
		final ByteArrayInputStream b1 = new ByteArrayInputStream(bytes1);

		final byte[] bytes2 = Bytes.toBytes("This is data from cell2.\n\n");
		final ByteArrayInputStream b2 = new ByteArrayInputStream(bytes2);

		final byte[] bytes3 = Bytes.toBytes("This is data from cell3.");
		final ByteArrayInputStream b3 = new ByteArrayInputStream(bytes3);

		final Vector<ByteArrayInputStream> inputStreams = new Vector<ByteArrayInputStream>();
		inputStreams.add(b1);
		inputStreams.add(b2);
		inputStreams.add(b3);
		inputStreams.add(null);
		return inputStreams;
	}

	public ByteArrayInputStream readBlobAStream(String tableName, String rowId, int cellCount) {
		// TODO Auto-generated method stub
		return null;
	}

//	public SequenceInputStream getNextSequenceInputStream(int cellCount) {
//		final Vector<ByteArrayInputStream> inputStreams = new Vector<ByteArrayInputStream>();
//		final ByteArrayInputStream bas = getHbaseStreams().get(cellCount);
//		if (bas == null) {
//			return null;
//		}
//		
//		inputStreams.add(bas);
//		SequenceInputStream sis = new SequenceInputStream(inputStreams.elements());
//		return sis;
//	}

}