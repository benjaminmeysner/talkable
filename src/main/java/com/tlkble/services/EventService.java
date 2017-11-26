package com.tlkble.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlkble.domain.Event;
import com.tlkble.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	EventRepository eventRepository;

	public Event createEvent(Event event) {
		return this.eventRepository.save(event);
	}
	
	public Event findEventById(String id) {
		return this.eventRepository.findEventById(id);
	}
	
	public Event updateEvent(Event event) {
		return this.eventRepository.save(event);
	}

}
