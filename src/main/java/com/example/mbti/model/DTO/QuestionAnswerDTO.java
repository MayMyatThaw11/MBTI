package com.example.mbti.model.DTO;

public class QuestionAnswerDTO {
    private Long questionId;
    private String questionText;
    private String answer;

    public QuestionAnswerDTO(Long questionId, String questionText, String answer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer = answer;
    }

    // getters
    public Long getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public String getAnswer() { return answer; }
}
