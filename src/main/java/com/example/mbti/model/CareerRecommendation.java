package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "career_recommendations")
public class CareerRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId; 
    private String career;
    private double score;

    // Optional: timestamp
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCareer() { return career; }
    public void setCareer(String career) { this.career = career; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
}
