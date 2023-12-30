package com.hcl.ask_buddy.answer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@ToString
@Builder
@Entity
@Table(name = "Likes")
public class Likes {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
//	@Column(name = "queLike")
//	private boolean queLike;
	@Column(name = "ansLike", columnDefinition = "boolean default false")
	private boolean ansLike;
//	@Column(name = "quesDisLike")
//	private boolean queDislike;
	@Column(name = "ansDisLike", columnDefinition = "boolean default false")
	private boolean ansDislike;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "sap_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "ans_id", referencedColumnName = "ans_id")
	private Answers answer;  
	
	
}
