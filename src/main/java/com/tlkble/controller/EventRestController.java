package com.tlkble.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import com.tlkble.services.EventService;


@RestController
public class EventRestController {
	
	@Autowired
	EventService eventService;

}
