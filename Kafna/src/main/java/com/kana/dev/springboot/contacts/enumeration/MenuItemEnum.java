package com.kana.dev.springboot.contacts.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum MenuItemEnum {
	
	// Home menu items
	HOME(TabEnum.HOME, "Home", "/home/menus/home"),
	//BIG_DATA(TabEnum.HOME, "Big Data", "/home/menus/bigdata"),
	CSS(TabEnum.HOME, "CSS", "/home/menus/css"),
	HTML(TabEnum.HOME, "HTML", "/home/menus/html"),
	JAVA(TabEnum.HOME, "Java Programming", "/home/menus/java"),
	JAVA_SCRIPT(TabEnum.HOME, "JavaScript Programming", "/home/menus/js"),
	XML_SCHEMA(TabEnum.HOME, "XML Schema", "/home/menus/xmlschema"),
	
	// Development menu items
	WEBSITE(TabEnum.DEVELOPMENT, "Website Design", "/development/menus/website"),
	ENTERPRISE_APPS(TabEnum.DEVELOPMENT, "Enterprise Applications", "/development/menus/enterprise"),
	RDBMS(TabEnum.DEVELOPMENT, "Relational Databases", "/development/menus/rdbms"),
	NO_SQL(TabEnum.DEVELOPMENT, "NO SQL Databases", "/development/menus/nosql"),
	BIG_DATA(TabEnum.DEVELOPMENT, "Big Data", "/development/menus/bigdata");
	
	private TabEnum tabEnum;
	private String displayName;
	private String url;

	private MenuItemEnum(TabEnum tabEnum, String displayName, String url) {
		this.tabEnum = tabEnum;
		this.displayName = displayName;
		this.url = url;
	}

	public TabEnum getTabEnum() {
		return tabEnum;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getUrl() {
		return url;
	}

	public static List<MenuItemEnum> getMenusForTab(TabEnum te) {
		List<MenuItemEnum> menuItems = new ArrayList<MenuItemEnum>();
		for (MenuItemEnum item : MenuItemEnum.values()){
			if(item.getTabEnum() == te){
				menuItems.add(item);
			}
		}
		return menuItems;
	}
}
