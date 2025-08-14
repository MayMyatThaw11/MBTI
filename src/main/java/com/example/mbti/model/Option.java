package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer optionId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String optionText;

    //Getter
    public Integer getOptionId() {
        return optionId;
    }
    public Question getQuestion() {
        return question;
    }
    public String getOptionText() {
        return optionText;
    }
    //Setter
    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
    // Default constructor
    public Option() {}
    
}

