package com.example.mbti.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @Column(columnDefinition = "TEXT")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    //Getters
    public Integer getQuestionId() {
        return questionId;
    }
    public String getQuestionText() {
        return questionText;
    }
    public Section getSection() {
        return section;    
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public List<Option> getOptions() {
        return options;
    }

    //Setters
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setSection(Section section) {
        this.section = section;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    // Default constructor
    public Question() { }

    
    
}
