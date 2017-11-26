package com.tlkble.controller;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlkble.domain.Event;
import com.tlkble.services.EventService;
import com.tlkble.util.EventDateTimeFormat;


@Controller
public class EventController {
	
	@Autowired
	private EventService eventService;

	EventDateTimeFormat edtf;
	
	//index page mapping
    @RequestMapping("/event/{eventId}")
    public String eventhome(@PathVariable(value = "eventId", required=true) String eventId, Model model) {
    	
    	Event event = eventService.findEventById(eventId);
    	
    	System.out.println("REACHES HERE");
    	
    	if(event == null) { //throw some errors 
    		return "";
    	} else {    	
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

	//event creation
    @RequestMapping(value = "/event/new", method = RequestMethod.POST)
    public String eventCreate(@RequestBody @ModelAttribute("event") Event event, Model model) {
    	//set event date
    	edtf = new EventDateTimeFormat();
    	event.setEventDate(edtf.dateFormat(new Date()));
    	//start time also...
    	event.setEventStartTime(edtf.timeFormat(LocalTime.now()));
    	event.setAlive(true);
    	//remember -> end time needed to be passed on /event/end
    	eventService.createEvent(event);
		model.addAttribute("event", new Event());
		return "redirect:/event/" + event.getId();
    }
    
	//ending event
    @RequestMapping(value = "/event/{eventId}/end")
    public String eventEnd(@PathVariable(value = "eventId", required=true) String eventId, Model model) {
    	Event event = eventService.findEventById(eventId);
    	event.setEventFinishTime(edtf.timeFormat(LocalTime.now()));
    	event.setAlive(false);
    	//remember -> end time needed to be passed on /event/end
    	
    	//update record
    	eventService.updateEvent(event);
		model.addAttribute("event", new Event());
		return "redirect:/";
    }
}
