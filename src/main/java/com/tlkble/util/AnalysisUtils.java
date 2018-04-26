package com.tlkble.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tlkble.domain.Event;
import com.tlkble.domain.OutputMessage;
import com.tlkble.domain.User;
import com.tlkble.domain.WordCount;
import com.tlkble.repository.EventRepository;
import com.tlkble.services.UserService;

public class AnalysisUtils {

	@Autowired
	EventRepository eventRepo;

	@Autowired
	UserService userService;

	/**
	 * Returns a complete block of words for all events created by 'User'
	 * 
	 * @param user
	 * @return
	 */
	public String getWordsFromAllEvents(User user) {

		StringBuilder wordBlock = new StringBuilder();

		List<Event> userCreatedEvents = new ArrayList<Event>();
		for (Event e : eventRepo.findAll()) {
			if (userService.isTheCreator(user, e))
				userCreatedEvents.add(e);
		}

		for (Event e : userCreatedEvents)
			for (OutputMessage om : e.getMessages())
				for (String word : om.getWords())
					wordBlock.append(word + " ");

		return new String(wordBlock);
	}

	/**
	 * Returns a complete block of words for a particular event
	 * 
	 * @param event
	 * @return
	 */
	public static List<String> getWordsFromEvent(Event event) {

		List<String> words = new ArrayList<String>();

		for (OutputMessage om : event.getMessages())
			for (String word : om.getWords())
				words.add(word);

		return words;
	}

	/**
	 * Removes all common(stop words) from an array of string, and returns a word
	 * block
	 * 
	 * @param words
	 * @return
	 */
	public String removeStopWords(List<String> words) {
		for (int i = 0; i < words.size(); i++)
			if (StopWordList.get_words().contains(words.get(i)))
				words.remove(i);

		StringBuilder sb = new StringBuilder();
		for (String word : words)
			sb.append(word + " ");

		return new String(sb);
	}

	/**
	 * Retrieves a word occurence count for a particular event
	 * @param event
	 * @return
	 */
	public static List<WordCount> retrieveWords(Event event) {
		List<WordCount> lwc = new ArrayList<WordCount>();
		List<String> words = getWordsFromEvent(event);
		
		//For every word in a list of words from an event
		for(String word : words)
			// If the word is not in a list of stopwords
			if(!StopWordList.get_words().contains(word)) {
				// And the list is empty
				if(lwc.isEmpty()) {
				// Add new K,V pair
				lwc.add(new WordCount(word, 1));
				} else {
					// Otherwise update existing pair
					Boolean contains = false;
					for(WordCount wc : lwc) {
						if(wc.getText().equals(word)) {
							wc.setSize(wc.getSize()+1);
							contains = true;
						}
					}	
					if(!contains)
							lwc.add(new WordCount(word, 1));
					}
			}
		
		return lwc;
	}

}
