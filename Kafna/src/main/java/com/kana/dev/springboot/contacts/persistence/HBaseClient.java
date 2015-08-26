package com.kana.dev.springboot.contacts.persistence;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseClient {

	public static void main(String[] arg) throws IOException {
		Configuration config = HBaseConfiguration.create();

		HTable testTable = new HTable(config, "test");

		for (int i = 0; i < 100; i++) {
			byte[] family = Bytes.toBytes("cf");
			byte[] qual = Bytes.toBytes("a");

			Scan scan = new Scan();
			scan.addColumn(family, qual);
			scan.setStartRow(Bytes.toBytes(i));
			scan.setStopRow(Bytes.toBytes(i));

			ResultScanner rs = testTable.getScanner(scan);
			for (Result r = rs.next(); r != null; r = rs.next()) {
				byte[] valueObj = r.getValue(family, qual);
				String value = new String(valueObj);
				System.out.println(value);
			}
		}

		testTable.close();
	}

	public void readColumn(String tableName, String colFamily, String qualifier, OutputStream outputStream) throws IOException {
		Configuration config = HBaseConfiguration.create();
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf(tableName));

		int rows = 100; // TODO how to get number of rows
		for (int i = 0; i < rows; i++) {
			byte[] family = Bytes.toBytes(colFamily);
			byte[] qual = Bytes.toBytes(qualifier);

			Scan scan = new Scan();
			scan.addColumn(family, qual);
			scan.setStartRow(Bytes.toBytes(i));
			scan.setStopRow(Bytes.toBytes(i));
			scan.setRowOffsetPerColumnFamily(1);

			ResultScanner rs = table.getScanner(scan);
			for (Result r = rs.next(); r != null; r = rs.next()) {
				byte[] value = r.getValue(family, qual);
				outputStream.write(value);
				System.out.println(new String(value));
			}
		}

		table.close();
	}

	public void readColumnUsingGet(String tableName, String colFamily, String qualifier, OutputStream outputStream) throws IOException {
		Configuration config = HBaseConfiguration.create();
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf(tableName));

		int rows = 100; // TODO how to get number of rows
		for (int i = 0; i < rows; i++) {
			byte[] family = Bytes.toBytes(colFamily);
			byte[] qual = Bytes.toBytes(qualifier);

			Get get = new Get(Bytes.toBytes(i));
			get.addColumn(family, qual);

			Scan scan = new Scan(get);
			scan.setStartRow(Bytes.toBytes(i));
			scan.setStopRow(Bytes.toBytes(i));

			ResultScanner rs = table.getScanner(scan);
			for (Result r = rs.next(); r != null; r = rs.next()) {

				byte[] value = r.getValue(family, qual);
				InputStream is = new ByteArrayInputStream(value);
				while (is.read() != -1) {
					outputStream.write(is.read());
				}
				System.out.println(new String(value));
			}
		}

		table.close();
	}

	public String[] getColumnsInColumnFamily(Result r, String ColumnFamily) {
		NavigableMap<byte[], byte[]> familyMap = r.getFamilyMap(Bytes.toBytes(ColumnFamily));
		String[] qualifiers = new String[familyMap.size()];

		int counter = 0;
		for (byte[] bQunitifer : familyMap.keySet()) {
			qualifiers[counter++] = Bytes.toString(bQunitifer);

		}

		Collections.sort(Arrays.asList(qualifiers), new Comparator<String>() {

			@Override
			public int compare(String f1, String f2) {
//				final String[] file1Parts = f1.getName().split("~.");
//				final Integer chunkNo1 = Integer.parseInt(file1Parts[1]);
//
//				final String[] file2Parts = f2.getName().split("~.");
//				final Integer chunkNo2 = Integer.parseInt(file2Parts[1]);
//				return chunkNo1.compareTo(chunkNo2);
				return 0;
			}
		});

		return qualifiers;
	}

	public InputStream readBlobAsStream(final String inputFile) {
		return new InputStream() {
			@Override
			public int read() throws IOException {
				return readByteData(inputFile);
			}
		};
	}

	protected int readByteData(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName);
		return is.read();
	}

}
