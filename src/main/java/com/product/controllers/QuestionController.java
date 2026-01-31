package com.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.models.Question;
import com.product.models.Response;
import com.product.models.WrapperQuestion;
import com.product.services.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	Environment environment;

	@GetMapping("/allQuestions")
	@ResponseBody
	public ResponseEntity<List<Question>> getAllQuestions(){
		return questionService.getAllQuestions();
	}
	
	@ResponseBody
	@GetMapping("/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
		return questionService.getQuestionByCategory(category);
	}
	
	@ResponseBody
	@PostMapping("/addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}
	
	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int id) {
		questionService.deleteQuestion(id);
		return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
	}
	
	@ResponseBody
	@PutMapping("/update/{id}")
	public ResponseEntity<Question> updateTitle(@PathVariable int id, @RequestBody Question question) {
		return questionService.updateTitle(id,question);
	}
	
	@GetMapping("/generate")
	@ResponseBody
	public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String category, @RequestParam int numOfQues){
		return questionService.generateQuestions(category,numOfQues);
	}
	
	@PostMapping("/getQuestionById")
	@ResponseBody
	public ResponseEntity<List<WrapperQuestion>> getQuestionsById(@RequestBody List<Integer> ids){ //questionIds
		System.out.println(environment.getProperty("local.server.port")); //load balancing-automatically done by feignClient
		return questionService.getQuestionsById(ids);
	}
	
	@PostMapping("/getScore")
	@ResponseBody
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
	}
	
	//1.generate all questions for quiz --> take catogory and number of questions, return question
	//2.getQuestion(int id) by questionId, return Wrapper Question as Quiz doesnt require rightAnswer.
	//3.getScore
}
