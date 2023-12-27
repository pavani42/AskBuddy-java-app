package com.hcl.ask_buddy.entity;

import java.util.List;

//import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cat")
public class Category {

	// Category entity Attributes
	@Id
	@Column(name = "cat_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "cat_name")
	private String cat_name;
	@JsonIgnore
	@OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questionList;
}
