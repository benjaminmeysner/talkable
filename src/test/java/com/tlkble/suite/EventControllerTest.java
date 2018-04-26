package com.tlkble.suite;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.tlkble.domain.Event;
import com.tlkble.domain.Message;
import com.tlkble.domain.User;
import com.tlkble.repository.EventRepository;

/**
 * =============================================================================
 * Testing the Event Controller
 * =============================================================================
 * some of the tests will be selenium tests, see EventController_FE_SEL_Test.
 * =============================================================================
 * 1. Unauthorised users tests *MULTIPLE TESTS* 3. Authorized users can create
 * an event 4. Authorized users can join an event 5. An event title which is
 * left blank is renamed to "Quick Event" 6. An event with no description is
 * renamed to "This is a quick event" 7. An authorised user who created an
 * event, can end it 8. An authorised user who hasn't created an event cannot
 * end it 9. An authoised user can send a message to an event *MULTIPLE TESTS*
 * 10. .. Another user can view somebody else's message (Selenium) 11. Blank
 * messages are not sent 12. Multiple, messages sent in a short time are not
 * sent 13. A message which contains an expletive is not sent and a warning is
 * sent to the user *MULTIPLE TESTS* 14. A message which is a response string of
 * the creator, triggers a bot to issue a prerecorded response *MULTIPLE TESTS*
 * 15. Message lengths 16. Test active event counter
 * 
 * inclusive of spring security
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

	@Autowired
	EventRepository eventRepo;

	@BeforeClass
	public static void configure() {
	}

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
		eventRepo.deleteAll();

	}

	/**
	 * Test 1 - User Redirection - If a user which is not logged in, tries to view
	 * an event via a URL such as "/event/{eventId}" then he/she is redirected back
	 * to the login
	 * 
	 * @throws Exception
	 */
	@Test
	public void _eventpage_to_return_login_if_user_is_not_logged_in() throws Exception {
		mockMvc.perform(get("/event/ABC123").with(csrf())).andExpect(redirectedUrl(server + "/login"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void _eventpage_to_return_login_if_user_is_not_logged_in_1() throws Exception {
		mockMvc.perform(get("/event/").with(csrf())).andExpect(redirectedUrl(server + "/login"))
				.andExpect(status().is3xxRedirection());
	}

	/**
	 * Test 2 - Selenium Test
	 */

	/**
	 * Test 3 - When a user is logged in, they are able to join an event which they
	 * may or may not be the creator of. For this case, we will set the event Id
	 * manually as this is generated on the front-end and needs to be tested with
	 * selenium.
	 */
	@Test
	@WithMockUser
	public void _authorised_users_can_join_an_event() throws Exception {

		Event new_event = new Event();
		new_event.setId("ABC123");
		eventService.createEvent(new_event);

		// mockMvc.perform(get("/event/ABC123").with(csrf())).andExpect(redirectedUrl(server
		// + "/event/ABC123"))
		// .andExpect(status().is3xxRedirection());
	}

	/**
	 * Test 4 - If an event has been created it can be ended
	 */
	@Test
	public void _an_event_which_has_been_created_can_be_ended() throws Exception {
		// TO-DO
	}

	/**
	 * Test 5 - User cannot join an event after it has been ended
	 */
	@Test
	public void _a_user_cannot_join_an_event_after_it_has_been_ended() throws Exception {
		// TO-DO
	}

	/**
	 * Test 13 - Check messages contain expletives, part 1
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_expletives_are_detected_1() throws Exception {
		// No Expletive
		message = new Message("user", "I am  a long message, I do not contain a swear word though.");
		assertThat(botservice.combMessageForProfanity(message), is(false));
	}

	/**
	 * The detection of an expletive surrounded by space characters
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_expletives_are_detected_2() throws Exception {

		// A Single expletive, surrounded by space characters
		message = new Message("user",
				"I am  a long message, I do contain a swear word though. The swear word I will use is the word shit a pretty common word used.");
		assertThat(botservice.combMessageForProfanity(message), is(true));
	}

	@Test
	public void _check_expletives_are_detected_3() throws Exception {
		// A Single expletive, followed by a comma character, and a full stop character
		message = new Message("user",
				"I am  a long message, I do contain a swear word though,shit. The swear word I use is a pretty common word.");
		assertThat(botservice.combMessageForProfanity(message), is(true));
	}

	/**
	 * Test 13 - Checking that expletives are cloaked, part 2
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_expletives_are_cloaked_but_first_character_and_last_are_not() throws Exception {
		message = new Message("user", "shit");
		assertThat(botservice.cloakProfaneMessage(message), is("s**t"));

		message = new Message("user", "bastard");
		assertThat(botservice.cloakProfaneMessage(message), is("b*****d"));

		message = new Message("user", "No profanity here");
		assertThat(botservice.cloakProfaneMessage(message), is("No profanity here"));
	}

	/**
	 * Test 15 - Checking the message length is correct
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_message_length_is_0() throws Exception {
		message = new Message("user", "");
		assertThat(botservice.checkMessageLength(message), is(0));
	}

	/**
	 * Test 15b - Checking message lengths are still correct
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_message_length_is_17() throws Exception {
		message = new Message("user", "I am of length 17");
		assertThat(botservice.checkMessageLength(message), is(17));
	}

	/**
	 * Test 15c - Checking message lengths are still correct
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_message_length_is_4() throws Exception {
		message = new Message("user", "four");
		assertThat(botservice.checkMessageLength(message), is(4));
	}

	/**
	 * With an empty repository we should have its size set at 0 and active events should be 0
	 * @throws Exception
	 */
	@Test
	public void _active_events_counter_shows_0() throws Exception {
		assertThat(eventRepo.findAll().size(), is(0));
		assertThat(eventService.activeEvents(), is(0));
	}
	
	/**
	 * With 1 newly created & active event, repo should be 1 and active event count should be 1 and inactive event set to zero
	 * @throws Exception
	 */
	@Test
	public void _active_events_counter_shows_1() throws Exception {
		Event e = new Event();
		e.setAlive(true);
		eventService.createEvent(e);
		assertThat(eventRepo.findAll().size(), is(1));
		assertThat(eventService.activeEvents(), is(1));
		assertThat(eventService.inactiveEvents(), is(0));
	}
	
	/**
	 * A more extravagant/complex test probing the size/count
	 * @throws Exception
	 */
	@Test
	public void _active_events_counter_shows_high_number() throws Exception {
		
		Event e;
		User u;
		
		// Create and add 20 event instances
		for(int i = 0; i < 20; ++i) {
			e = new Event();
			u = new User();
			u.setEmailAddress(String.valueOf(i+"...com"));
			e.getUsers().add(u.getEmailAddress());
			e.setAlive(true);
			e.setId(String.valueOf(i));
			eventService.createEvent(e);
		}

		assertThat(eventRepo.findAll().size(), is(20));
		assertThat(eventService.activeEvents(), is(20));
		
		// Remove 5 events
		for(int k = 5; k < 10; ++k)
			eventRepo.delete(eventService.findEventById(String.valueOf(k)));
		
		assertThat(eventRepo.findAll().size(), is(15));
		assertThat(eventService.activeEvents(), is(15));

	}
	
	/**
	 * With 1 newly created & inactive event, repo should be 1 and active event count should be 1
	 * @throws Exception
	 */
	@Test
	public void _inactive_events_counter_shows_1() throws Exception {
		Event e = new Event();
		e.setAlive(false);
		eventService.createEvent(e);
		assertThat(eventRepo.findAll().size(), is(1));
		assertThat(eventService.activeEvents(), is(0));
	}
	
	

}