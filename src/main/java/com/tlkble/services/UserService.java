package com.tlkble.services;

import java.util.List;


import com.tlkble.domain.User;

//------------------------------------------------------------- //
// --- Class of user services to access the user repository --- // 
//------------------------------------------------------------- //

public interface UserService {

	// user service impl methods
	void authorize(User user);

	// current logged in user
	User getCurrentLoginUser();

	// check the pass
	Boolean checkPassword(String rqPassword, String dbPassword);

	// save a user
	User register(User user);

	// exists by email
	Boolean existsByEmailAddress(String emailAddress);

	// exists by usernamame
	Boolean existsByUsername(String username);

	//get all users
	List<User> getAllUsers();
	
	//find by username
	User findByUsername(String username);

}
