package com.kana.dev.springboot.contacts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kana.dev.springboot.contacts.model.Video;

@Service
public class VideoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoService.class);
	
	public void uploadVideoFile(Video videoPart, String tempDir) {
		File [] chunkFiles = getChunkedFiles(videoPart, tempDir);
		for (File file : chunkFiles){
			if (file == null){
				return;
			}
			Video video = createVideoFromChunk(file, videoPart);
			
			//here will send to the actual uploader
		}

	}

	private File[] getChunkedFiles(Video videoPart, String tempDir) {
		File directory = new File(tempDir);
		final String originalName = videoPart.getFileName();
		File[] files = directory.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(originalName);
			}
		});

		return files;
	}

	private Video createVideoFromChunk(File file, Video videoPart) {
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
			byte[] chunkData = IOUtils.toByteArray(new FileInputStream(file));
			String chunkName = file.getName();
			// eclipse-jee-juno-SR2-win32-x86_64.zip.0.29
			String [] parts = chunkName.split("\\.");
			String fileName = parts[0] + parts[1];
			String chunkNo = parts[2];
			String chunks = parts[3];
			
			LOGGER.info("The parts are File Name: {}, Chunk No: {}, Chunks: {}", fileName, chunkNo, chunks);
			LOGGER.info("The size of Chunk DATA: {} ", chunkData.length );

			video = new Video();
			video.setChunk(chunkNo);
			video.setChunkData(chunkData);
			video.setChunkFileName(chunkName);
			video.setChunks(chunks);
			video.setFileName(fileName);

		}catch (IOException e){
			e.printStackTrace();
		}
		return video;
	}

}
