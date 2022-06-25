package com.example.demo.dao;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

public interface CharacterDao {

	public JSONObject getDataSWAPI(String url) throws MalformedURLException, IOException;

}
