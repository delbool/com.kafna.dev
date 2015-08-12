package com.kana.dev.springboot.contacts.web;

public class BulkUpload {

	private int chunk;
	private int chunks;
	private String name;
	private String user;
	private String time;

	public int getChunk() {
		return chunk;
	}

	public void setChunk(final int chunk) {
		this.chunk = chunk;
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(final int chunks) {
		this.chunks = chunks;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

}
