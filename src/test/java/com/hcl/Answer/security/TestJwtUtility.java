package com.hcl.Answer.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.hcl.ask_buddy.answer.security.JwtUtil;

public class TestJwtUtility {
	
	private JwtUtil jwtUtil;
	
	private String secret = "java";
	
	UserDetails userDetails;
	
	private String token;
	
	@BeforeEach
	public void setUp()
	{
		jwtUtil = new JwtUtil(secret);
		userDetails = new org.springframework.security.core.userdetails.User("Test", "Test", new ArrayList<>());
		token = jwtUtil.generateToken(userDetails);
	}
	
	@Test
	public void testGenerateToken()
	{
		UserDetails userName = new org.springframework.security.core.userdetails.User("Test", "Test", new ArrayList<>());
		String token = jwtUtil.generateToken(userName);
		assertNotNull(token);
		
	}
	
	@Test
	public void testGetUserNamefromToken()
	{
		String username = jwtUtil.getUsernameFromToken(token);
		assertEquals(username, userDetails.getUsername());
	}
	
	@Test
	public void testGetIssuedAtDateFromToken()
	{
		Date issuedDate = jwtUtil.getIssuedAtDateFromToken(token);
		Date expectedDate = new Date(System.currentTimeMillis());
		assertEquals(issuedDate.getDate(), expectedDate.getDate());
	}
	
	@Test
	public void testCanTokenBeRefreshed()
	{
		assertTrue(jwtUtil.canTokenBeRefreshed(token));
	}
	
	@Test
	public void testValidateToken()
	{
		assertTrue(jwtUtil.validateToken(token, userDetails));
	}
	
	
	
	
}
