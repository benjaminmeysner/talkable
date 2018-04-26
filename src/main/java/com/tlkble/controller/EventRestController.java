package com.tlkble.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlkble.domain.Event;
import com.tlkble.domain.WordCount;
import com.tlkble.services.EventService;
import com.tlkble.util.AnalysisUtils;

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
	
	/**
	 * returns a word count for this particular event
	 * 
	 * @return int
	 */
	@RequestMapping("/event/getWordCount")
	@ResponseBody
	public List<WordCount> wordCount(@RequestParam("eventid") String eventid) {
		Event event = eventService.findEventById(eventid);
		if (event != null) {
			return AnalysisUtils.retrieveWords(event);
		}
		return null;
	}
}
