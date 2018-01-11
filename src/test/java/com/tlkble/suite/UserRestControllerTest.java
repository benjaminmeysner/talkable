package com.tlkble.suite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
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

import com.tlkble.domain.User;
import com.tlkble.repository.UserRepository;
import com.tlkble.services.UserService;

/**
 * Integration tests of User, UserService, UserServiceImpl, UserRepository,
 * Testing: Back-end validation checks are in place.
 * 
 * @author Ben
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@WebAppConfiguration
public class UserRestControllerTest extends TestSetup {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		userRepository.deleteAll();
		assertThat(userRepository.count()).isEqualTo(0);
	}

	/**
	 * Complete user credentials Test complete user instance with full fields,
	 * should expect (1)
	 * 
	 * @throws Exception
	 */
	@Test
	public void complete_user() throws Exception {
		user = new User("John", "Doe", "email@domain.com", "jtd123", "B0bl1q4321!");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(1);
		assertThat(response).isNotNull();
	}

	/**
	 * User with missing firstname, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_has_missing_first_name() throws Exception {
		user = new User("", "Doe", "email@domain.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with missing lastname, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_has_missing_last_name() throws Exception {
		user = new User("John", "", "email@domain.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with missing emailaddress, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_has_missing_email_address() throws Exception {
		user = new User("John", "Doe", "", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User has filled out the form with white spaces instead of alphabetic,
	 * numeric, or special characters ============================= input: first
	 * name = " "; = invalid input: first name = " ben "; = invalid input: first
	 * name = " ben"; = invalid input: first name = "ben "; = invalid applying to
	 * fields: firstname, lastname, username, email, password
	 * 
	 * @throws Exception
	 */
	@Test
	public void fname_contains_white_spaces() throws Exception {
		user = new User(" John", "Doe", "email@domain.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	@Test
	public void fname_contains_white_spaces_1() throws Exception {
		user = new User("John ", "Doe", "email@domain.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	@Test
	public void uname_contains_white_spaces() throws Exception {
		user = new User("John ", "Doe", "email@domain.com", " jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	@Test
	public void uname_contains_white_spaces_1() throws Exception {
		user = new User("John ", "Doe", "email@domain.com", "jtd123 ", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_1() throws Exception {
		user = new User("John", "Doe", "email", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_2() throws Exception {
		user = new User("John", "Doe", "email@", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_3() throws Exception {
		user = new User("John", "Doe", "@email", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_4() throws Exception {
		user = new User("John", "Doe", "email@.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_5() throws Exception {
		user = new User("John", "Doe", "@domain.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with bad email address format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_email_address_wrong_format_6() throws Exception {
		user = new User("John", "Doe", "@.com", "jtd123", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with missing username, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_has_missing_username() throws Exception {
		user = new User("John", "Doe", "email@domain.com", "", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect format, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void username_is_incorrect_format() throws Exception {
		user = new User("John", "Doe", "email@domain.com", "a", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect format, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void username_is_incorrect_format_1() throws Exception {
		user = new User("John", "Doe", "email@domain.com", "1abcd", "MyP455w0rd.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with missing password, check response is null and repository is empty
	 * expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void user_has_missing_password() throws Exception {
		user = new User("John", "Doe", "email@domain.com", "bm234", "");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect password format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void password_is_incorrect_format() throws Exception {
		/**
		 * Special Character Testing with no special character
		 */
		user = new User("John", "Doe", "email@domain.com", "bm234", "MyP455w0");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect password format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void password_is_incorrect_format_1() throws Exception {
		/**
		 * Password too short Testing with password too short character
		 */
		user = new User("John", "Doe", "email@domain.com", "bm234", "MyP4.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect password format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void password_is_incorrect_format_2() throws Exception {
		/**
		 * Absence of numeric Testing with no numeric character
		 */
		user = new User("John", "Doe", "email@domain.com", "bm234", "MyPassword.");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * User with incorrect password format, check response is null and repository is
	 * empty expect(0)
	 * 
	 * @throws Exception
	 */
	@Test
	public void password_is_incorrect_format_3() throws Exception {
		/**
		 * Absence of alphabetic Testing with no alphabetic character
		 */
		user = new User("John", "Doe", "email@domain.com", "bm234", "12345678!");
		Object response = userService.register(user);
		assertThat(userRepository.count()).isEqualTo(0);
		assertThat(response).isNull();
	}

	/**
	 * Test to find a user by its username and return it
	 * 
	 * @throws Exception
	 */
	@Test
	public void find_user_by_name() throws Exception {

		/* Setup a new user */
		setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);
		/* perform get on the url users/{username} and return JSON response */
		mockMvc.perform(get("/users/jtd123")).andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("John"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Doe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email_address").value("email@domain.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("jtd123"));
	}

	/**
	 * Test for probing the AJAX method,
	 * backend test
	 * @throws Exception
	 */
	@Test
	public void find_user_by_name_which_doesnt_exist() throws Exception {

		/* Setup a new user */
		setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);
		/* perform get on the url users/{username} and return JSON response */
		mockMvc.perform(get("/users/non_exist")).andExpect(jsonPath("$.first_name").doesNotExist())
				.andExpect(jsonPath("$.last_name").doesNotExist()).andExpect(jsonPath("$.email_address").doesNotExist())
				.andExpect(jsonPath("$.username").doesNotExist());
	}

	/**
	 * Test for probing the AJAX method,
	 * backend test
	 * @throws Exception
	 */
	@Test
	public void test_AJAX_email_check_on_a_user_which_exists_response_entity() throws Exception {

		/* Setup a new user */
		User user = setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);

		/* perform get on the url /register/emailCheck and return HTTP Response entity */
		/* User should already exist so a badRequest should be the expected response */
		mockMvc.perform(get("/register/emailCheck?emailAddress=" + user.getEmailAddress()))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test for probing the AJAX method,
	 * backend test
	 * @throws Exception
	 */
	@Test
	public void test_AJAX_email_check_on_a_user_which_doesnt_exist_response_entity_2() throws Exception {

		/* Setup a new user */
		User user = setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);

		/* perform get on the url /register/emailCheck and return HTTP Response entity */
		/* User should not already exist so OK should be the expected response */
		mockMvc.perform(get("/register/emailCheck?emailAddress=" + StringUtils.reverse(user.getEmailAddress())))
				.andExpect(status().isOk());
	}
	
	/**
	 * Test for probing the AJAX method,
	 * backend test
	 * @throws Exception
	 */
	@Test
	public void test_AJAX_username_check_on_a_user_which_exists_response_entity() throws Exception {

		/* Setup a new user */
		User user = setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);

		/* perform get on the url /register/usernameCheck and return HTTP Response entity */
		/* User should already exist so a badRequest should be the expected response */
		mockMvc.perform(get("/register/usernameCheck?username=" + user.getUsername()))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test for probing the AJAX method,
	 * backend test
	 * @throws Exception
	 */
	@Test
	public void test_AJAX_username_check_on_a_user_which_doesnt_exist_response_entity_2() throws Exception {

		/* Setup a new user */
		User user = setup_user();
		/* Assert that the repository is not empty */
		assertThat(userRepository.count()).isEqualTo(1);

		/* perform get on the url /register/usernameCheck and return HTTP Response entity */
		/* User should not already exist so an OK should be the expected response */
		mockMvc.perform(get("/register/usernameCheck?username=" + StringUtils.reverse(user.getUsername())))
				.andExpect(status().isOk());
	}
}