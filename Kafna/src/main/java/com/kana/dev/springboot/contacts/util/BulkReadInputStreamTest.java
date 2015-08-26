package com.kana.dev.springboot.contacts.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class BulkReadInputStreamTest 
{

	@Test
	public void testRead() throws FileNotFoundException, IOException{
		final BulkReadInputStream is = new BulkReadInputStream();
		is.read();
	}

}