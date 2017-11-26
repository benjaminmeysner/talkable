package com.tlkble.controller;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlkble.domain.User;

@Controller
public class HomeController {
	
	//user home mapping - LOGGED IN
    @RequestMapping("/home")
    public String settings(Model model) {   	
    	//something?   
    	//Required for index registration handling
    	model.addAttribute("currentDate", new Date());
    	model.addAttribute("currentTime", LocalTime.now());
		model.addAttribute("user", new User());
        return "index";
    }
}
