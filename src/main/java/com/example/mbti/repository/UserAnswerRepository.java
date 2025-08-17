package com.example.mbti.repository;


import com.example.mbti.model.UserAnswer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
}

