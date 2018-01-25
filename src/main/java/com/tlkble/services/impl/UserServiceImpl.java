package com.tlkble.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.tlkble.domain.User;
import com.tlkble.repository.UserRepository;
import com.tlkble.services.UserService;
import com.tlkble.util.PasswordCloak;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SimpUserRegistry simpUserRegistry;

	PasswordCloak cloaker;

	@Override
	public User register(User user) {

		/**
		 * Server-side user validation checks 1. Validating any blank fields. Does not
		 * cater for white spaces Fields must be fully white-space
		 * 
		 * 2. Check for appearances of white space. Cannot appear anywhere inside string
		 * 
		 * 3. Pattern Match Email REGEX
		 * 
		 * 4. Pattern Match Username Format
		 * 
		 * 5. Pattern Match Password Format
		 * 
		 * Otherwise save the new user
		 */

		/* 1 */
		if (StringUtils.isBlank(user.getFirstName()) || StringUtils.isBlank(user.getLastName())
				|| StringUtils.isBlank(user.getEmailAddress()) || StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())) {
			return null;
		}

		/* 2 */
		if (StringUtils.containsWhitespace(user.getFirstName()) || StringUtils.containsWhitespace(user.getLastName())
				|| StringUtils.containsWhitespace(user.getEmailAddress())
				|| StringUtils.containsWhitespace(user.getUsername())
				|| StringUtils.containsWhitespace(user.getPassword())) {
			return null;
		}

		/* 3 */
		if (!user.getEmailAddress().matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			return null;

		/* 4 */
		if (!user.getUsername().matches("(?=^.{5,10}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$"))
			return null;

		/* 5 */
		if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&amp;])[A-Za-z\\d$@$!%*#?&amp;]{8,}$"))
			return null;

		/**
		 * Save new user to repository Encrypt passwords and set both emailaddress and
		 * username to lowercase
		 */
		user.setPasswordCloak(PasswordCloak.cloakPassword(user.getPassword()));
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setEmailAddress(user.getEmailAddress().toLowerCase());
		user.setUsername(user.getUsername().toLowerCase());
		user.setRole("ROLE_USER");
		return userRepo.save(user);
	}

	/**
	 * Check if user exists
	 * 
	 * @return boolean
	 */
	public Boolean existsByEmailAddress(String emailAddress) {
		User user = userRepo.findByEmailAddressIgnoreCase(emailAddress);
		if (user != null)
			return true;
		else
			return false;
	}

	/**
	 * Check if a user exists by username
	 * 
	 * @return boolean
	 */
	public Boolean existsByUsername(String username) {
		User user = userRepo.findByUsernameIgnoreCase(username);
		if (user != null)
			return true;
		else
			return false;
	}

	/**
	 * Return list of all users
	 * 
	 * @return user
	 */
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User findByUsername(String username) {
		User user = userRepo.findByUsernameIgnoreCase(username);
		if (user != null) {
			return user;
		}
		;
		return null;
	}

	public User findByEmailAddress(String emailAddress) {
		return userRepo.findByEmailAddressIgnoreCase(emailAddress);
	}

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
	public void update(User user) {
		this.userRepo.save(user);
	}

	@Override
	public User getCurrentUser() {

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) a.getPrincipal();
		if (currentUser != null) {
			return currentUser;
		}

		return null;
	}
}
