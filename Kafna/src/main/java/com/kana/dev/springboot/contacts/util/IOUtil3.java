package com.kana.dev.springboot.contacts.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil3 {

	public static boolean readBytesFromFile(String fileName) throws IOException {
		int bytesRead = 0;
		File file = new File(fileName);
		
		try (InputStream is = new FileInputStream("/downloads/eclipse/e.zip"); OutputStream os = new FileOutputStream("/downloads/eclipse/e3.zip")) {
			byte[] buff = new byte[(int) file.length()];
			while (-1 != (bytesRead = is.read(buff))) {
				//System.out.println("Current Read: " + bytesRead);
				os.write(buff, 0, bytesRead);
			}
			os.flush();
		} catch (Exception ex) {
			return false;
		} 
		return true;
	}

	public static boolean readBytesFromFile2(String fileName) throws IOException {
		int bytesRead = 0;
		File file = new File(fileName);
		byte[] buffer = new byte[64 *1024];
		
		try (InputStream is = new FileInputStream("/downloads/eclipse/e.zip"); OutputStream os = new FileOutputStream("/downloads/eclipse/e4.zip")) {
			byte[] buff = new byte[(int) file.length()];
			while (-1 != (bytesRead = is.read(buffer)) ) {
				//System.out.println("Current Read: " + bytesRead);
				os.write(buff, 0, bytesRead);
			}
			os.flush();
		} catch (Exception ex) {
			return false;
		} 
		return true;
	}

	/**
	 * Writes the specified byte[] to the specified File path.
	 * 
	 * @param theFile
	 *            File Object representing the path to write to.
	 * @param bytes
	 *            The byte[] of data to write to the File.
	 * @throws IOException
	 *             Thrown if there is problem creating or writing the File.
	 */
	public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
		BufferedOutputStream bos = null;

		try {
			FileOutputStream fos = new FileOutputStream(theFile);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
		} finally {
			if (bos != null) {
				try {
					// flush and close the BufferedOutputStream
					bos.flush();
					bos.close();
				} catch (Exception e) {
				}
			}
		}
	}
}