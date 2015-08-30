package com.kana.dev.springboot.contacts.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseInputStream extends InputStream {
	private String tableName;
	private String rowId;
	private int cellCount = 0;
	boolean firstPass = true;
	
	private HBaseStreamService hbaseStream = null;
	SequenceInputStream is = null;

	public HBaseInputStream(final HBaseStreamService hbaseStream) {
		this.hbaseStream = hbaseStream;
	}

	
	@Override
	public int read() throws IOException {
		if (is == null && firstPass) {
			firstPass = false;
			is = getNextSequenceInputStream(tableName, rowId);
		}

		if (is == null) {
			return -1;
		}

		int byteRead = is.read();
		
		if (byteRead == -1) {
			is = getNextSequenceInputStream(tableName, rowId);
			if (is == null) {
				return -1;
			}
			return read();
		}

		return byteRead;
	}
	
	public SequenceInputStream getNextSequenceInputStream(String tableName, String rowId) {
		final Vector<ByteArrayInputStream> inputStreams = new Vector<ByteArrayInputStream>();
		final ByteArrayInputStream bas = hbaseStream.readBlobAStream(tableName, rowId, this.cellCount);
		this.cellCount++;
		if (bas == null) {
			return null;
		}
		
		inputStreams.add(bas);
		SequenceInputStream sis = new SequenceInputStream(inputStreams.elements());
		return sis;
	}

	public int getCellCount() {
		return cellCount;
	}


	public void setCellCount(int cellCount) {
		this.cellCount = cellCount;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getRowId() {
		return rowId;
	}


	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	
}
