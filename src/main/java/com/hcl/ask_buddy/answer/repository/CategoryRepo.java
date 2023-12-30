package com.hcl.ask_buddy.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.ask_buddy.answer.entity.*;

// Category Repository
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{
//	@Query("select Category")
}
