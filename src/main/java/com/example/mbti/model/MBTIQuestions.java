package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mbti_questions")
public class MBTIQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    // MBTI Dimension this question relates to: EI, SN, TF, JP
    @Column(nullable = false, length = 5)
    private String dimension;

    // Direction the question leans toward: E, I, S, N, T, F, J, P
    @Column(nullable = false, length = 1)
    private String direction;

    // === Constructors ===
    public MBTIQuestions() {}

    public MBTIQuestions(String text, String dimension, String direction) {
        this.text = text;
        this.dimension = dimension;
        this.direction = direction;
    }

    // === Getters and Setters ===
    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
