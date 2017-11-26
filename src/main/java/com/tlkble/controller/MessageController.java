package com.tlkble.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.tlkble.domain.Message;
import com.tlkble.domain.OutputMessage;
import com.tlkble.services.MessageService;

/** Controller handling the communication
 * / messaging using STOMP and SockJS over 
 *   lower WebSocket API
 * @author Ben */

@Controller
public class MessageController {
	
	@Autowired MessageService messageService;

	// CLIENT POSTS TO
	@MessageMapping("/events/{eventId}")
	// DESTINATION FOR OUTPUT MESSAGES
	@SendTo("/topic/{eventId}")
	public OutputMessage send(@DestinationVariable String eventId, Message message) throws Exception {
		// LOG TIME OF MESSAGE
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		
		// EXPLETIVE AND PROFANITY CHECK COULD GO HERE...
		
		//Check eventID is passing
		System.out.println("eventId = " + eventId);
		// SAVE OUTPUT MESSAGE TO REPO
		OutputMessage returned_message = new OutputMessage(message.getFrom(), message.getText(), time);
		messageService.saveMessage(returned_message);
		
		// OUTPUT MESSAGE TO SEND TO CLIENT
		return returned_message;
	}
}
