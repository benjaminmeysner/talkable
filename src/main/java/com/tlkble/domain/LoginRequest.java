package com.tlkble.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest implements Serializable{

	private static final long serialVersionUID = -949145796707798094L;
	
	@NotEmpty(message = "Message for REST API")
	private String username;
	@NotEmpty(message = "Message for REST API")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
