package com.example.demo.error;

public class CharacterNotFoundException extends RuntimeException {

	public CharacterNotFoundException(String characterName) {
		super(String.format("The Character '%s' was not found", characterName));
	}
}
