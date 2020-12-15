package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {
	private Long id;
	private String name;
	
	private List<TodoDto> todos = new ArrayList<>();
	// This will return JSON

}
