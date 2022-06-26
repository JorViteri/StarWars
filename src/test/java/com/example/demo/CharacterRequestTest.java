package com.example.demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void characterIsReturned() throws Exception {

		String getRequest = "/swapi-proxy/person-info?name=luke skywalker";
		String expectedCharacter = "{ \"films\": [ { \"release_date\": \"1977-05-25\""
				+ ", \"name\": \"A New Hope\" }, { \"release_date\": \"1980-05-17\""
				+ ", \"name\": \"The Empire Strikes Back\" }, { \"release_date\": "
				+ "\"1983-05-25\", \"name\": \"Return of the Jedi\" }, { \"release_date\""
				+ ": \"2005-05-19\", \"name\": \"Revenge of the Sith\" } ], \"gender\""
				+ ": \"male\", \"name\": \"Luke Skywalker\", \"fastest_vehicle\""
				+ ": \"X-wing\", \"planet_name\": \"Tatooine\", \"birth_year\": \"19BBY\" }";

		JSONObject jsonObject = new JSONObject(expectedCharacter);

		this.mockMvc.perform(get(getRequest)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(jsonObject.toString(4))));
	}

	@Test
	public void characterIsNotReturned() throws Exception {

		String getRequest = "/swapi-proxy/person-info?name=L";
		String expectedErrorMessage = "The Character 'L' was not found";

		this.mockMvc.perform(get(getRequest)).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString(expectedErrorMessage)));
	}

}
