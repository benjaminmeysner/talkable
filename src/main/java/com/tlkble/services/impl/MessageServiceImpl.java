package com.tlkble.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlkble.domain.OutputMessage;
import com.tlkble.repository.MessageRepository;
import com.tlkble.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired MessageRepository messageRepository;

	@Override
	public void saveMessage(OutputMessage om) {
		messageRepository.save(om);	
	}

}
