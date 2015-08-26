package com.kana.dev.springboot.contacts.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class IOUtil2 {

	public static byte[] readBytesFromFile(String fileName) throws IOException {
		int bufferSize = 64 * 1024;
		File file = new File(fileName);

		long length = file.length();
		byte[] buffer = new byte[(int) length];

		int numRead = 0;
		try (InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)), bufferSize)) {
			while (numRead != -1) {
				System.out.println("Bytes read: " + numRead);
				numRead = bis.read(buffer, numRead, bufferSize);
			}
		}
		return buffer;
	}
  
   /**
    * Writes the specified byte[] to the specified File path.
    * 
    * @param theFile File Object representing the path to write to.
    * @param bytes The byte[] of data to write to the File.
    * @throws IOException Thrown if there is problem creating or writing the 
    * File.
    */
   public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
     BufferedOutputStream bos = null;
     
   try {
     FileOutputStream fos = new FileOutputStream(theFile);
     bos = new BufferedOutputStream(fos); 
     bos.write(bytes);
   }finally {
     if(bos != null) {
       try  {
         //flush and close the BufferedOutputStream
         bos.flush();
         bos.close();
       } catch(Exception e){}
     }
   }
   }
}