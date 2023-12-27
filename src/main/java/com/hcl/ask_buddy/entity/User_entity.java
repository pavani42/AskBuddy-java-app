package com.hcl.ask_buddy.entity;

import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "user")
public class User_entity {

	// User Entity Attribute
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
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.PERSIST,
            mappedBy = "user"
            ,orphanRemoval = true)
	private UserOtp userOtp;

}
