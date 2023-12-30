package com.hcl.ask_buddy.answer.service;

import java.util.List;

import com.hcl.ask_buddy.answer.entity.Answers;
import com.hcl.ask_buddy.answer.entity.Likes;

public interface AnswerService {
	public Answers postAnswer(long question, String Answer);
	public List<Answers> getAnswers(String question);
	public Answers getAnswersById(long id);
	public String updateAnswer(long id, String answer);
	public String deleteAnswer(long id); 
	public List<Answers> getUserAnswers();
//	public int addLike(long quesId);
//	public int addDisLike(long quesId);
//	public int decrementLike(long quesId);
//	public int decrementDisLike(long quesId);
	public int addLike(long ans_Id);
	public int addDislike(long ans_Id);
	public int getNoOfLikes(long ans_Id);
	public int getNoOfDilikes(long ans_Id);
	public List<Likes> getListOfLikes();
	
	
}
