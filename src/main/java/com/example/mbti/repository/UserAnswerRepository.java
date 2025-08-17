package com.example.mbti.repository;

import com.example.mbti.model.UserAnswer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
     List<UserAnswer> findByUserId(Integer userId);
    
}
