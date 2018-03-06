package com.tlkble.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

	/**
	 * Produces a count of all the events which have their isAlive boolean set to
	 * 'true'
	 * 
	 * @return
	 */
	public int activeEvents() {
		int cnt = 0;
		for (Event e : eventRepository.findAll()) {
			if (e.isAlive())
				++cnt;
		}
		return cnt;
	}

	@Autowired
	private SimpMessagingTemplate template; // Broadcast to users that event has ended, and force disconnect

	/**
	 * Events are kept alive for 180 minutes before being set to 'end' due to
	 * inactivity
	 */
	public void normalise() {
		for (Event e : eventRepository.findAll()) {
			if (e.isAlive()) {
				if (e.getInactiveMinutes() >= 2) {
					
					// Set isAlive to 'false'
					e.setAlive(false);
					
					System.out.println(e.getId() + " set to 'false' due to inactivity");

					// Send signal to all clients connected that the event has finished
					template.convertAndSend("/topic/eventStatus?" + e.getId(), true);

					// Update event
					eventRepository.save(e);
				} else {
					// Increase inactive minute count by 1
					e.setInactiveMinutes(e.getInactiveMinutes() + 1);					
					System.out.println(e.getId() + " inactive minutes now set at " + e.getInactiveMinutes());
					eventRepository.save(e);
				}
			}
		}

	}

}
