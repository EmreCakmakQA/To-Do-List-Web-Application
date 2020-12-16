package com.example.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.TodoDto;
import com.example.demo.persistence.domain.Todo;
import com.example.demo.service.TodoService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application properties file.
public class TodoControllerUnitTest {

	@Autowired
	private TodoController controller;

	@MockBean
	private TodoService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private TodoDto maptoDto(Todo todo) {
		return this.mapper.map(todo, TodoDto.class);
	}

	// During our test we want give it some data to use
	private final Todo TEST_TODO_1 = new Todo(1L, "Buy eggs", false);
	private final Todo TEST_TODO_2 = new Todo(2L, "Wash dishes", false);
	private final Todo TEST_TODO_3 = new Todo(3L, "Buy cake", false);
	private final Todo TEST_TODO_4 = new Todo(4L, "Finish project",false);

	// I also want to create a list of todos that i can use later
	private final List<Todo> LISTOFTODOS = List.of(TEST_TODO_1, TEST_TODO_2, TEST_TODO_3, TEST_TODO_4);
	

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TODO_1)).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<TodoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_TODO_1));
		verify(this.service, atLeastOnce()).create(TEST_TODO_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_TODO_1.getId())).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<TodoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_TODO_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_TODO_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<TodoDto> dtos = LISTOFTODOS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_TODO_1), TEST_TODO_1.getId())).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<TodoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_TODO_1.getId(), this.maptoDto(TEST_TODO_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_TODO_1), TEST_TODO_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_TODO_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_TODO_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_TODO_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_TODO_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());

	}

	// Find by name
	@Test
	void findByName() throws Exception {
		List<TodoDto> dtos = LISTOFTODOS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.findByName(TEST_TODO_1.getName())).thenReturn(dtos);
		assertThat(this.controller.findByName(TEST_TODO_1.getName()))
				.isEqualTo(new ResponseEntity<List<TodoDto>>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).findByName(TEST_TODO_1.getName());
	}

}