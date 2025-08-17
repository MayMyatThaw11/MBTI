package com.example.mbti.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mbti.model.MBTIQuestions; 



public interface MBTIQuestionRepository  extends JpaRepository<MBTIQuestions, Integer> {
   
}
