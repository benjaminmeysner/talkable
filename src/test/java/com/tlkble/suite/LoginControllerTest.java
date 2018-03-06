package com.tlkble.suite;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Login controllers Redirection Tests
 * 
 * @author Ben
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class LoginControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;

	@BeforeClass
	public static void configure() {
		/* Not needed */ }

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				/* Applying Spring Security */
				.apply(springSecurity()).build();
	}

	/**
	 * User Redirection - If user is not logged in he/she should be redirected to
	 * /login on any URL attempt
	 * 
	 * @throws Exception
	 */
	@Test
	public void _anypage_to_return_login_if_user_is_not_logged_in() throws Exception {
		mockMvc.perform(get("/user/home").with(csrf())).andExpect(redirectedUrl(server + "/login"))
				.andExpect(status().is3xxRedirection());
	}

	/**
	 * User Redirection - If user is logged in he/she should not be able to log in
	 * again and /login shouldnt return the login view
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void _login_page_to_return_user_home_if_is_logged_in() throws Exception {
		mockMvc.perform(get("/login").with(csrf())).andExpect(redirectedUrl("/user/home"))
				.andExpect(status().is3xxRedirection());
	}

	/**
	 * Testing the succesfu login of a user
	 * 
	 * @throws Exception
	 */
	@Test
	public void good_login_credentials_1_() throws Exception {
		mockMvc.perform(get("/login").with(user("jtd123").password("B0bl1q4321")))
				.andExpect(redirectedUrl("/user/home")).andExpect(status().is3xxRedirection());
	}

	/**
	 * Testing the unsuccesful login of an unknown user along with model attribute
	 * 
	 * @throws Exception
	 */
	@Test
	public void bad_login_credentials_1_username() throws Exception {
		mockMvc.perform(formLogin("/user/login").user("i_dont_exist").password("wrong_pass"))
				.andExpect(redirectedUrl("/login")).andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("loginError")).andExpect(flash().attribute("loginError",
						"Username or email address could be not found, please note, you can log into your account with either your email address or your username"));
	}

	/**
	 * Testing the unsuccesful login of a user with missing fields attribute
	 * 
	 * @throws Exception
	 */
	@Test
	public void bad_login_credentials_1_missing_fields() throws Exception {
		mockMvc.perform(formLogin("/user/login").user("").password("")).andExpect(redirectedUrl("/login"))
				.andExpect(status().is3xxRedirection()).andExpect(flash().attributeExists("loginError"))
				.andExpect(flash().attribute("loginError",
						"There are missing fields, you have failed to provide a username, email address or a password, please note, you can log into your account with either your email address or your username"));
	}

	/**
	 * Testing the unsuccesful login of a user with wrong password along with model
	 * attribute
	 * 
	 * @throws Exception
	 */
	@Test
	public void bad_login_credentials_1_password() throws Exception {
		mockMvc.perform(formLogin("/user/login").user("jtd123").password("wrong_pass"))
				.andExpect(redirectedUrl("/login")).andExpect(status().is3xxRedirection())
				.andExpect(flash().attributeExists("loginError")).andExpect(flash().attribute("loginError",
						"Username or email address could be not found, please note, you can log into your account with either your email address or your username"));
	}

	/**
	 * Register view
	 * 
	 * @throws Exception
	 */
	@Test
	public void register_to_return_view_register() throws Exception {
		mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(view().name("register_"));
	}

	/**
	 * Testing the log out
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void logout_redirects_to_login_page() throws Exception {
		mockMvc.perform(get("/user/logout").with(csrf())).andExpect(redirectedUrl("/login"))
				.andExpect(status().is3xxRedirection());
	}
}