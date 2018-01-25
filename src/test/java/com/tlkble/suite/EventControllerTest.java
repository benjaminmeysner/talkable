package com.tlkble.suite;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



/**=============================================================================
 * 						Testing the Event Controller 							|
 * =============================================================================|																				
 *  some of the tests will be selenium tests, see EventController_FE_SEL_Test.	|
 * =============================================================================|
 * 1. Unauthorised users tests	*MULTIPLE TESTS*								|
 * 3. Authorized users can create an event										|
 * 4. Authorized users can join an event										|
 * 5. An event title which is left blank is renamed								|
 * to "Quick Event"																|
 * 6. An event with no description is renamed to								|
 * "This is a quick event"														|
 * 7. An authorised user who created an event, can end it						|
 * 8. An authorised user who hasn't created an event cannot end it				|
 * 9. An authoised user can send a message to an event *MULTIPLE TESTS*			|
 * 10. .. Another user can view somebody else's message (Selenium)				|
 * 11. Blank messages are not sent												|
 * 12. Multiple, messages sent in a short time are not sent						|
 * 13. A message which contains an expletive is not sent and a warning			|
 * is sent to the user *MULTIPLE TESTS*											|
 * 14. A message which is a response string of the creator, triggers			|
 * a bot to issue a prerecorded response *MULTIPLE TESTS*						|
 * 																				|
 * inclusive of spring security													|
 * ==============================================================================
 * 
 * @author Ben
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class EventControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;

	@BeforeClass
	public static void configure() { /* Not needed */ }

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				/* Applying Spring Security */
				.apply(springSecurity()).build();
	}

	/** 1
	 * User Redirection - If a user which is not logged in, tries to view an event
	 * via a URL such as "/event/{eventId}" then he/she is redirected back to the 
	 * login 
	 * 
	 * @throws Exception
	 */
	@Test
	public void _eventpage_to_return_login_if_user_is_not_logged_in() throws Exception {
		mockMvc.perform(get("/event/ABC123")
				.with(csrf()))
				.andExpect(redirectedUrl(server + "/login"))
					.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void _eventpage_to_return_login_if_user_is_not_logged_in_1() throws Exception {
		mockMvc.perform(get("/event/")
				.with(csrf()))
				.andExpect(redirectedUrl(server + "/login"))
					.andExpect(status().is3xxRedirection());
	}
}