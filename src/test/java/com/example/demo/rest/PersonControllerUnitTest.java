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

import com.example.demo.dto.PersonDto;
import com.example.demo.persistence.domain.Person;
import com.example.demo.service.PersonService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application properties file.
public class PersonControllerUnitTest {

	@Autowired
	private PersonController controller;

	@MockBean
	private PersonService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private PersonDto maptoDto(Person person) {
		return this.mapper.map(person, PersonDto.class);
	}

	// During our test we want give it some data to use
	private final Person TEST_PERSON_1 = new Person(1L, "Emre");
	private final Person TEST_PERSON_2 = new Person(2L, "Nouha");
	private final Person TEST_PERSON_3 = new Person(3L, "Lawrence");
	private final Person TEST_PERSON_4 = new Person(4L, "Troy");

	// I also want to create a list of todos that i can use later
	private final List<Person> LISTOFPEOPLE = List.of(TEST_PERSON_1, TEST_PERSON_2, TEST_PERSON_3, TEST_PERSON_4);
	

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_PERSON_1)).thenReturn(this.maptoDto(TEST_PERSON_1));
		assertThat(new ResponseEntity<PersonDto>(this.maptoDto(TEST_PERSON_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_PERSON_1));
		verify(this.service, atLeastOnce()).create(TEST_PERSON_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_PERSON_1.getId())).thenReturn(this.maptoDto(TEST_PERSON_1));
		assertThat(new ResponseEntity<PersonDto>(this.maptoDto(TEST_PERSON_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_PERSON_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_PERSON_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<PersonDto> dtos = LISTOFPEOPLE.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_PERSON_1), TEST_PERSON_1.getId())).thenReturn(this.maptoDto(TEST_PERSON_1));
		assertThat(new ResponseEntity<PersonDto>(this.maptoDto(TEST_PERSON_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_PERSON_1.getId(), this.maptoDto(TEST_PERSON_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_PERSON_1), TEST_PERSON_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_PERSON_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_PERSON_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_PERSON_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_PERSON_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_PERSON_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_PERSON_1.getId());

	}

	// Find by name
//	@Test
//	void findByName() throws Exception {
//		List<PersonDto> dtos = LISTOFPEOPLE.stream().map(this::maptoDto).collect(Collectors.toList());
//		when(this.service.findByName(TEST_PERSON_1.getName())).thenReturn(dtos);
//		assertThat(this.controller.findByName(TEST_PERSON_1.getName()))
//				.isEqualTo(new ResponseEntity<List<PersonDto>>(dtos, HttpStatus.OK));
//		verify(this.service, atLeastOnce()).findByName(TEST_PERSON_1.getName());
//	}

}