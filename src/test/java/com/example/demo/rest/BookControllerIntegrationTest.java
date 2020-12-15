package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.demo.dto.BookDto;
import com.example.demo.dto.TodoDto;
import com.example.demo.persistence.domain.Book;
import com.example.demo.persistence.domain.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
// sql runs in order schema followed by data file - JH dont make the mistake
@Sql(scripts = { "classpath:todo-schema.sql",
		"classpath:todo-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class BookControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private TodoDto mapToDTO(Todo todo) {
		return this.mapper.map(todo, TodoDto.class);
	}

	private final Todo TEST_TODO_1 = new Todo(1L, "Buy eggs", false);
	private final Todo TEST_TODO_2 = new Todo(2L, "Wash dishes", false);
	private final Todo TEST_TODO_3 = new Todo(3L, "Buy cake", false);
	private final Todo TEST_TODO_4 = new Todo(4L, "Finish project",false);

	// I also want to create a list of cars that i can use later
	private final List<Todo> LISTOFTODOS = List.of(TEST_TODO_1, TEST_TODO_2, TEST_TODO_3, TEST_TODO_4);
	private final String URI = "/todo";

	// Create test
	@Test
	void createTest() throws Exception {
		TodoDto testDTO = mapToDTO(new Todo(1L, "Buy eggs", false));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isCreated();

		BookDto testSavedDTO = mapToDTO(new Book("Harry Potter", "JK Rowling", "JK", 2000, 300));
		testSavedDTO.setId(5L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

//		this.mvc.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON))
//				.andExpect(status().isCreated()).andExpect(content().json(testSavedDTOAsJSON));
	}

}