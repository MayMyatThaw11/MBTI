package com.example.mbti.repository;


import com.example.mbti.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CareerRepository extends JpaRepository<Career, Long> {
    
}

