package com.hcl.ask_buddy.answer.security;

import org.springframework.stereotype.Component;

import com.hcl.ask_buddy.answer.entity.User;

import lombok.Data;

@Component
@Data
public class AuthenticatedUser {
	
	private User user;

}
