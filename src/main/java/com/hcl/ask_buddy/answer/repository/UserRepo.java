package com.hcl.ask_buddy.answer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.ask_buddy.answer.entity.User;

// User Repository
@Repository
public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findById(String mail);

	public User getByMail(String username);

}
