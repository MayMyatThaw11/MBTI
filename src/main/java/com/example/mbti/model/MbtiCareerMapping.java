package com.example.mbti.model;


import jakarta.persistence.*;

@Entity
@Table(name = "mbti_career_mapping")  // Name it as needed
public class MbtiCareerMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "mbti_type_id", nullable = false)
    private MbtiType mbtiType;

    @ManyToOne
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    // Constructors
    public MbtiCareerMapping() {}

    public MbtiCareerMapping(MbtiType mbtiType, Career career) {
        this.mbtiType = mbtiType;
        this.career = career;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MbtiType getMbtiType() {
        return mbtiType;
    }

    public void setMbtiType(MbtiType mbtiType) {
        this.mbtiType = mbtiType;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
}

