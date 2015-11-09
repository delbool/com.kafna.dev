package com.kana.dev.springboot.contacts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kana.dev.springboot.contacts.model.User;
import com.kana.dev.springboot.contacts.service.UserService;

public class AbstractElssaController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public User getAuthenticatedUser(){		
		return userService.getAuthenticatedUser();
	}
}
