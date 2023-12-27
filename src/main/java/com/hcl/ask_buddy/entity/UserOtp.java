package com.hcl.ask_buddy.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserOtp {
	@Id
	@Column(name = "sno")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sno;
	@Column(name = "timestamp", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime dtntm;
	@Column(name = "otp")
	private String otp;
	@OneToOne
	@JoinColumn(name = "mailId", nullable = false, referencedColumnName = "user_mail")
	private User_entity user;
}
