package com.kana.dev.springboot.contacts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kana.dev.springboot.contacts.model.Video;

@Service
public class VideoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoService.class);
	
	public void uploadVideoFile(final Video videoPart, final String tempDir) {
		final File [] chunkFiles = getChunkedFiles(videoPart, tempDir);
				
		for (final File file : chunkFiles){
			if (file == null){
				return;
			}
			
			LOGGER.info("-------------->>> Processing chunk file : " + file.getAbsolutePath());
			Video video = createVideoFromChunk(file, videoPart);
			
			//here will send to the actual uploader
		}
	}

	File[] getChunkedFiles(final Video videoPart, final String tempDir) {
		final File directory = new File(tempDir);
		final String originalName = videoPart.getFileName();
		final File[] files = directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String fileName) {
				return fileName.startsWith(originalName) && fileName.indexOf("~.") > 0;
			}			
		});

		Collections.sort(Arrays.asList(files), new Comparator<File>(){

			public int compare(File f1, File f2) {
				final String [] file1Parts = f1.getName().split("~.");
				final Integer chunkNo1 = Integer.parseInt(file1Parts[1]);
				
				final String [] file2Parts = f2.getName().split("~.");
				final Integer chunkNo2 = Integer.parseInt(file2Parts[1]);
				return chunkNo1.compareTo(chunkNo2);
			}			
		});

		return files;
	}

	private Video createVideoFromChunk(final File file, final Video videoPart) {
		Video video = null;
		if( videoPart == null){
			LOGGER.error("Video Part {} is empty", videoPart);
			return null;
		}
		
		if ( !file.exists() ){
			LOGGER.error("Chunk file {} does not exist.", file.getName());
			return null;
		}
		
		try{
			final byte[] chunkData = IOUtils.toByteArray(new FileInputStream(file));
			final String chunkName = file.getName();
			// eclipse-jee-juno-SR2-win32-x86_64.zip.0.29
			final String [] parts = chunkName.split("\\.");
			final String fileName = parts[0] + parts[1];
			final String chunkNo = parts[2];
			final String chunks = parts[3];
			
			LOGGER.info("The parts are File Name: {}, Chunk No: {}, Chunks: {}", fileName, chunkNo, chunks);
			LOGGER.info("The size of Chunk DATA: {} ", chunkData.length );

			video = new Video();
			video.setChunk(chunkNo);
			video.setChunkData(chunkData);
			video.setChunkFileName(chunkName);
			video.setChunks(chunks);
			video.setFileName(fileName);

		}catch (final IOException e){
			e.printStackTrace();
		}
		return video;
	}

}
