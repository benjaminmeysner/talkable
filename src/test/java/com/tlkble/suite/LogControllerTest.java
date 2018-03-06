package com.tlkble.suite;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class LogControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				/* Applying Spring Security */
				.apply(springSecurity()).build();
	}

	@Test
	public void log_returns_correct_view() throws Exception {
		//mockMvc.perform(get("/user/log").with(user("jtd123").password("B0bl1q4321"))).andExpect(view().name("log"))
			//	.andExpect(status().isOk());
	}

}
