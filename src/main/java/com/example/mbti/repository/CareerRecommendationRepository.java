package com.example.mbti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mbti.model.CareerRecommendation;

@Repository
public interface CareerRecommendationRepository extends JpaRepository<CareerRecommendation, Integer> {
    // Optional: find all recommendations by userId
    List<CareerRecommendation> findByUserId(Integer userId);
}
