package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Vector;

import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HbaseInputStreamClient {

	private HBaseStreamServiceLocal hbaseStreamServiceLocal;
	private HBaseInputStream stream = null;
	private Writer writer = new StringWriter();

	@Before
	public void setup() {
		hbaseStreamServiceLocal = new HBaseStreamServiceLocal();
		stream = new HBaseInputStream(hbaseStreamServiceLocal);
		stream.setTableName("fakeTable");
		stream.setRowId("fakeRowdId");
	}

	@Test
	public void testStreamFromHbase() throws Exception {
		int byteRead;
		while ((byteRead = stream.read()) != -1) {
			writer.write(byteRead);
		}

		String expected = "This is data from cell1.\n" + "This is data from cell2.\n\n" + "This is data from cell3.";
		Assert.assertEquals(expected, writer.toString());
	}

	@Test
	public void testStreamFromHbaseWithBufer() throws Exception {
		int bytesRead;
		byte[] buffer = new byte[1024];
		while ((bytesRead = stream.read(buffer)) != -1) {
			writer.write(Bytes.toString(buffer, 0, bytesRead));
		}

		String expected = "This is data from cell1.\n" + "This is data from cell2.\n\n" + "This is data from cell3.";
		Assert.assertEquals(expected, writer.toString());
	}

	@After
	public void cleanUp() throws IOException {
		stream = null;
		hbaseStreamServiceLocal = null;
		writer.close();
	}

	// private test class hiding the complexity of HbaseService
	private class HBaseStreamServiceLocal extends HBaseStreamService {

		public ByteArrayInputStream readBlobAStream(String tableName, String rowId, int cellCount) {
			// handling this call locally
			return getHbaseStreams().get(cellCount);
		}

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
	}
}