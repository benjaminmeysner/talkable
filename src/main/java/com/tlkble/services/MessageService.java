package com.tlkble.services;

import com.tlkble.domain.OutputMessage;

//--------------------------------------------------------------- //
//--- Class of message services to access the message repository  // 
//--------------------------------------------------------------- //

public interface MessageService {
	
	void saveMessage(OutputMessage om);
}
