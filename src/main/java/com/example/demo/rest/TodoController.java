package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDto;
import com.example.demo.persistence.domain.Todo;
import com.example.demo.service.TodoService;

@RestController
@CrossOrigin
@RequestMapping("/todo") // this is to further define the path
public class TodoController {

	private TodoService service;
	
	@Autowired
	public TodoController(TodoService service) {
		super();
		this.service = service;
	}
	
	// Create method
		@PostMapping("/create")
		public ResponseEntity<TodoDto> create(@RequestBody Todo todo) {
			TodoDto created = this.service.create(todo);
			return new ResponseEntity<>(created, HttpStatus.CREATED);
			// http status code - 201 (created)

		}
		
		// read all method
		@GetMapping("/read")
		public ResponseEntity<List<TodoDto>> read() {
			return ResponseEntity.ok(this.service.readAll());
			// ok - 200
		}
		
		// read one
		@GetMapping("/read/{id}")
		public ResponseEntity<TodoDto> readOne(@PathVariable Long id) {
			return ResponseEntity.ok(this.service.readOne(id));
		}
		
		// update
		@PutMapping("/update/{id}")
		public ResponseEntity<TodoDto> update(@PathVariable Long id, @RequestBody TodoDto todoDto) {
			return new ResponseEntity<>(this.service.update(todoDto, id), HttpStatus.ACCEPTED);
		}
		
		// Delete one
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<TodoDto> delete(@PathVariable Long id) {
			return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
					// no_content - if deleted successfully then should return nothing
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			// if the record isnt found!
		}
		
		@GetMapping("findByName/{name}")
		public ResponseEntity<List<TodoDto>> findByName(@PathVariable String name) {
			return ResponseEntity.ok(this.service.findByName(name));
		}

}
