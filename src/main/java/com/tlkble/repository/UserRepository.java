package com.tlkble.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tlkble.domain.User;

/**
 *	Repository class for handling users
 * @author Ben
 */

public interface UserRepository extends MongoRepository<com.tlkble.domain.User, String> {
	
	com.tlkble.domain.User findByEmailAddress(String emailAddress);
	com.tlkble.domain.User findByEmailAddressIgnoreCase(String emailAddress);
	com.tlkble.domain.User findByUsernameIgnoreCase(String username);
	List<com.tlkble.domain.User> findAll();
	User findByUsername(String username);
}