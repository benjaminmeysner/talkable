package com.tlkble.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tlkble.domain.User;


/**
 * UserService
 * @author Ben
 * @issues none
 */

@Component
public interface UserService {

	User register(User user);

	Boolean existsByEmailAddress(String emailAddress);

	Boolean existsByUsername(String username);

	List<User> getAllUsers();

	User findByUsername(String username);

	User findByEmailAddress(String userOrEmail);

	Boolean checkPassword(String rqPassword, String dbPassword);

	void authorize(User user);
	
	void update(User user);
	
	User getCurrentUser();
}
