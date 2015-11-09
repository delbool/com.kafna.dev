package com.kana.dev.springboot.contacts.service;

import java.util.List;

import com.kana.dev.springboot.contacts.model.User;

public interface UserService {

	public List<User> getUsers();
	
	public void addUser(User user);
	
	public void deleteUser(User user);

	public User getAuthenticatedUser();
}
