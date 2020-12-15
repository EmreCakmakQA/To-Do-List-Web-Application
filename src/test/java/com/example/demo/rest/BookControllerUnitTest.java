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

import com.example.demo.dto.BookDto;
import com.example.demo.persistence.domain.Book;
import com.example.demo.service.BookService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application properties file.
public class BookControllerUnitTest {

	@Autowired
	private BookController controller;

	@MockBean
	private BookService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private BookDto maptoDto(Book book) {
		return this.mapper.map(book, BookDto.class);
	}

	// During our test we want give it some data to use
	private final Book TEST_BOOK_1 = new Book(1L, "Harry Potter", "JK Rowling", "JK", 2000, 300);
	private final Book TEST_BOOK_2 = new Book(2L, "Life Of EC", "Emre Cakmak", "EC", 2020, 999);
	private final Book TEST_BOOK_3 = new Book(3L, "EC: A life well lived", "Emre Cakmak", "EC", 2001, 509);
	private final Book TEST_BOOK_4 = new Book(4L, "Mokito Doesn't Like Me", "Troy Alleyene", "EC", 2005, 3001);

	// I also want to create a list of cars that i can use later
	private final List<Book> LISTOFBOOKS = List.of(TEST_BOOK_1, TEST_BOOK_2, TEST_BOOK_3, TEST_BOOK_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_BOOK_1)).thenReturn(this.maptoDto(TEST_BOOK_1));
		assertThat(new ResponseEntity<BookDto>(this.maptoDto(TEST_BOOK_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_BOOK_1));
		verify(this.service, atLeastOnce()).create(TEST_BOOK_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_BOOK_1.getId())).thenReturn(this.maptoDto(TEST_BOOK_1));
		assertThat(new ResponseEntity<BookDto>(this.maptoDto(TEST_BOOK_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_BOOK_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_BOOK_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<BookDto> dtos = LISTOFBOOKS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_BOOK_1), TEST_BOOK_1.getId())).thenReturn(this.maptoDto(TEST_BOOK_1));
		assertThat(new ResponseEntity<BookDto>(this.maptoDto(TEST_BOOK_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_BOOK_1.getId(), this.maptoDto(TEST_BOOK_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_BOOK_1), TEST_BOOK_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_BOOK_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_BOOK_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_BOOK_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_BOOK_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_BOOK_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_BOOK_1.getId());

	}

	// Find by name
	@Test
	void findByName() throws Exception {
		List<BookDto> dtos = LISTOFBOOKS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.findByName(TEST_BOOK_1.getName())).thenReturn(dtos);
		assertThat(this.controller.findByName(TEST_BOOK_1.getName()))
				.isEqualTo(new ResponseEntity<List<BookDto>>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).findByName(TEST_BOOK_1.getName());
	}

}