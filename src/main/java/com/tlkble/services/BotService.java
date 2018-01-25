package com.tlkble.services;

import org.springframework.stereotype.Component;

import com.tlkble.domain.Message;
import com.tlkble.domain.User;

@Component
public interface BotService {
	boolean combMessageForProfanity(Message message);
	String cloakProfaneMessage(Message message);
	boolean checkIfSpamMessage(User user);
	int checkMessageLength(Message message);
}
