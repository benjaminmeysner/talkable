package com.tlkble.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * List of stop words
 * 
 * @author Ben
 *
 */
public class StopWordList {

	public static List<String> stopWords = new ArrayList<String>();

	public StopWordList() {
		load_words_();
	}

	public static void load_words_() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/lists/stopword.txt"));

			String line = "";
			while ((line = reader.readLine()) != null) {
				stopWords.add(line);
			}

			reader.close();

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}

	public static List<String> get_words() {
		return stopWords;
	}
}
