package com.tlkble.suite;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tlkble.domain.User;

/**
 * Register controller Redirection Test
 * 
 * @author Ben
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class RegisterControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * User Redirection - Redirection on successful registration
	 * 
	 * @throws Exception
	 */
	@Test
	public void _once_user_registers_they_should_log_in_automatically() throws Exception {
		mockMvc.perform(post("/").flashAttr("user", new User()))
				.andExpect(redirectedUrl("/user/home")).andExpect(status().is3xxRedirection());
	}
}