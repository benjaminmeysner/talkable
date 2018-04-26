package com.tlkble.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "messages")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize
public class OutputMessage {
	
	@Getter @Setter public String eventId;
	@Getter @Setter public String author;
	@Getter @Setter public String message;
	@Getter @Setter public String[] words;
	@Getter @Setter public String time;
	@Getter @Setter public String id;
	
	public OutputMessage (String author, String message, String time) {
		this.author = author;
		this.message = message;
		this.time = time;
		
		words = message.split("\\s+");
	}
}
