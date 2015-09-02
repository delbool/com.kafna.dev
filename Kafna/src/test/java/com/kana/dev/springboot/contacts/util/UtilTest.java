package com.kana.dev.springboot.contacts.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;



public class UtilTest {

	@Test
	public void ioTest() throws IOException {

		long start = System.currentTimeMillis();
		IOUtil3.readBytesFromFile2("/downloads/eclipse/e.zip");
//		IOUtil2.writeBytesToFile(new File("/downloads/eclipse/e2.zip"), bytes);
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));
		
		start = System.currentTimeMillis();
		IOUtil3.readBytesFromFile("/downloads/eclipse/e.zip");
//		IOUtil2.writeBytesToFile(new File("/downloads/eclipse/e2.zip"), bytes);
		end = System.currentTimeMillis();
		System.out.println("Time taken: " + (end - start));
		
	}

	@Test
	public void testJava7IoTry()  {
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt"))) {

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} 
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNavigableMap(){
		final NavigableMap<String, String> map = new TreeMap<String, String>(); 
		map.put("Abraham1", "Abraham1");
		map.put("Tesfay1", "Tesfay1");
		map.put("Abraham2", "Abraham2");
		map.put("Abraham10", "Abraham10");
		
		for (final String key : map.keySet()){
			System.out.println(map.get(key));
		}
	}
	
	@Test
	public void testInputStream() throws IOException{
		final String inputFileName =  "C:/downloads/Eclipse/e.zip";
		final File outputFile = new File("C:/AAA/abraham.zip");
		final OutputStream out = new FileOutputStream(outputFile);
		
//		final HBaseClient client = new HBaseClient();
//		final InputStream is = client.readBlobAsStream(inputFileName);
		final int bytesRead;
//		final byte[] buffer = new byte[1024];
//		while ( (bytesRead = is.read(buffer)) != -1 ){
//			out.write(buffer, 0, bytesRead);
//		}
//		is.close();
		out.flush();
		out.close();
	}
	
	@Test
	public void testReadBytesFromStream() throws IOException{
		final long start = System.currentTimeMillis();
		IOUtil3.readBytesFromStream();
		final long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));

	}
	
	@Test
	public void testAppendToByteBuffer() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte [] myColData = new byte[5];
		
		byte[] bytes1 = Bytes.toBytes("The quick brown fox-");
		byte[] bytes2 = Bytes.toBytes("jumps over the lazy-");
		byte[] bytes3 = Bytes.toBytes("dog. An old time abc lesson.");
		
		baos.write(bytes1);
		baos.write(bytes2);
		baos.write(bytes3);

		myColData = baos.toByteArray();
		
		String allBytes = Bytes.toString(myColData);
		System.out.println(allBytes);
	}

	@Test
	public void testAppendToByteBufferApacheUtils() throws IOException{

		byte [] myColData = null;
		
		byte[] bytes1 = Bytes.toBytes("The quick brown fox-");
		byte[] bytes2 = Bytes.toBytes("jumps over the lazy-");
		byte[] bytes3 = Bytes.toBytes("dog. An old time abc lesson.");
		
		myColData = ArrayUtils.addAll(myColData, bytes1);
		myColData = ArrayUtils.addAll(myColData, bytes2);
		myColData = ArrayUtils.addAll(myColData, bytes3);
		
		String allBytes = Bytes.toString(myColData);
		System.out.println("Size of byte array: " + myColData.length + "   And the content: " + allBytes);
	}


}
