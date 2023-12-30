package com.hcl.ask_buddy.answer.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
@Table(name = "user")
public class User {

	//  User Entity Attributes
	@Column(name = "sap_id", nullable = false, unique = true)
	private long sap_Id;

	@Id
	@Column(name = "user_mail")
	private String mail;
	@Column(name = "user_name")
	private String username;
	//	@JsonIgnore
	@Column(name = "password")
	private String password;
	@Lob
	@Column(name = "pic")
	private byte[] pic;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Question> questionList;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Answers> answerList;

}
