package com.kana.dev.springboot.contacts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kana.dev.springboot.contacts.model.User;

@Service
public class UserServiceImpl implements UserService {

	private static final List<User> USER_LIST = new ArrayList<User>();
	private static int idCounter = 0;
	static {
		USER_LIST.add(new User(++idCounter, "Abraham", "Tesfay", 45, true));
		USER_LIST.add(new User(++idCounter, "Luwam", "Tesfay", 22, true));
		USER_LIST.add(new User(++idCounter, "Seyoum", "Tesfay", 18, true));
		USER_LIST.add(new User(++idCounter, "Selam", "Tesfay", 14, false));
		USER_LIST.add(new User(++idCounter, "Elizabeth", "Gebremedhin", 35, false));
	}
	
	@Override
	public List<User> getUsers() {
		return USER_LIST;
	}

	@Override
	public void addUser(User user) {
		USER_LIST.add(user);
		
	}

	@Override
	public void deleteUser(User user) {
		USER_LIST.remove(user);
	}

	@Override
	public User getAuthenticatedUser() {
		throw new RuntimeException("Method not yet implemented");
	}

}
