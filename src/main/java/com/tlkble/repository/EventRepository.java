package com.tlkble.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tlkble.domain.Event;

/**
 *	Repository class for handling users
 * @author Ben
 */

@Repository
public interface EventRepository extends MongoRepository<com.tlkble.domain.Event, String> {
	Event findEventById(String id);
}