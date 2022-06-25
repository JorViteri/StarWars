package com.example.demo.entity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Character {

	private String name;
	private String birthYear;
	private String gender;
	private String planetName;
	private String fastestVehicle;
	private JSONArray films;

	public Character(String name, String birthYear, String gender, String fastestVehicle, String planetName,
			JSONArray films) {
		this.name = name;
		this.birthYear = birthYear;
		this.gender = gender;
		this.planetName = planetName;
		this.fastestVehicle = fastestVehicle;
		this.films = films;
	}

	@Override
	public String toString() {
		return this.toJSON().toString(4);
	}

	private JSONObject toJSON() {
		JSONObject characterJSONObject = new JSONObject();

		characterJSONObject.put("name", this.name);
		characterJSONObject.put("birth_year", this.birthYear);
		characterJSONObject.put("gender", this.gender);
		characterJSONObject.put("planet_name", this.planetName);
		characterJSONObject.put("fastest_vehicle", this.fastestVehicle);
		characterJSONObject.put("films", this.films);

		return characterJSONObject;
	}
}
