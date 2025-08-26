// package com.example.mbti.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "user_responses")
// public class UserResponse {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long responseId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "user_id", nullable = false)
//     private User user;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "question_id", nullable = false)
//     private Question question;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "option_id", nullable = false)
//     private Option option;

//     @Column(name = "answered_at", nullable = false, updatable = false)
//     private LocalDateTime answeredAt = LocalDateTime.now();

//     // Getters and Setters
//     public Long getResponseId() { return responseId; }
//     public void setResponseId(Long responseId) { this.responseId = responseId; }

//     public User getUser() { return user; }
//     public void setUser(User user) { this.user = user; }

//     public Question getQuestion() { return question; }
//     public void setQuestion(Question question) { this.question = question; }

//     public Option getOption() { return option; }
//     public void setOption(Option option) { this.option = option; }

//     public LocalDateTime getAnsweredAt() { return answeredAt; }
//     public void setAnsweredAt(LocalDateTime answeredAt) { this.answeredAt = answeredAt; }
// }
