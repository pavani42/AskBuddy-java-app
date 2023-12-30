package com.hcl.ask_buddy.answer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hcl.ask_buddy.answer.entity.*;
import com.hcl.ask_buddy.answer.service.AnswerServiceImpl;

import java.util.*;

// Answer Controller
@RestController
@RequestMapping("/api/answers")
//@CrossOrigin(origins = "http://localhost:3000")
public class AnswerController {
	
	@Autowired
	AnswerServiceImpl answerService;
	
	
	// Controller for fetching Ans by Question
	@GetMapping("/answersByQuestion")
	public List<Answers> getAnswersByQuestion(@RequestParam String question)
	{
		return answerService.getAnswers(question);
	}
	
	//Controller for publishing answer
	@PostMapping("/postAnswer")
	public Answers postAnswer(@RequestParam String answer, @RequestParam long question)
	{
		return answerService.postAnswer(question, answer);
	}
	
	// Controller for fetching answer by ID
	@GetMapping("/answerById/{id}")
	public Answers getAnswerById(@PathVariable("id") long id)
	{
		return answerService.getAnswersById(id);
	}
	
	// Controller for updating answer by ID
	@PostMapping("/updateAnswer")
	public String updateAnswer(@RequestParam long id, @RequestParam String answer)
	{
		return answerService.updateAnswer(id, answer);
	}
	
	// Controller for deleting answer by ID
	@DeleteMapping("/answer/{id}")
	public String deleteAnswer(@PathVariable("id") long id)
	{
		return answerService.deleteAnswer(id);
	}	
	
	@GetMapping("/getQuestion/{questionId}")
	public Question getquestion(long question)
	{
		return answerService.getQuestion(question);
	}
	
	@GetMapping("/userAnswers")
	public List<Answers> getUserAnswers()
	{
		return answerService.getUserAnswers();
	}
	
	@PostMapping("/updateLikes")
	public int postLikes(@RequestParam long ans_Id)
	{
		return answerService.addLike(ans_Id);
	}
	
	@PostMapping("/updateDisLikes")
	public int postDisLikes(@RequestParam long ans_Id)
	{
		return answerService.addDislike(ans_Id);
	}
	
	@GetMapping("/getLikes")
	public int getLikes(@RequestParam long ans_Id)
	{
		return answerService.getNoOfLikes(ans_Id);
	}
	
	@GetMapping("/getDisLikes")
	public int getDisLikes(@RequestParam long ans_Id)
	{
		return answerService.getNoOfDilikes(ans_Id);
	}
	
	@GetMapping("/getUserLikesAnswers")
	public List<Likes> getLikesList()
	{
		return answerService.getListOfLikes();
	}
}
