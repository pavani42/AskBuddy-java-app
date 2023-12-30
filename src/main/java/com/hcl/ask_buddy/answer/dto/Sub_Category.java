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
public class Sub_Category {  

	// Sub-Category entity Attributes
	@JsonIgnore
	private long id;
	private String subcat_name;
	@JsonIgnore
	private Category category;
	@JsonIgnore
	private List<Question> questionList;
}
