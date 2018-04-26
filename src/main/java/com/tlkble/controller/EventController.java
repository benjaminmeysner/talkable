package com.tlkble.controller;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlkble.domain.Event;
import com.tlkble.domain.User;
import com.tlkble.services.EventService;
import com.tlkble.services.UserService;
import com.tlkble.util.EventDateTimeFormat;

/**
 * Event Controller
 * 
 * @author Ben
 *
 */
@Controller
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	EventDateTimeFormat edtf;

	@RequestMapping("/event/{eventId}")
	public String eventhome(@PathVariable(value = "eventId", required = true) String eventId, Model model,
			Principal principal) throws JsonProcessingException {

		Event event = eventService.findEventById(eventId);

		// If user is not logged or the event is not 'alive' show appropriate view
		if (event == null || !event.isAlive()) {
			return "no_event";
		} else {
			User user = (User) ((Authentication) principal).getPrincipal();

			// Adding all previous message as model attribute
			ObjectMapper mapper = new ObjectMapper();
			// In string format
			String messageList = mapper.writeValueAsString(event.getMessages());
			model.addAttribute("messages", messageList);
			
			// Update user statistical counts if they are not the creator
			if (user != null) {
				if (!userService.isTheCreator(user, event)) {
					user.setEventsJoined(user.getEventsJoined() + 1);
					user.getEventsJoinedList().add(event);
				}
				userService.update(user);
			}

			// Stuff - Needs tidying
			model.addAttribute("currentloggedInUser", user);
			model.addAttribute("loggedInUsername", user.getUsername());
			model.addAttribute("eventTitle", event.getEventTitle());
			model.addAttribute("eventDescription", event.getEventDescription());
			model.addAttribute("eventId", event.getId());
			model.addAttribute("eventDate", event.getEventDate());
			model.addAttribute("eventStartTime", event.getEventStartTime());
			model.addAttribute("isAlive", event.isAlive());
			model.addAttribute("eventCreator", event.getCreator());
			model.addAttribute("event", new Event());
		}
		return "event";
	}

	/**
	 * Every 60 seconds (using a cron expression) check if events are inactive ( An
	 * event has not received any message for 180 minutes )
	 */
	@Scheduled(cron = "0 * * ? * *")
	public void checkIfEventsAreInactive() {
		eventService.normalise();
	}

	/**
	 * eventCreate Create a new event
	 * 
	 * @param event
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/event/new", method = RequestMethod.POST)
	public String eventCreate(@RequestBody @ModelAttribute("event") Event event, Model model, Principal principal) {

		// Set time statistics
		edtf = new EventDateTimeFormat();
		event.setEventDate(edtf.dateFormat(new Date()));
		event.setEventStartTime(edtf.timeFormat(LocalTime.now()));

		// The event is now live!
		event.setAlive(true);

		// If the user is logged in, Update User statistics
		User user = (User) ((Authentication) principal).getPrincipal();
		if (user != null) {
			event.setCreator(user.getUsername());
			user.setEventsCreated(user.getEventsCreated() + 1);
			user.getEventsCreatedList().add(event);
			event.getUsers().add(user.getUsername());
			userService.update(user);
		} else
			return null;

		// Set Quick title
		if (event.getEventTitle().isEmpty()) {
			event.setEventTitle("Quick Event");
		}
		// Set Quick description
		if (event.getEventDescription().isEmpty()) {
			event.setEventDescription("This is a quick event. No description was entered.");
		}

		// Create the event
		eventService.createEvent(event);
		model.addAttribute("event", new Event());
		return "redirect:/event/" + event.getId();
	}

	@Autowired
	private SimpMessagingTemplate template; // Broadcast to users that event has ended, and force disconnect

	/**
	 * endEvent End current event
	 * 
	 * @param eventId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/event/{eventId}/end")
	public String eventEnd(@PathVariable(value = "eventId", required = true) String eventId, Model model) {
		Event event = eventService.findEventById(eventId);
		event.setEventFinishTime(edtf.timeFormat(LocalTime.now()));
		event.setAlive(false);
		eventService.updateEvent(event);
		model.addAttribute("event", new Event());

		// Send signal to all clients connected that the event has finished
		template.convertAndSend("/topic/eventStatus?" + eventId, true);

		// Event creator redirects
		return "redirect:/user/home";
	}
}
