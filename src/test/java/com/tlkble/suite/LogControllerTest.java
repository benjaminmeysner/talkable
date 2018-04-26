package com.tlkble.suite;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tlkble.domain.Event;
import com.tlkble.services.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class LogControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	EventService eventService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				/* Applying Spring Security */
				.apply(springSecurity()).build();
	}

	/**
	 * View test
	 * @throws Exception
	 */
	@Test
	public void log_returns_correct_view() throws Exception {
		//mockMvc.perform(get("/user/log").with(user("jtd123").password("B0bl1q4321"))).andExpect(view().name("log"))
			//	.andExpect(status().isOk());
	}
	
	
	public void user_joining_an_event_increases_joined_list_by_1() throws Exception {
		
		Event e = new Event("ABC123", "Quick Event", "Quick Description");
		e.setAlive(true);
		eventService.createEvent(e);
		
		mockMvc.perform(get("/event/ABC123").with(user("jtd123").password("B0bl1q4321")))
		.andExpect(status().isOk()).andExpect(view().name("event"));
	}

}
