package com.hcl.ask_buddy.answer.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.hcl.ask_buddy.answer.entity.Question;
import com.hcl.ask_buddy.answer.entity.Answers;
import com.hcl.ask_buddy.answer.entity.Likes;
import com.hcl.ask_buddy.answer.exception.ResourceNotFoundException;
import com.hcl.ask_buddy.answer.repository.*;
import com.hcl.ask_buddy.answer.security.AuthenticatedUser;

@Service
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	AnswerRepo answerRepo;

	@Autowired
	QuestionsRepo questionRepo; 

	@Autowired
	UserRepo userRepo;

	@Autowired
	AuthenticatedUser authUser;
	
	@Autowired
	LikesRepo likesRepo;

	@Autowired
	RestTemplate restTemplate;

	public com.hcl.ask_buddy.answer.entity.Question getQuestion(long questionId)
	{
		return restTemplate.exchange("http://localhost:9191/api/questions/question/" + questionId, HttpMethod.GET, new HttpEntity<>(null),Question.class).getBody();
	}
	@Override
	public Answers postAnswer(long question, String answerDesc) {
		System.out.println(question);
//		question = question.trim();
		answerDesc = answerDesc.trim();
//		System.out.println(userRepo.findById(authUser.getUser().getSap_Id()).get());
		System.out.println(getQuestion(question));
		Answers answer = Answers.builder().question(getQuestion(question)).description(answerDesc)
				.date(LocalDateTime.now()).user(userRepo.findById(authUser.getUser().getMail()).get()).build();
		answerRepo.save(answer);
		return answer;
	}

	@Override
	public List<Answers> getAnswers(String ques) {
		//		System.out.println(ques);
		List<Answers> answers = answerRepo.getAnswerByQuestion(ques);
		if(answers.size() > 0)
			return answers;
		else
			throw new ResourceNotFoundException("Given Question is not found");
	}

	@Override
	public Answers getAnswersById(long id) {

		return answerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Answer Id is not found in the list"));

	}

	@Override
	public String updateAnswer(long id, String answer) {
		answer = answer.trim();
		answerRepo.updateAnswer(id, answer);
		return "Successfully Updated";	
	}

	@Override
	public String deleteAnswer(long id) {
		answerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Given Id Not found"));
		if(answerRepo.findById(id).get().getUser().getSap_Id() == authUser.getUser().getSap_Id())
		{
		   answerRepo.deleteById(id);
		   return "Successfully Deleted";
		}
		else
		{
			return "Unauthorized User";
		}


	}
	
	
    @Override
	public List<Answers> getUserAnswers() {
		return answerRepo.getUserAnswers(authUser.getUser());
	}

	@Override
	public int addLike(long ans_Id) {
//		User user = userRepo.findById(userId).get();
		Answers answer = answerRepo.findById(ans_Id).get();
		try
		{
		if(likesRepo.getlikes(authUser.getUser(), answer) == null)
		{
			Likes likes = Likes.builder().ansDislike(false).ansLike(true).answer(answer).user(authUser.getUser()).build();
			likesRepo.save(likes);
			return likesRepo.getNoOfLikes(ans_Id);
		}
		else
		{
			likesRepo.updateLikes(authUser.getUser(), answer);
			return likesRepo.getNoOfLikes(ans_Id);
		}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return likesRepo.getNoOfLikes(ans_Id);
		}
	}

	@Override
	public int addDislike(long ans_Id) {
		Answers answer = answerRepo.findById(ans_Id).get();
		try
		{
		if(likesRepo.getlikes(authUser.getUser(), answer) == null)
		{
			Likes likes = Likes.builder().ansDislike(true).ansLike(false).answer(answer).user(authUser.getUser()).build();
			likesRepo.save(likes);
			return likesRepo.getNoOfLikes(ans_Id);
		}
		else
		{
			likesRepo.updateDisLikes(authUser.getUser(), answer);
			return likesRepo.getNoOfLikes(ans_Id);
		}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return likesRepo.getNoOfLikes(ans_Id);
		}
	}

	@Override
	public int getNoOfLikes(long ans_Id) {
		return likesRepo.getNoOfLikes(ans_Id);
	}

	@Override
	public int getNoOfDilikes(long ans_Id) {
		return likesRepo.getNoOfDisLikes(ans_Id);
	}

	@Override
	public List<Likes> getListOfLikes() {
		return likesRepo.getLikesList(authUser.getUser());
	}

}
