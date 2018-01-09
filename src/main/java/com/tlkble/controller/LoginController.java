package com.tlkble.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tlkble.domain.User;
import com.tlkble.services.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public String login_redirect(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/user/home";
		} else
			return "login";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String authorise_and_login(@RequestParam("username") String userOrEmail,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {

		String loginError;
		User user;

		/* See if user or email addresses points to a user */
		if (userService.findByUsername(userOrEmail) != null) {
			user = userService.findByUsername(userOrEmail);
		} else if (userService.findByEmailAddress(userOrEmail) != null) {
			user = userService.findByEmailAddress(userOrEmail);
		} else
			user = null;

		/* Errors are added to the redirect using redirectAttribute */

		if (userOrEmail == "" || password == "") {
			loginError = "There are missing fields, you have failed to provide a username,"
					+ "email address or a password, please note, you can log into your account with"
					+ "either your email address or your username";
			ra.addFlashAttribute("loginError", loginError);
			return "redirect:/login";

		} else if (user == null) {
			loginError = "Username or email address could be not found, please note, you can log into your account with either your email address or your username";
			ra.addFlashAttribute("loginError", loginError);
			return "redirect:/login";

		} else if (!userService.checkPassword(password, user.getPassword())) {
			loginError = "Incorrect username/email and password combination,"
					+ "please note you can log into your account with either your email address or your username";
			ra.addFlashAttribute("loginError", loginError);
			return "redirect:/login";

		} else {
			
		userService.authorize(user);
		request.getSession().setAttribute("loggedInUser", user);
		return "redirect:/user/home";
		}
	}

	@RequestMapping("/user/logout")
	public String verifyLogout(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);

		return "redirect:/login?logout";

	}
}
