package com.tlkble.suite;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Testing the web socket endpoints
 * @author Ben
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class WebsocketEndpointTest extends TestSetup {
	
    private static final String SEND_MESSAGE_ENDPOINT = "/app/create/";
    private static final String SUBSCRIBE_EVENT = "/topic/board/";
    private String URL;

	@Autowired
	WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		URL = "ws://localhost:" + 8080 + "/event";
	}


	/**
	 * Endpoint tests
	 * @throws Exception
	 */
	@Test
	public void _testing_websocket_endpoints() throws Exception {

		
	}

    
    
}