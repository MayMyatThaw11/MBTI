package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mbti_types")
public class MbtiType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 4, nullable = false, unique = true)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(name = "key_traits", length = 255)
    private String keyTraits;

@Column(name = "strength", columnDefinition = "TEXT", nullable = false)
    private String strengths;

@Column(name = "weakness", columnDefinition = "TEXT", nullable = false)
    private String weaknesses;

    // Default constructor
    public MbtiType() {
    }

    // All-args constructor
    public MbtiType(String code, String name, String description, 
                    String keyTraits, String strengths, String weaknesses) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.keyTraits = keyTraits;
        this.strengths = strengths;
        this.weaknesses = weaknesses;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyTraits() {
        return keyTraits;
    }   

    public void setKeyTraits(String keyTraits) {
        this.keyTraits = keyTraits;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }
}
