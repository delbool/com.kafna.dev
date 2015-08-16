package com.kana.dev;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class VideoServiceTest {

	@Test
	public void testFileExists(){		
		File file1 = new File("/AAA");
		Assert.assertTrue(file1.exists());	
		
		File file2 = new File("/AAA/ABCD");
		Assert.assertFalse(file2.exists());	
	}
}
