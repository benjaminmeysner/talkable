package com.tlkble.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * List of offensive words
 * 
 * @author Ben
 *
 */
public class ProfanityList {

	public static List<String> offensiveWords = new ArrayList<String>();

	public ProfanityList() {
		load_words_();
	}

	public void load_words_() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(
					"https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv")
							.openConnection().getInputStream()));
			
			String line = "";
			String[] content;
			while((line = reader.readLine()) != null) { 
				content = line.split(",");
				offensiveWords.add(content[0]);
			}
			
			
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}

	public List<String> get_words() {
		return offensiveWords;
	}
}
