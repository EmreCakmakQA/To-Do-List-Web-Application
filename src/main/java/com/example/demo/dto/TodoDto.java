package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDto {
	
	private Long id;
	private String name;
	private Boolean isComplete;
	// this will spit out JSON		
		
}
