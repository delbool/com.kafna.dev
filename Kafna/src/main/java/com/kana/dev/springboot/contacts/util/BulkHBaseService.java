package com.kana.dev.springboot.contacts.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class BulkHBaseService  {
	final public List<String> fileLocations = new ArrayList<String>();
	final public Vector<InputStream> inputStreams = new Vector<InputStream>();

	public SequenceInputStream readBlobAsStream() throws FileNotFoundException, IOException {
		fileLocations.add("C:/temp/data/testfile1.txt");
		fileLocations.add("C:/temp/data/testfile2.txt");
		fileLocations.add("C:/temp/data/testfile3.txt");

		final Vector<InputStream> inputStreams = new Vector<InputStream>();
		inputStreams.add(new FileInputStream(fileLocations.get(0)));
		final Enumeration<InputStream> enu = inputStreams.elements();
		final SequenceInputStream sis = new SequenceInputStream(enu);

		return sis;
	}

	public SequenceInputStream addInputStreamToSequenceInputStream(final List<String> fileLocations,
			final Vector<InputStream> inputStreams, final int index) throws FileNotFoundException {		
		inputStreams.add(new FileInputStream(fileLocations.get(index)));
		final Enumeration<InputStream> enu = inputStreams.elements();
		final SequenceInputStream sis = new SequenceInputStream(enu);
		
		return sis;
	}

	public InputStream getMyStream() {
		return new InputStream() {

			@Override
			public int read() throws IOException {
				readBlobAsStream();
				return 0;
			}
		};
	}
}