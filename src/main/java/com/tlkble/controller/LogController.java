package com.tlkble.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlkble.domain.Event;
import com.tlkble.domain.User;
import com.tlkble.services.EventService;

/**
 * Log Controller
 * 
 * @author Ben
 *
 */
@Controller
public class LogController {

	@Autowired
	EventService eventService;

	/**
	 * Log home page
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping("/user/log")
	public String log_screen(Model model, Principal principal) {

		User user = (User) ((Authentication) principal).getPrincipal();
		if (user != null) {

			List<Event> myJoinedEvents = user.getEventsJoinedList();
			List<Event> myCreatedEvents = user.getEventsCreatedList();

			// Reverse list to have most recent events at beginning, using a shallow copy
			// to return a pointer to the list elements without actually modifying the list
			// directly
			List<?> shallowCopy_JE = myJoinedEvents.subList(0, myJoinedEvents.size());
			Collections.reverse(shallowCopy_JE);

			List<?> shallowCopy_CE = myCreatedEvents.subList(0, myCreatedEvents.size());
			Collections.reverse(shallowCopy_CE);

			model.addAttribute("myJoinedEvents", shallowCopy_JE);
			model.addAttribute("myCreatedEvents", shallowCopy_CE);
		}
		return "log";
	}

	/**
	 * Individual event analysis
	 * 
	 * @param eventId
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping("/event/log/{eventId}")
	public String eventhome(@PathVariable(value = "eventId", required = true) String eventId, Model model,
			Principal principal) {

		User user = (User) ((Authentication) principal).getPrincipal();

		Event e = eventService.findEventById(eventId);

		if (user != null && e != null) {
			model.addAttribute("eventId", e.getId());
		}

		return "event_analysis";

	}

}
