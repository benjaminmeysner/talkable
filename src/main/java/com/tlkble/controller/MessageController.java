package com.tlkble.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.tlkble.domain.Message;
import com.tlkble.domain.OutputMessage;
import com.tlkble.domain.User;
import com.tlkble.services.MessageService;
import com.tlkble.services.UserService;

/**
 * Controller handling the communication / messaging using STOMP and SockJS over
 * lower WebSocket API
 * 
 * @author Ben
 */

@Controller
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;

	@MessageMapping("/events/{eventId}")
	@SendTo("/topic/{eventId}")
	public OutputMessage send(@DestinationVariable String eventId, Message message, Principal principal) throws Exception {
		
		String time = new SimpleDateFormat("HH:mm").format(new Date());
				
		/* Set author */
		User user = (User) ((Authentication) principal).getPrincipal();
		OutputMessage returned_message = null;
		if(user!=null) { 
			returned_message = new OutputMessage(user.getUsername(), message.getText(), time);
			user.setMessagesSent(user.getMessagesSent()+1);
			userService.update(user);
		}
	
		messageService.saveMessage(returned_message);
		return returned_message;
	}
}
