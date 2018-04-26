package com.tlkble.suite;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tlkble.domain.Event;
import com.tlkble.domain.OutputMessage;
import com.tlkble.repository.EventRepository;
import com.tlkble.services.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class EventRestControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	EventService eventService;

	@Autowired
	EventRepository eventRepo;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		eventRepo.deleteAll();
	}

	/**
	 * Check null value for event that doesnt exist
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_event_object_response_is_null_for_bad_id() throws Exception {
		mockMvc.perform(get("/event/checkevent?eventid=wrong")).andExpect(jsonPath("$.id").doesNotExist())
				.andExpect(jsonPath("$.eventTitle").doesNotExist())
				.andExpect(jsonPath("$.eventDescription").doesNotExist());
	}

	/**
	 * Test basic Rest API for Existing Event
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_event_object_response_is_not_null_1() throws Exception {
		Event e = new Event("ABC123", "Quick Event", "Quick Description");
		eventService.createEvent(e);

		mockMvc.perform(get("/event/checkevent?eventid=ABC123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("ABC123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.event_title").value("Quick Event"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.event_description").value("Quick Description"));
	}

	/**
	 * Test Rest API for more complex event
	 * 
	 * @throws Exception
	 */
	@Test
	public void _check_event_object_response_is_not_null_2_more_complex() throws Exception {
		Event e = new Event("ABC123", "Quick Event", "Quick Description");

		OutputMessage om;
		for (int i = 0; i < 6; ++i) {
			om = new OutputMessage("bm234", "message #" + i, String.valueOf(i));
			e.getMessages().add(om);
		}

		eventService.createEvent(e);

		mockMvc.perform(get("/event/checkevent?eventid=ABC123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("ABC123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.event_title").value("Quick Event"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.event_description").value("Quick Description"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages").isArray())
				// Message 1
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message").value("message #0"))
				// Message 2
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].message").value("message #1"))
				// Message 3
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[2].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[2].message").value("message #2"))
				// Message 3
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[3].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[3].message").value("message #3"))
				// Message 4
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[4].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[4].message").value("message #4"))
				// Message 5
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[5].author").value("bm234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[5].message").value("message #5"));
	}

}
