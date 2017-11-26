package com.tlkble.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *	Repository class for handling users
 * @author Ben
 */

public interface UserRepository extends MongoRepository<com.tlkble.domain.User, String> {
	
	//Find by a username - case sensitive
	com.tlkble.domain.User findByUsername(String username);
	
	//Find by a username - case sensitive
	com.tlkble.domain.User findByEmailAddress(String emailAddress);
	
	//find by an email address
	com.tlkble.domain.User findByEmailAddressIgnoreCase(String emailAddress);
	
	//find by a username
	com.tlkble.domain.User findByUsernameIgnoreCase(String username);
	
	//Return all Users
	List<com.tlkble.domain.User> findAll();
	
	
}