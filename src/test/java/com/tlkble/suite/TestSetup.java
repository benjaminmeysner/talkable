package com.tlkble.suite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.tlkble.domain.User;
import com.tlkble.services.UserService;

@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
public class TestSetup {

	static MockMvc mockMvc;
	static User user;
	static ResultActions result;
	static String server = "http://localhost";

	@Autowired
	UserService userService;

	public User setup_user() {
		user = new User("John", "Doe", "email@domain.com", "jtd123", "B0bl1q4321!");
		return this.userService.register(user);
	}
}
