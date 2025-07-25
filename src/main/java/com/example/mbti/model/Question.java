package com.example.mbti.model;


import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    // Constructors
    public Question() {}

    public Question(String text) {
        this.text = text;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    // no setter for id, since it's auto-generated

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

