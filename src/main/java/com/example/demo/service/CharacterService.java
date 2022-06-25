package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.example.demo.entity.Character;

public interface CharacterService {

	public Character getCharacter(String characterName)
			throws MalformedURLException, IOException;

}
