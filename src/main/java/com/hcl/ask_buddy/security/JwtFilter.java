package com.hcl.ask_buddy.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticatedUser requestToken;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
		{
			token = requestTokenHeader.substring(7);
			try
			{ 
			  username = jwtUtil.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Unable to get JwtToken");
			} 
			catch(ExpiredJwtException e)
			{
				System.out.println("Jwt Token Expired");
			}	
		}
		else
		{
			logger.warn("Jwt Token Not Started with Bearer String");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(token, userDetails)) 
			{
				UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
				userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
			}
		}
		requestToken.setUser(jwtUserDetailsService.loadUser(username));
		requestToken.setToken(token);
		filterChain.doFilter(request, response);
	}
	
	
	
	
	

}
