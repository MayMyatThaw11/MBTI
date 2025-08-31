package com.example.mbti.repository;


import com.example.mbti.model.CourseRecommendation;
import com.example.mbti.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRecommendationRepository extends JpaRepository<CourseRecommendation, Long> {
    List<CourseRecommendation> findByCareer(Career career);
    List<CourseRecommendation> findByCareerId(Integer careerId);
}