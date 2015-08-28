package com.kana.dev.springboot.contacts.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

   public static byte[] readBytesFromFile(File file) throws IOException {
     InputStream is = new FileInputStream(file);
 
     // If this is reverted this file will have 63 lines .......
     // jsdhfskdj fksjdhfksdh jksdhfkjsdf hsdkfh sdkjf hsdkj f hsdkj 
     // sjdjfhksdjfhsdkj hkjsdhfksdj hkjsdfhskdjfhsdkjf hsd
     // sjkhsdkjfh sdkjfh ksjfhsdkjfh sdkjf hsdkjfhsdkj
     long length = file.length();
     byte[] bytes = new byte[(int)length];
     int bufferSize = 64 * 1024;
     // Read in the bytes
     int offset = 0;
     int numRead = 0;
//   while ( offset < bytes.length &&  (numRead=is.read(bytes, offset, ( (bytes.length - offset < bufferSize) ? bytes.length - offset : bufferSize) )) >= 0) {
     while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
    	 System.out.println("Offeset :" + offset);
    	 System.out.println("Bytes read: " + numRead);
         offset += numRead;
     }
 
     // Ensure all the bytes have been read in
     if (offset < bytes.length) {
         throw new IOException("Could not completely read file " + file.getName());
     }
 
     // Close the input stream and return bytes
     is.close();
     return bytes;
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