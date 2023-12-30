package com.hcl.ask_buddy.answer.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.ask_buddy.answer.entity.User;
import com.hcl.ask_buddy.answer.repository.UserRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepo userRepo;
	
	UserDetails userDetails;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getByMail(username);
		if(user == null)
			throw new UsernameNotFoundException("User not found with Username" + username);
		return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), new ArrayList<>());
	}
	
	public User loadUser(String username) {
		return userRepo.getByMail(username);
	}



}
