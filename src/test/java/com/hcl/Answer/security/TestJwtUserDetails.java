package com.hcl.Answer.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hcl.ask_buddy.answer.entity.User;
import com.hcl.ask_buddy.answer.repository.UserRepo;
import com.hcl.ask_buddy.answer.security.JwtUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class TestJwtUserDetails {
	@InjectMocks
	private JwtUserDetailsService userDetailsService;
	
	@Mock
	UserRepo userRepo;
	
	User user;
	
	@BeforeEach
	public void setUp()
	{
		user = User.builder().sap_Id(52112593).mail("test@gmail.com").username("test").password("$2a$10$x3/ufTsGe5GWleD334csK.9rY1xiIGqP4lEV4VlB6WXQR9ff6bxqW").build();
	}
	
	@Test
	public void testLoadUserByUsername()
	{
		Mockito.when(userRepo.getByMail(user.getMail())).thenReturn(user);
		assertNotNull(userDetailsService.loadUserByUsername(user.getMail()));
	}
	
	@Test
	public void testLoadUserByUsername_throws_Exception()
	{
		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("pavani");
		});
	}
	
	@Test
	public void testloadUser()
	{
		Mockito.when(userRepo.getByMail(user.getMail())).thenReturn(user);
		assertEquals(userDetailsService.loadUser(user.getMail()), user);
	}

}
