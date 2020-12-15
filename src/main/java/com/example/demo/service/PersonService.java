package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PersonDto;
import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.persistence.domain.Person;
import com.example.demo.persistence.repo.PersonRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class PersonService {
	// this is where our business logic will happen

		//	this is also where CRUD logic will take place.

		// implements are crud functionality
		private PersonRepo repo;

		// makes object mapping easy by automatically determining how one object model
		// maps to another.
		private ModelMapper mapper;
		
		private PersonDto mapToDTO(Person person) {
			return this.mapper.map(person, PersonDto.class);
		}

		@Autowired
		public PersonService(PersonRepo repo, ModelMapper mapper) {
			super();
			this.repo = repo;
			this.mapper = mapper;
		}

		// Create
		public PersonDto create(Person person) {
			return this.mapToDTO(this.repo.save(person));
		}
		
		// read all method
		public List<PersonDto> readAll() {
			return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
			// stream - returns a sequential stream considering collection as its source
			// map - used to map each element to its corresponding result
			// :: - for each e.g. Random random = new Random();
			// random.ints().limit(10).forEach(System.out::println);
			// Collectors - used to return a list or string
		}

		// read one method
		public PersonDto readOne(Long id) {
			return this.mapToDTO(this.repo.findById(id).orElseThrow(PersonNotFoundException::new));
		}
		
		// update
		public PersonDto update(PersonDto personDto, Long id) {
			// check if record exists
			Person toUpdate = this.repo.findById(id).orElseThrow(PersonNotFoundException::new);
			// set the record to update
			toUpdate.setName(personDto.getName());
			// check update for any nulls
			SpringBeanUtil.mergeNotNull(personDto, toUpdate);
			// return the method from repo
			return this.mapToDTO(this.repo.save(toUpdate));

		}

		// what happens when you try to merge null stuff?

		// Delete
		public boolean delete(Long id) {
			this.repo.deleteById(id);// true
			return !this.repo.existsById(id);// true
		}
				
}
