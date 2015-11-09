package com.kana.dev.springboot.contacts.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kana.dev.springboot.contacts.enumeration.MenuItemEnum;
import com.kana.dev.springboot.contacts.enumeration.TabEnum;
import com.kana.dev.springboot.contacts.model.User;
import com.kana.dev.springboot.contacts.service.UserService;

@Controller
@RequestMapping(value = { "/", "/home" })
public class HomeController extends AbstractElssaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class.getSimpleName());

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(">>>>... In {}.home(..) method", this.getClass().getSimpleName());
		TabEnum[] tabs = TabEnum.values();
		List<MenuItemEnum> menus = MenuItemEnum.getMenusForTab(TabEnum.HOME);
		model.put("tabs", tabs);
		model.put("menus", menus);
		model.put("selectedTab", TabEnum.HOME);
		return "home";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Map<String, Object> model, @ModelAttribute User user, //
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(">>>>... In {}.login(..) method", this.getClass().getSimpleName());
		
		User authenticatedUser = userService.getAuthenticatedUser();
		TabEnum[] tabs = TabEnum.values();
		List<MenuItemEnum> menus = MenuItemEnum.getMenusForTab(TabEnum.HOME);
		model.put("tabs", tabs);
		model.put("menus", menus);
		model.put("selectedTab", TabEnum.HOME);
		model.put("authenticatedUser", authenticatedUser);
		if ( authenticatedUser == null ){
			return "login";
		}
		return "loggedInPage";
	}

}
