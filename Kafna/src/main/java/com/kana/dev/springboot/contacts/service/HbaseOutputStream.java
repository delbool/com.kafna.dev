package com.kana.dev.springboot.contacts.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class HbaseOutputStream extends OutputStream {

	private final static int MAX_BUFFER_LIMT = 5 * 1024 * 1024;
	byte[] blob = new byte[MAX_BUFFER_LIMT];
	int offset = -1;

	int blobCount = 0;
	
	public HbaseOutputStream() {
	}

	@Override
	public void write(int b) throws IOException {
		
		if (offset < MAX_BUFFER_LIMT) {
			if (b == -1) {
				// System.out.println("A value of -1 was found in input...But limit is not reached...");
				writeBlobToHBase();
			}
			offset++;
			blob[offset] = (byte) b;
//			blob = ArrayUtils.add(blob, (byte) b);
			if (offset % 2000000 == 0) {
				System.out.println("-------------->> Current blob  offset at : " + offset);
			}
		} else {
			writeBlobToHBase();
		}
	}

	private void writeBlobToHBase() throws IOException {
		// This will be actually writing a blob to HBase.
		// For now saving to file.
		flush();
		if (offset < MAX_BUFFER_LIMT - 1){
			// copy valid bytes only
			byte [] actualBytes = new byte[offset];
			System.arraycopy(blob, 0, actualBytes, 0, offset);
			blob = actualBytes;
		}
		
		System.out.println("Writing blob of size : " + blob.length + " to file while offset of : " + offset);
		int bufferSize = 1024;
		if (blob.length > 0) {
			File destFile = new File("/temp/data/Output-" + System.currentTimeMillis() + ".zip");
			try (ByteArrayInputStream bais = new ByteArrayInputStream(blob);
					OutputStream fos = new BufferedOutputStream(new FileOutputStream(destFile, destFile.exists()), bufferSize)) {
				IOUtils.copy(bais, fos);
			}
		}
		blobCount++;

		// reset offset
		offset = -1;
		blob = new byte[MAX_BUFFER_LIMT];
		System.out.println("===> Saved " + blobCount + " blobs so far.");
	}

}
