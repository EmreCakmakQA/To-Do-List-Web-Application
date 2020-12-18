package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.dto.PersonDto;
import com.example.demo.persistence.domain.Person;
import com.example.demo.persistence.domain.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

@Sql(scripts = { "classpath:todo-schema.sql",
		"classpath:todo-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class PersonControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private PersonDto mapToDTO(Person person) {
		return this.mapper.map(person, PersonDto.class);
	}

	private final Person TEST_PERSON_1 = new Person(1L, "Emre");
	private final Person TEST_PERSON_2 = new Person(2L, "Nouha");
	private final Person TEST_PERSON_3 = new Person(3L, "Troy");
	private final Person TEST_PERSON_4 = new Person(4L, "Lawrence");

	private final List<Todo> todos = Collections.emptyList();
	private final List<Person> LISTOFPEOPLE = List.of(TEST_PERSON_1, TEST_PERSON_2, TEST_PERSON_3, TEST_PERSON_4);
	private final String URI = "/person";

	// Create test
	@Test
	void createTest() throws Exception {
		PersonDto testDTO = mapToDTO(new Person("Emre"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isCreated();

		PersonDto testSavedDTO = mapToDTO(new Person("Emre"));
		testSavedDTO.setId(5L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);


	}	
	
	
	// Read One Test
	@Test
	void ReadOne() throws Exception {
		Person person = new Person("Emre");
		person.setTodos(todos);
		PersonDto testDTO = mapToDTO(person);
		
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);
		
		RequestBuilder request = get(URI + "/read/1").contentType(MediaType.APPLICATION_JSON);
		
		ResultMatcher checkStatus = status().isOk();
		person.setId(1L);
		PersonDto expected = mapToDTO(person);
		String expectedAsJSON = this.jsonifier.writeValueAsString(expected);
		ResultMatcher checkBody = content().json(expectedAsJSON);
		
		this.mvc.perform(request).andExpect(checkBody).andExpect(checkBody);
		

	}
	

	// Read All Test
	@Test
	void ReadAll() throws Exception {
		Person person = new Person("Emre");
		person.setTodos(todos);
		PersonDto testDTO = mapToDTO(person);
		
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);
		
		RequestBuilder request = get(URI + "/read").contentType(MediaType.APPLICATION_JSON);
		
		ResultMatcher checkStatus = status().isOk();
		person.setId(1L);
		PersonDto expected = mapToDTO(person);
		String expectedAsJSON = this.jsonifier.writeValueAsString(expected);
		ResultMatcher checkBody = content().json(expectedAsJSON);
		
		this.mvc.perform(request).andExpect(checkBody).andExpect(checkBody);
		

	}
	
	// Update Test
		@Test
		void updateTest() throws Exception {
			List<Person> people = new ArrayList<>();
			PersonDto personDto = mapToDTO(new Person("Emre"));
			String testDTOAsJSON = this.jsonifier.writeValueAsString(personDto);
		
			RequestBuilder request = put(URI + "/update/1").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
			
			ResultMatcher checkStatus = status().isAccepted();
			
			PersonDto testSavedDTO = mapToDTO(new Person("Emre"));
			testSavedDTO.setId(1L);
			String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
			
			ResultMatcher checkBody = content().json(testDTOAsJSON);
			
			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
		}
	
	// Delete Test
		@Test
		void deleteTest() throws Exception {
			RequestBuilder request = delete(URI + "/delete/1").contentType(MediaType.APPLICATION_JSON);
			ResultMatcher checkStatus = status().isNoContent();
			this.mvc.perform(request).andExpect(checkStatus);
		}

}