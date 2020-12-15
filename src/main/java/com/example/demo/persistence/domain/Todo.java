package com.example.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private Boolean isComplete;
	
	@ManyToOne
	private Person person;

	public Todo(Long id, String title, Boolean isComplete) {
		super();
		this.id = id;
		this.name = title;
		this.isComplete = isComplete;
	}

	public Todo(String title, Boolean isComplete) {
		super();
		this.name = title;
		this.isComplete = isComplete;
	}
	
	
}



