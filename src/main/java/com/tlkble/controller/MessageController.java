package com.tlkble.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.tlkble.domain.Message;
import com.tlkble.domain.OutputMessage;
import com.tlkble.domain.User;
import com.tlkble.services.BotService;
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

	@Autowired
	BotService botService;

	@Autowired
	SimpMessagingTemplate simpMessagingTemp;

	/**
	 * Return user message to client
	 * 
	 * @param eventId
	 * @param message
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/events/{eventId}")
	@SendTo("/topic/{eventId}")
	public OutputMessage send(@DestinationVariable String eventId, Message message, Principal principal)
			throws Exception {

		String time = new SimpleDateFormat("HH:mm").format(new Date());

		User user = (User) ((Authentication) principal).getPrincipal();
		OutputMessage returned_message = null;
		if (user != null) {
			returned_message = new OutputMessage(user.getUsername(), message.getText(), time);
			user.setMessagesSent(user.getMessagesSent() + 1);
			user.setLastMessageSent(time);
			userService.update(user);
		}

		returned_message.setMessage(botService.cloakProfaneMessage(message));
		
		if (botService.checkMessageLength(message) > 20) {
			messageService.saveMessage(returned_message);
			returned_message.setEventId(eventId);
			return returned_message;
		}
		return null;
	}

	/**
	 * Scans user message to check if it contains any unwanted contents and returns
	 * 
	 * @param principal
	 * @return
	 */
	@MessageMapping("/events/bot_reply")
	@SendToUser("/queue/reply")
	public OutputMessage send_to_user_(Message message, Principal principal) {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		User user = (User) ((Authentication) principal).getPrincipal();

		if (user != null) {
			// Messages must be over 20 characters in length
			if (botService.checkMessageLength(message) < 20) {
				OutputMessage custom_response = new OutputMessage("TALKBOT",
						"In line with our terms and conditions, we require that messages must be over 20 characters in length.",
						time);
				return custom_response;
			}
			// Checks if messages contains any profanity
			if (botService.combMessageForProfanity(message)) {
				OutputMessage custom_response = new OutputMessage("TALKBOT",
						"This is an automated response, it seems the message you sent contains some form of profanity, "
								+ "we would like to politely remind you that this service does not tolerate any offensive language. Please take time to read the terms and conditions.",
						time);
				return custom_response;
			}

			// Protects event from SPAM, more than 1 messages cannot be sent inside
			// 60seconds
			if (botService.checkIfSpamMessage(user)) {
				OutputMessage custom_response = new OutputMessage("TALKBOT",
						"This web service operates on an anti-spam basis, you must wait 60 seconds before sending a new message. If there are any questions please"
								+ " read the terms and conditions.",
						time);
				return custom_response;
			}
		}
		return null;
	}
}
