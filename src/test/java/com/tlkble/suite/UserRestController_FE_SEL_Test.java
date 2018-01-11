package com.tlkble.suite;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContextTest.xml")
@WebAppConfiguration
public class UserRestController_FE_SEL_Test extends TestCase {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@SuppressWarnings("unused")
	private MockMvc mockMvc;

	@Before
	@Override
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
		
		super.setUp();
	}

	//@Test
	//public void testWeSeeRegisterPage() {
	//	webDriver().get("http://localhost:8080/register");
	//	//assertTrue(webDriver().getPageSource().contains("register"));
	//}

	@Bean
	@Scope("test")
	public WebDriver webDriver() {
		return new ChromeDriver();
	}

	@After
	public void quitBrowser() {
		webDriver().quit();
	}

}