package com.hcl.ask_buddy.answer.dto;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	// Category entity Attributes
	private long id;
	private String cat_name;
	private List<Question> questionList;
}
