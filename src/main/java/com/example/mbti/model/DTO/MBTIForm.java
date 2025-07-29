package com.example.mbti.model.DTO;

import java.util.Map;

public class MBTIForm {
    private Map<Long, String> answers; // questionId → answer (e.g., "agree")

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
