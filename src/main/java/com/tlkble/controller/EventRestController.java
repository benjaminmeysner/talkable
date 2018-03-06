package com.tlkble.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlkble.domain.Event;
import com.tlkble.services.EventService;

@RestController
public class EventRestController {

	@Autowired
	EventService eventService;

	/**
	 * return an event by its id
	 * @param eventid
	 * @return
	 */
	@RequestMapping("/event/checkevent")
	@ResponseBody
	public Event eventCheck(@RequestParam("eventid") String eventid) {
		// Get rid of first and last character
		eventid = eventid.substring(1);
		eventid = eventid.substring(0, eventid.length() - 1);
		Event event = eventService.findEventById(eventid);
		if (event != null) {
			return event;
		}
		return null;
	}

	/**
	 * returns the total active events
	 * 
	 * @return int
	 */
	@RequestMapping("/event/activecount")
	@ResponseBody
	public int eventsActive() {
		return eventService.activeEvents();
	}
}
