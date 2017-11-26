package com.tlkble.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlkble.domain.LoginRequest;
import com.tlkble.domain.User;
import com.tlkble.services.UserService;

/**
 * REST API
 * @author Ben
 * @issues Not REST Methods cause issues loading static resources when placed within this class.
 */

@RestController
public class UserRestController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/users/{username}")
	public User userByUsername(@PathVariable("username") String username) {
		User user = this.userService.findByUsername(username);
		return user;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User createUser(@Valid @ModelAttribute("user") User user) {
		return this.userService.register(user);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/register/emailCheck")
	@ResponseBody
	public ResponseEntity emailCheck(@RequestParam("emailAddress") String emailAddress) {			
		if(userService.existsByEmailAddress(emailAddress))  {
	        return new ResponseEntity(HttpStatus.BAD_REQUEST);
	        } else return new ResponseEntity(HttpStatus.OK); 
		// method return bolean if email exist or not in database.
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/register/usernameCheck")
	@ResponseBody
	public ResponseEntity usernameCheck(@RequestParam("username") String username) {			
		if(userService.existsByUsername(username))  {
	        return new ResponseEntity(HttpStatus.BAD_REQUEST);
	        } else return new ResponseEntity(HttpStatus.OK); 
		// method return bolean if username exist or not in database.
	}
	
	//TO DO:- LOG IN TO SPRING SECURITY VIA AJAX
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
    public boolean authorise_and_login(@Valid @RequestBody LoginRequest loginRequest) {
		
		User user = userService.findByUsername(loginRequest.getUsername());
		System.out.println("testtttttt - reaches here");
        if (user == null) return false;
        if(!userService.checkPassword(loginRequest.getPassword(), user.getPassword())) return false;
        else userService.authorize(user);
		
		return true; 
    }


}
