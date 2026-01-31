package com.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findByCategory(String category);
	
	@Query(value="select * from question where category=:category order by rand() ",nativeQuery=true)
	List<Question> createQuizWithRandomQues(@Param(value = "category") String category, Pageable pageable);

	@Query(value="select id from question where category=:category order by rand()",nativeQuery=true)
	List<Integer> generateQuestions(@Param(value = "category") String category, Pageable pageable);


}
