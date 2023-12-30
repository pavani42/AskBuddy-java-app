package com.hcl.ask_buddy.answer.repository;

import org.springframework.stereotype.Repository;

import com.hcl.ask_buddy.answer.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Question Repository
@Repository
public interface QuestionsRepo extends JpaRepository<Question, Long>{
	@Query("select ques from Question ques where ques.question = ?1")
	public Question getQuestionByQuestion(String quest);
}
