package com.tlkble.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain Class for an event
 * @issues Lombok Getters & Setters showing empty JSON data at endpoint,
 * using traditional method for now.
 * @author Ben
 * MongoDB & Lombok Annotations
 */

@Document(collection="events")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize
public class Event {

	private String id;
	private String eventTitle;
	private String eventDescription;
	private String eventDate;
	private String eventStartTime;
	private String eventFinishTime;
	private boolean isAlive;
	
	public Event (String id, String eventTitle, String eventDescription) {
		this.id = id;
		this.eventTitle = eventTitle;
		this.eventDescription = eventDescription;
	}
	
	
}
