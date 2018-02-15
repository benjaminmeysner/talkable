package com.tlkble.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping("/user/log")
	public String log_screen(Model model, Principal principal) {

		User user = (User) ((Authentication) principal).getPrincipal();
		if (user != null) {
			List<Event> myJoinedEvents = user.getEventsJoinedList();
			List<Event> myCreatedEvents = user.getEventsCreatedList();
			if (myJoinedEvents.size() == 0) {
				String NOJITEMS = "Sorry, you have not joined any events yet";
				model.addAttribute(NOJITEMS, "Sorry, you have not joined any events");
			}
			if (myCreatedEvents.size() == 0) {
				String NOCITEMS = "Sorry, you have not created any events yet";
				model.addAttribute(NOCITEMS, "Sorry you have not created any events");
			}
			model.addAttribute("myJoinedEvents", myJoinedEvents);
			model.addAttribute("myCreatedEvents", myCreatedEvents);
		}
		return "log";
	}

}
