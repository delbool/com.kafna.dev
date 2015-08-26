package com.kana.dev.springboot.contacts.util;

import java.io.IOException;
import java.io.InputStream;

public class BulkReadInputStream extends InputStream {

	@Override
	public int read() throws IOException {
		final BulkHBaseService hbaseService = new BulkHBaseService();
		return 0;
	}

}