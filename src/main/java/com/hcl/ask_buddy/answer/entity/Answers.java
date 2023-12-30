package com.hcl.ask_buddy.answer.entity;


import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.hcl.ask_buddy.answer.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "answers")
public class Answers {

	// Answer entity Attribues
	@Id
	@Column(name = "ans_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(name = "que_id", referencedColumnName = "que_id")
	private Question question;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_mail")
	private User user;
	@Column(name = "ans_description", columnDefinition = "varchar(5000)", length = 5000)
	private String description;
	@Column(name = "ans_date")
	private LocalDateTime date;
	
}
