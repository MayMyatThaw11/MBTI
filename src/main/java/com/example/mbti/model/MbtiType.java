package com.example.mbti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mbti_types")
public class MbtiType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4, nullable = false, unique = true)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    private String description;

    // No-args constructor
    public MbtiType() {
    }

    // All-args constructor
    public MbtiType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
