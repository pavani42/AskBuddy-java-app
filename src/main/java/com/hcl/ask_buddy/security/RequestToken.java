package com.hcl.ask_buddy.security;

import org.springframework.stereotype.Component;

@Component
public class RequestToken {
	
	private String token;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
