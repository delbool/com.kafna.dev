package com.kana.dev.springboot.contacts.model;

public class MenuItem {
	private boolean active;
	private String displayName;
	private String url;


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "MenuItem [active=" + active + ", displayName=" + displayName + ", url=" + url + "]";
	}

	
}
