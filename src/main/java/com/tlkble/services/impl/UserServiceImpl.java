package com.tlkble.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.tlkble.domain.User;
import com.tlkble.repository.UserRepository;
import com.tlkble.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	// User register service
	@Override
	public User register(User user) {
		// Encrypt password using BCrypt
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		// for ignoring case sensitivity
		user.setEmailAddress(user.getEmailAddress().toLowerCase());
		user.setUsername(user.getUsername().toLowerCase());
		user.setRole("ROLE_USER");
		return userRepo.save(user);
	}

	// Find user by email address
	public Boolean existsByEmailAddress(String emailAddress) {
		User user = userRepo.findByEmailAddressIgnoreCase(emailAddress);
		if (user != null)
			return true;
		else
			return false;
	}

	// Find user by username
	public Boolean existsByUsername(String username) {
		User user = userRepo.findByUsernameIgnoreCase(username);
		if (user != null)
			return true;
		else
			return false;
	}

	// Spring security AJAX Stuff
	@Override
	public Boolean checkPassword(String rqPassword, String dbPassword) {
		try {
			if (BCrypt.checkpw(rqPassword, dbPassword))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Return the list of users available
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	// Find user by username
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public void authorize(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, "N/A",
				authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		return;
	}

	@Override
	public User getCurrentLoginUser() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
				return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
		}
		return null;
	}
}
