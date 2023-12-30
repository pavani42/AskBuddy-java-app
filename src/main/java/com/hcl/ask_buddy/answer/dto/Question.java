package com.hcl.ask_buddy.answer.dto;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.ask_buddy.answer.entity.Answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

	// Question Entity Attributes
	private long id;
	private User user;
	private String question;
	private String quesDescription;
	private LocalDateTime date;
	private Category cat;
	private Sub_Category subCat;
	private List<Answers> answerList;
	

}

