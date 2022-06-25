package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CharacterDao;
import com.example.demo.entity.Character;

@Service
public class CharacterServiceImpl implements CharacterService {

	@Autowired
	private CharacterDao characterDao;
	private Logger logger = Logger.getLogger("MyLog");

	@Cacheable({ "Character", "#characterName" })
	@Override
	public Character getCharacter(String characterName) throws MalformedURLException, IOException {

		logger.info("Searching for character");
		int numPage = 1;

		String characterPageTemplate = "https://swapi.trileuco.com/api/people/?page=%d&format=json";
		String characterPage = String.format(characterPageTemplate, numPage);
		JSONObject charactersPageJson = null;
		JSONObject tmpCharacterJSON = null;
		String tmpName = "";
		String nextPage = "";

		while (nextPage != null) {

			charactersPageJson = characterDao.getDataSWAPI(characterPage);
			JSONArray characters = charactersPageJson.getJSONArray("results");

			if (!charactersPageJson.isNull("next")) {
				nextPage = charactersPageJson.getString("next");
				;
			} else {
				nextPage = null;
			}

			for (Object tmpCharacter : characters) {

				if (tmpCharacter instanceof JSONObject) {

					tmpCharacterJSON = (JSONObject) tmpCharacter;

					tmpName = tmpCharacterJSON.getString("name");

					if (tmpName.equalsIgnoreCase(characterName)) {

						String birthYear = tmpCharacterJSON.getString("birth_year");

						String gender = tmpCharacterJSON.getString("gender");

						String vehicle = this.searchFastestVehicle(tmpCharacterJSON.getJSONArray("vehicles"),
								tmpCharacterJSON.getJSONArray("starships"));

						String planetName = this.getPlanetName(tmpCharacterJSON.getString("homeworld"));

						JSONArray movies = this.getMovies(tmpCharacterJSON.getJSONArray("films"));

						Character resultCharacter = new Character(tmpName, birthYear, gender, vehicle, planetName,
								movies);

						return resultCharacter;
					}

				}
			}

			numPage += 1;
			characterPage = String.format(characterPageTemplate, numPage);

		}
		return null;
	}

	private String searchFastestVehicle(JSONArray vehiclesUrlList, JSONArray starshipsUrlList)
			throws MalformedURLException, IOException {

		int maxVelocity = 0, currentVelocity;
		String fastestVehicle = null;
		String vehiclePageUrl = null;
		JSONObject vehicle = null;

		for (Object vehicleUrl : vehiclesUrlList) {
			vehiclePageUrl = String.format("%s?format=json", (String) vehicleUrl);
			vehicle = characterDao.getDataSWAPI(vehiclePageUrl);
			currentVelocity = Integer.parseInt(vehicle.getString("max_atmosphering_speed"));

			if (currentVelocity > maxVelocity) {
				fastestVehicle = vehicle.getString("name");
				maxVelocity = currentVelocity;
			}
		}

		for (Object starshipUrl : starshipsUrlList) {
			vehiclePageUrl = String.format("%s?format=json", (String) starshipUrl);
			vehicle = characterDao.getDataSWAPI(vehiclePageUrl);
			currentVelocity = Integer.parseInt(vehicle.getString("max_atmosphering_speed"));

			if (currentVelocity > maxVelocity) {
				fastestVehicle = vehicle.getString("name");
				maxVelocity = currentVelocity;
			}
		}

		return fastestVehicle;
	}

	private String getPlanetName(String planetUrl) throws MalformedURLException, IOException {
		String planetPageUrl = String.format("%s?format=json", planetUrl);
		JSONObject planetJson = characterDao.getDataSWAPI(planetPageUrl);
		return planetJson.getString("name");

	}

	private JSONArray getMovies(JSONArray moviesUrlList) throws MalformedURLException, IOException {
		String moviePageUrl;
		JSONObject movie, tmpFilm;
		JSONArray filmsList = new JSONArray();

		for (Object movieUrl : moviesUrlList) {
			moviePageUrl = String.format("%s?format=json", (String) movieUrl);
			movie = characterDao.getDataSWAPI(moviePageUrl);
			tmpFilm = new JSONObject();

			tmpFilm.put("name", movie.getString("title"));
			tmpFilm.put("release_date", movie.getString("release_date"));

			filmsList.put(tmpFilm);

		}

		return filmsList;
	}

}
