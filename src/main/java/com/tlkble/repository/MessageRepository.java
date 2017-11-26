package com.tlkble.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tlkble.domain.OutputMessage;

public interface MessageRepository extends MongoRepository<com.tlkble.domain.OutputMessage, String> {
	
	//get a message by its id
	OutputMessage findOutputMessageById(String id);

}
