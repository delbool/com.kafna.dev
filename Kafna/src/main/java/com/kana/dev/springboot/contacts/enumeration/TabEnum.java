package com.kana.dev.springboot.contacts.enumeration;

public enum TabEnum {
	HOME("Home", "home",  "/home"), //
	DEVELOPMENT("Application Development", "development", "/development"),
	SERVICES("Services", "services", "/services"), //
	PROJECTS("Projects", "projects", "/projects"), //
	ABOUT("About Us", "about", "/about"), //
	CONTACT("Contact Us", "contact", "/contact");

	private String tabName;
	private String url;
	private String shortName;

	private TabEnum(String tabName, String shortName, String url) {
		this.tabName = tabName;
		this.shortName = shortName;
		this.url = url;
	}

	public String getTabName() {
		return tabName;
	}

	public String getUrl() {
		return url;
	}

	public static TabEnum getByTabName(String name) {
		for (TabEnum t : TabEnum.values()){
			if( t.shortName.equalsIgnoreCase(name)){
				return t;
			}
		}
		return null;
	}

}
