package com.tlkble.domain;

/**
 * Class representing the K,V of a word
 * @author ben
 *
 */
public class WordCount {

	String text;
	int size;
	
	public WordCount (String text, int size) {
		this.text = text;
		this.size = size;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
