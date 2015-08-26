package com.kana.dev.springboot.contacts.persistence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static void main(final String[] arg) throws IOException {
        final Configuration config = HBaseConfiguration.create();

        final HTable testTable = new HTable(config, "test");

        for (int i = 0; i < 100; i++) {
            final byte[] family = Bytes.toBytes("cf");
            final byte[] qual = Bytes.toBytes("a");

            final Scan scan = new Scan();
            scan.addColumn(family, qual);
            scan.setStartRow(Bytes.toBytes(i));
            scan.setStopRow(Bytes.toBytes(i));
            
            final ResultScanner rs = testTable.getScanner(scan);
            for (Result r = rs.next(); r != null; r = rs.next()) {
                final byte[] valueObj = r.getValue(family, qual);
                final String value = new String(valueObj);
                System.out.println(value);
            }
        }
        
        testTable.close();
    }
    
    public void readColumn(final String tableName, final String colFamily, final String qualifier, final OutputStream outputStream) throws IOException{
        final Configuration config = HBaseConfiguration.create();
        final Connection connection = ConnectionFactory.createConnection(config);
        final Table table = connection.getTable(TableName.valueOf(tableName));
       
        final int rows = 100;  //TODO how to get number of rows
        for (int i = 0; i < rows; i++) {
            final byte[] family = Bytes.toBytes(colFamily);
            final byte[] qual = Bytes.toBytes(qualifier);

            final Scan scan = new Scan();
            scan.addColumn(family, qual);
            scan.setStartRow(Bytes.toBytes(i));
            scan.setStopRow(Bytes.toBytes(i));
            scan.setRowOffsetPerColumnFamily(1);
           
            final ResultScanner rs = table.getScanner(scan);
             for (Result r = rs.next(); r != null; r = rs.next()) {
                final byte[] value = r.getValue(family, qual);               
                outputStream.write(value);
                System.out.println( new String(value) );
            }
        }
        
        table.close();
    }
    
    public void readColumnUsingGet(final String tableName, final String colFamily, final String qualifier, final OutputStream outputStream) throws IOException{
        final Configuration config = HBaseConfiguration.create();
        final Connection connection = ConnectionFactory.createConnection(config);
        final Table table = connection.getTable(TableName.valueOf(tableName));
       
        final int rows = 100;  //TODO how to get number of rows
        for (int i = 0; i < rows; i++) {
            final byte[] family = Bytes.toBytes(colFamily);
            final byte[] qual = Bytes.toBytes(qualifier);

            final Get get = new Get(Bytes.toBytes(i));
            get.addColumn(family, qual);

            final Scan scan = new Scan(get);
            scan.setStartRow(Bytes.toBytes(i));
            scan.setStopRow(Bytes.toBytes(i));
           
            final ResultScanner rs = table.getScanner(scan);
             for (Result r = rs.next(); r != null; r = rs.next()) {
                final byte[] value = r.getValue(family, qual); 
                final InputStream is = new ByteArrayInputStream(value);
				while (is.read() != -1) {
					outputStream.write(is.read());
				}
                System.out.println( new String(value) );
            }
        }
        
        table.close();
    }
    


    public String[] getColumnsInColumnFamily(final Result r, final String ColumnFamily)
    {

          final NavigableMap<byte[], byte[]> familyMap = r.getFamilyMap(Bytes.toBytes(ColumnFamily));
          final String[] Quantifers = new String[familyMap.size()];

          int counter = 0;
          for(final byte[] bQunitifer : familyMap.keySet())
          {
              Quantifers[counter++] = Bytes.toString(bQunitifer);

          }

          return Quantifers;
    }

	public InputStream getMyStream() {
		return new InputStream() {

			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

}
