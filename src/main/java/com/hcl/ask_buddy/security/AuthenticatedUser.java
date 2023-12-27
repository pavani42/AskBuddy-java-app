package com.hcl.ask_buddy.security;

import org.springframework.stereotype.Component;

import com.hcl.ask_buddy.entity.*;

import lombok.Data;

@Component
@Data
public class AuthenticatedUser {
	
	private User_entity user;
	
	private String token;

}
