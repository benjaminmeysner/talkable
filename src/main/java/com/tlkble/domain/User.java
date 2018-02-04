package com.tlkble.domain;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain Class for a registered User.
 * User Types: "USER", "PRESENTER", "ADMIN"
 * @issues Lombok Getters & Setters showing empty JSON data at endpoint,
 * using traditional method for now.
 * @author Ben
 * MongoDB & Lombok Annotations
 */

//Name of collection, user objects are mapped to! ->
@Document(collection="users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize
public class User {
	/* Personal Data */
	@Getter @Setter private String firstName;
	@Getter @Setter private String lastName;
	@Getter @Setter @Indexed(name="emailAddress", unique=true) private String emailAddress;
	@Id @Getter @Setter @Indexed(name="username") private String username;
	@Getter @Setter @JsonIgnore private String password;
	@Getter @Setter @JsonIgnore private String passwordCloak;
	@Getter @Setter @JsonIgnore private String role;
	/* Event Data */
	@Getter @Setter private int eventsCreated;
	@Getter @Setter private int eventsJoined;
	@Getter @Setter private int messagesSent;
	@Getter @Setter private String lastMessageSent;
	
	
	@PersistenceConstructor
	public User(String firstName, String lastName, String emailAddress, String username, String password) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
	}

	public boolean isEnabled() {
		return true;
	}

	public User(String username2, String password2, boolean b, boolean c, boolean d,
			Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
	}
}



