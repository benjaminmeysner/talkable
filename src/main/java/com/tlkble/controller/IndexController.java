package com.tlkble.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlkble.domain.Event;
import com.tlkble.domain.User;
import com.tlkble.services.UserService;

@Controller
public class IndexController {

	@Autowired
	UserService userService;

	@RequestMapping("/")
	@Secured("ROLE_USER")
	public String root_redirect(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/user/home";
		} else
			return "login";
	}

	@RequestMapping("/user/home")
	@Secured("ROLE_USER")
	public String index(Model model, Principal principal) {

		/**
		 * Get the current logged in user from principal and add it to the model
		 */
		User user = (User) ((Authentication) principal).getPrincipal();
		System.out.println("I MAKE IT HERE");
		if (user != null) {
			model.addAttribute("currentDate", new Date());
			model.addAttribute("user", user);
			model.addAttribute("event", new Event());
			return "index";
		}
		System.out.println("I'M NULL");
		return null;
	}

	@RequestMapping("/register")
	public String register(Model model) {

		model.addAttribute("user", new User());
		return "register_";
	}

}
