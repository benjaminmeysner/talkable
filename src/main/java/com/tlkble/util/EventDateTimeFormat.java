package com.tlkble.util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EventDateTimeFormat {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	//Format the time
	public String timeFormat(LocalTime time) {
		String newtime = time.format(timeFormat);
		return newtime;		
	}

	//Format the date
	public String dateFormat(Date date) {
		String today = dateFormat.format(date);
		return today;
	}		 
}
