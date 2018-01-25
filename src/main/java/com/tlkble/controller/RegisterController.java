package com.tlkble.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlkble.domain.User;
import com.tlkble.services.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute("user") User user) {
		Object response = this.userService.register(user);
		if (response != null) {
			userService.register(user);

			/* Login user on successful registration */

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
					"N/A", authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			return "redirect:/user/home";
		}
		return null;
	}
}
