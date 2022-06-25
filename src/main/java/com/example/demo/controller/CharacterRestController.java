package com.example.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Character;
import com.example.demo.service.CharacterService;

@RestController
public class CharacterRestController {

	@Autowired
	CharacterService characterService;

	@GetMapping("/swapi-proxy/person-info")
	@ResponseBody
	public String findCharacterInfo(@RequestParam(value = "name", required = true) String characterName)
			throws MalformedURLException, IOException {
		Character character = characterService.getCharacter(characterName);
		return String.format("%s", character.toString());
	}
}
