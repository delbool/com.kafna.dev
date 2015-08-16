package com.kana.dev.springboot.contacts.model;

public class Video {

	private String fileName;
	private String chunk;
	private String chunks;
	private String chunkFileName;
	private byte[] chunkData;
	private String userName;

	public Video() {
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getChunk() {
		return chunk;
	}

	public void setChunk(String chunk) {
		this.chunk = chunk;
	}

	public String getChunks() {
		return chunks;
	}

	public void setChunks(String chunks) {
		this.chunks = chunks;
	}

	public String getChunkFileName() {
		return chunkFileName;
	}

	public void setChunkFileName(String chunkFileName) {
		this.chunkFileName = chunkFileName;
	}


	public byte[] getChunkData() {
		return chunkData;
	}

	public void setChunkData(byte[] chunkData) {
		this.chunkData = chunkData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
