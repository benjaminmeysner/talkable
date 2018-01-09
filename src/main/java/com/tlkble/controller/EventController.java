package com.tlkble.controller;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlkble.domain.Event;
import com.tlkble.domain.User;
import com.tlkble.services.EventService;
import com.tlkble.services.UserService;
import com.tlkble.util.EventDateTimeFormat;

/**
 * Event Controller ================ Controls actions related to event handling,
 * creating deleting etc.
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
			Principal principal) {

		Event event = eventService.findEventById(eventId);

		if (event == null) { /* throw errors */
			return "";
		} else {
			/* set the events joined count */
			User user = (User) ((Authentication) principal).getPrincipal();

			if (user != null) {
				user.setEventsJoined(user.getEventsJoined() + 1);
				userService.update(user);
			}

			model.addAttribute("eventTitle", event.getEventTitle());
			model.addAttribute("eventDescription", event.getEventDescription());
			model.addAttribute("eventId", event.getId());
			model.addAttribute("eventDate", event.getEventDate());
			model.addAttribute("eventStartTime", event.getEventStartTime());
			model.addAttribute("isAlive", event.isAlive());
			model.addAttribute("event", new Event());
		}
		return "event";
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

		edtf = new EventDateTimeFormat();
		event.setEventDate(edtf.dateFormat(new Date()));
		event.setEventStartTime(edtf.timeFormat(LocalTime.now()));
		event.setAlive(true);

		/* set event creator from user principal */
		User user = (User) ((Authentication) principal).getPrincipal();
		if (user != null) {
			event.setCreator(user.getUsername());
			user.setEventsCreated(user.getEventsCreated() + 1);
			userService.update(user);
			/* return error, authentication issue */
		} else
			return null;

		eventService.createEvent(event);
		model.addAttribute("event", new Event());
		return "redirect:/event/" + event.getId();
	}

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
		return "redirect:/user/home";
	}
}
