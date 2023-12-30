package com.hcl.Answer.controller;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.is;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hcl.ask_buddy.answer.controller.AnswerController;
import com.hcl.ask_buddy.answer.entity.Answers;
import com.hcl.ask_buddy.answer.entity.Question;
import com.hcl.ask_buddy.answer.entity.User;
import com.hcl.ask_buddy.answer.service.AnswerServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



//@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestAnswerController {
	
	private MockMvc mockMvc;
	
	@Mock
	private AnswerServiceImpl answerService;
	
	
	@InjectMocks
	private AnswerController answerController;
	
	
    Answers answer;
	
	User user;
	
	Question question;
	
	
	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
		user = User.builder().sap_Id(52112593).mail("test@gmail.com").username("test").password("$2a$10$x3/ufTsGe5GWleD334csK.9rY1xiIGqP4lEV4VlB6WXQR9ff6bxqW").build();
//		userRepo.save(user);
		question = Question.builder().quesDescription("What is abstraction?").question("What is abstraction?").user(user).date(LocalDateTime.now()).build();
//		questionRepo.save(question);
		answer = Answers.builder().id(1).description("Hiding the details").date(LocalDateTime.now())
				.question(question).user(user).build();
	}
	
	
	@Test
	public void testPostAnswer() throws Exception
	{
		when(answerService.postAnswer(answer.getQuestion().getId(), answer.getDescription())).thenReturn(answer);
		ResultActions result = mockMvc.perform(post("/api/answers/postAnswer" + "?answer=" + answer.getDescription() + "&question=" + question.getQuestion()));
		result.andExpect(status().is4xxClientError())
		.andDo(print())
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testGetAnswersByQuestion() throws Exception
	{
		List<Answers> answerList = new ArrayList<Answers>();
		answerList.add(answer);
		when(answerService.getAnswers(question.getQuestion())).thenReturn(answerList);
		ResultActions result = mockMvc.perform(get("/api/answers/answersByQuestion?question=" + question.getQuestion()));
		result.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(answerList.size())));
	}
	
	@Test
	public void testGetAnswerById() throws Exception
	{
		when(answerService.getAnswersById(1)).thenReturn(answer);
		ResultActions result = mockMvc.perform(get("/api/answers/answerById/" + answer.getId()));
		result.andExpect(status().isOk())
		.andDo(print())
		.andReturn();
	}
	
	@Test
	public void testupdateAnswer() throws Exception 
	{
		when(answerService.updateAnswer(1, "Hiding the data")).thenReturn("Successfully Updated");
		ResultActions result = mockMvc.perform(post("/api/answers/updateAnswer?id=" + 1 + "&answer=Hiding the data"));
		result.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().string("Successfully Updated"));
	}
	
	@Test
	public void testDeleteAnswer() throws Exception
	{
		when(answerService.deleteAnswer(1)).thenReturn("Successfully Deleted");
		ResultActions result = mockMvc.perform(delete("/api/answers/answer/" + 1 + "?sap_Id=" + 52112593));
		result.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().string("Successfully Deleted"));
	}
	
	@Test
	public void getUserAnswers() throws Exception
	{
		when(answerService.getUserAnswers()).thenReturn(null);
		ResultActions result = mockMvc.perform(get("/api/answers/userAnswers"));
		result.andExpect(status().isOk())
		.andDo(print())
		.andReturn();
	}
	
}
