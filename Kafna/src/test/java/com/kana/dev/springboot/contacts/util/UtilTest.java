package com.kana.dev.springboot.contacts.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Test;

import com.kana.dev.springboot.contacts.persistence.HBaseClient;

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
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNavigableMap(){
		NavigableMap<String, String> map = new TreeMap<String, String>(); 
		map.put("Abraham1", "Abraham1");
		map.put("Tesfay1", "Tesfay1");
		map.put("Abraham2", "Abraham2");
		map.put("Abraham10", "Abraham10");
		
		for (String key : map.keySet()){
			System.out.println(map.get(key));
		}
	}
	
	@Test
	public void testInputStream() throws IOException{
		String inputFileName =  "C:/downloads/Eclipse/e.zip";
		File outputFile = new File("C:/AAA/abraham.zip");
		OutputStream out = new FileOutputStream(outputFile);
		
		HBaseClient client = new HBaseClient();
		InputStream is = client.readBlobAsStream(inputFileName);
		int bytesRead;
		byte[] buffer = new byte[1024];
		while ( (bytesRead = is.read(buffer)) != -1 ){
			out.write(buffer, 0, bytesRead);
		}
		is.close();
		out.flush();
		out.close();
	}
	
	@Test
	public void testReadBytesFromStream() throws IOException{
		long start = System.currentTimeMillis();
		IOUtil3.readBytesFromStream();
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));

	}
}
