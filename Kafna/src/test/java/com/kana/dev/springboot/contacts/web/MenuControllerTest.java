package com.kana.dev.springboot.contacts.web;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kana.dev.springboot.contacts.appconfig.Application;
import com.kana.dev.springboot.contacts.model.User;
import com.kana.dev.springboot.contacts.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
@WebAppConfiguration
public class MenuControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Mock
	private UserServiceImpl mockUserService;

	@InjectMocks
	@Autowired
	private MenuController menuController;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetDocument() throws Exception {
		List<User> users = generateTestUsers();
//		Mockito.doReturn(users.get(0)).when(mockUserService).getAuthenticatedUser();
		Mockito.when(mockUserService.getAuthenticatedUser()).thenReturn(users.get(0));
		Mockito.when(mockUserService.getUsers()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.post("/tab/menus/home")).andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		mockMvc.perform(MockMvcRequestBuilders.get("/tab/menu/home")).andExpect(MockMvcResultMatchers.status().isNotFound());
		mockMvc.perform(MockMvcRequestBuilders.get("/tab/menus/home")).andExpect(MockMvcResultMatchers.status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/{tab}/menus/{menu}", "myTab", "myMenu")).andExpect(MockMvcResultMatchers.status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/anything/menus/anywhere")) //
			.andExpect(MockMvcResultMatchers.status().isOk()) //
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
	}

	private List<User> generateTestUsers() {
		User user1 = new User(1, "Abraham", "Tesfay", 50, true);
		User user2 = new User(2, "Selam", "Tesfay", 17, false);
		return Arrays.asList(user1, user2);
	}

}
