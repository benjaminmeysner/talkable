package com.tlkble.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tlkble.domain.Message;
import com.tlkble.domain.User;
import com.tlkble.services.BotService;
import com.tlkble.util.ProfanityList;

/**
 * Implementation of BotService methods
 * 
 * @author Ben
 *
 */
@Service
public class BotServiceImpl implements BotService {

	ProfanityList profanityList = new ProfanityList();

	/**
	 * Comb the message for any offensive word, message is split on space
	 */
	@Override
	public boolean combMessageForProfanity(Message message) {

		boolean containsProfanity = false;
		String[] message_words_ = message.getText().split("\\s+");

		for (String word : message_words_) {
			if (profanityList.get_words().contains(word)) {
				containsProfanity = true;
			}

			for (int i = 0; i < profanityList.get_words().size(); i++) {
				if (word.contains(profanityList.get_words().get(i))) {
					containsProfanity = true;
				}
			}
		}
		return containsProfanity;
	}

	@Override
	public String cloakProfaneMessage(Message message) {
		String[] message_words_ = message.getText().split("\\s+");

		for (int i = 0; i < message_words_.length; i++) {

			for (int k = 0; k < profanityList.get_words().size(); k++) {

				// If expletive IS the current word
				if (message_words_[i].equals(profanityList.get_words().get(k))) {

					char prefix_, suffix_;
					int wordlength_;

					wordlength_ = message_words_[i].length() - 1;
					prefix_ = message_words_[i].charAt(0);
					suffix_ = message_words_[i].charAt(wordlength_);

					message_words_[i] = message_words_[i].replaceAll(".", "*");

					char[] cloaked_word_ = message_words_[i].toCharArray();

					cloaked_word_[0] = prefix_;
					cloaked_word_[wordlength_] = suffix_;

					message_words_[i] = String.valueOf(cloaked_word_);
				}
			}
		}
		;

		String output = "";
		for (int i = 0; i < message_words_.length; i++) {
			output = output + message_words_[i] + " ";
		}

		output = output.substring(0, output.length() - 1);

		return output;
	}

	@Override
	public boolean checkIfSpamMessage(User user) {

		boolean isSpam = false;

		System.out.println(user.getLastMessageSent() + " :: last message sent");

		// Current minutes
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		String[] current_split = time.split(":");
		int currentMinutes = Integer.parseInt(current_split[1]);

		System.out.println(time + " :: current time");

		// First message
		String[] message_split = user.getLastMessageSent().split(":");
		int messageMinutes = Integer.parseInt(message_split[1]);

		if (currentMinutes > messageMinutes) {
			if (!((currentMinutes - messageMinutes) > 1)) {
				isSpam = true;
			}
		}
		return isSpam;
	}

	@Override
	public int checkMessageLength(Message message) {
		return message.getText().length();
	}
}
