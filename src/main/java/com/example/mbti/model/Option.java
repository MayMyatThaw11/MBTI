package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many options belong to one question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String optionText;  // e.g., "Yes", "No", or "Often"

    // MBTI dimension this option contributes to, e.g., "I", "E", "N", "S", "T", "F", "J", "P"
    @Column(nullable = false, length = 1)
    private String mbtiDimension;

    public Option() {}

    public Option(Question question, String optionText, String mbtiDimension) {
        this.question = question;
        this.optionText = optionText;
        this.mbtiDimension = mbtiDimension;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getMbtiDimension() {
        return mbtiDimension;
    }

    public void setMbtiDimension(String mbtiDimension) {
        this.mbtiDimension = mbtiDimension;
    }
}
