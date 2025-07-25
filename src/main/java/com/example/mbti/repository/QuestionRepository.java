package com.example.mbti.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mbti.model.Question; 



public interface QuestionRepository  extends JpaRepository<Question, Long> {
   
}
