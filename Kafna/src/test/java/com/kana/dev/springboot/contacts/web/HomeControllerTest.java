package com.kana.dev.springboot.contacts.web;

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
import com.kana.dev.springboot.contacts.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { Application.class })
public class HomeControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Mock
	private UserService mockUserService;

	@InjectMocks
	@Autowired
	private HomeController homeController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLogin() throws Exception{
		User user = new User(1, "A", "b", 3, true);
		Mockito.when(mockUserService.getAuthenticatedUser()).thenReturn(user);
//		Mockito.doReturn(user).when(mockUserService).getAuthenticatedUser();

		mockMvc.perform(MockMvcRequestBuilders.get("/login") //
			.param("firstNameXX", "Abraham").param("layystName", "Tesfay") //
			.param("id", "1").param("age", "50"))  //
			.andExpect(MockMvcResultMatchers.status().isOk()) //
			.andExpect(MockMvcResultMatchers.view().name("loggedInPage")) //
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/tiles/loggedInPage.jsp"));
	}
}
