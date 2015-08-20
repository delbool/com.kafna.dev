package com.kana.dev.springboot.contacts.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.kana.dev.springboot.contacts.model.Video;

public class VideoServiceTest {

	private String dirName;		
	private Video video = null;
	private int totalChunks;
	
	@Before
	public void setup() throws Exception{
		dirName = "/java.io.temp";		
		Video video = new Video();
		totalChunks = 10;
		video.setFileName("test.zip");
		saveChunkedFilesInDirectory(dirName);
	}
	
	@Test
	public void testGetChunkedFiles() throws Exception{
		VideoService service = new VideoService();
		File[] files = service.getChunkedFiles(video, dirName);
		Assert.assertNotNull(files);
		Assert.assertTrue(10 == files.length);
		String prevFileName = "test.zip";
		for ( File file : files){
			String currentFileName = file.getName();
			Assert.assertTrue(currentFileName.indexOf("test.zip") == 0);
			Assert.assertTrue(currentFileName.indexOf("~.") == 0);
			Assert.assertTrue(currentFileName.compareTo(prevFileName) > 0 );
			prevFileName = currentFileName;
		}		
	}

	private void saveChunkedFilesInDirectory(String dirName) throws Exception {
		String content = "The quick brown fox...";
		for (int i = 1; i <= totalChunks; i++){
			Path path = FileSystems.getDefault().getPath(dirName + File.separator + "test.zip~." + i + "~." + totalChunks);
			Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
		}		
	}
	
	@After
	public void cleanUp(){
		for ( int i = 1; i <= 10; i++){
			File file = new File(dirName + File.separator + "test.zip~." + i + "~." + totalChunks);
			file.delete();
		}
	}
	
}
