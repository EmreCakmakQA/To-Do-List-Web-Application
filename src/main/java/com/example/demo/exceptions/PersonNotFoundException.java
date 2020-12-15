package com.example.demo.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "A Person with that id does not exist, try again")
public class PersonNotFoundException extends EntityNotFoundException{
	private static final long serialVersionUID = 1L;
}
