package com.kana.dev.springboot.contacts.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kana.dev.springboot.contacts.enumeration.MenuItemEnum;
import com.kana.dev.springboot.contacts.enumeration.TabEnum;
import com.kana.dev.springboot.contacts.model.MenuItem;
import com.kana.dev.springboot.contacts.model.User;
import com.kana.dev.springboot.contacts.service.UserService;


@RestController
public class MenuController extends AbstractElssaController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class.getSimpleName());

	@Autowired UserService userService;
	
	@RequestMapping(value="{tab}/menus", method = RequestMethod.GET)
	public List<MenuItem> showMenus(Map<String, Object> model, @PathVariable("tab") String tab,
			@RequestParam(value = "selectedMenuItem", required = false) String selectedMenuItem) {
		LOGGER.info(">>>>... In {}.showMenus(..) method", this.getClass().getSimpleName());
		if (StringUtils.isBlank(tab)) {
			throw new RuntimeException("Tab name is missing.");
		}

		TabEnum tabEnum = TabEnum.getByTabName(tab);
		List<MenuItemEnum> menus = MenuItemEnum.getMenusForTab(tabEnum);
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		//Map<String, String> menuItems = new LinkedHashMap<String, String>();
		//menuItems.put("menusFound", menus.size()+"");
		boolean menuItemSelected = false;
		for ( MenuItemEnum mie : menus){
			MenuItem item = new MenuItem();
			item.setDisplayName(mie.getDisplayName());
			item.setUrl(mie.getUrl());
			if ( StringUtils.isNotBlank(selectedMenuItem) && selectedMenuItem.equals(item.getUrl()) ){
				menuItemSelected = true;
				item.setActive(true);
			}
			
			//TODO:  use the ENUM itself instead
			menuItems.add(item);
			LOGGER.info("DisplayName: {},  URL: {}", item.getDisplayName(), item.getUrl());
		}
		if (! menuItemSelected  ){
			menuItems.get(0).setActive(true);
		}
		LOGGER.info("Number of menu items found: " + menuItems.size());

		return menuItems;
	}
	
	@RequestMapping(value="{tab}/menus/{menuItem}", method = RequestMethod.GET)
	public Map<String, String> getDocument(Map<String, Object> model, @PathVariable("tab") String tab, 
			@PathVariable("menuItem") String menuItem, HttpServletRequest request) {
		LOGGER.info(">>>>... In {}.getDocument(..) method", this.getClass().getSimpleName());
		if ( StringUtils.isEmpty(tab)){
			throw new RuntimeException("Tab name is missing.");
		}
		
		if ( StringUtils.isEmpty(menuItem)){
			throw new RuntimeException("Menu Item is missing.");
		}
		
		List<User> users = userService.getUsers();
		
		Map<String, String> content = new HashMap<String, String>();
		content.put("content", tab + " |  menus " + "  | " + menuItem);

		return content;
	}
	

}
