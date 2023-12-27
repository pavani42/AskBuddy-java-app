package com.hcl.ask_buddy.entity;


import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
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
	private User_entity user;
	@Column(name = "ans_description")
	private String description;
	@Column(name = "ans_date")
	private LocalDateTime date;	
	
}
