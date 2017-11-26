package com.tlkble.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlkble.domain.Event;
import com.tlkble.domain.User;

@Controller
public class IndexController {
	
	//index page mapping
    @RequestMapping("/")
    public String index(Model model) {   	
    	//something?      
    	
    	//Required for index model handling & dynamic attributes
    	model.addAttribute("currentDate", new Date());
		model.addAttribute("user", new User());
		model.addAttribute("event", new Event());
        return "index";
    }
    
	//register page mapping
    @RequestMapping("/register")
    public String register(Model model) {   	
    	//something?   
    	
    	//Required for index registration handling
		model.addAttribute("user", new User());
        return "register";
    }

}
