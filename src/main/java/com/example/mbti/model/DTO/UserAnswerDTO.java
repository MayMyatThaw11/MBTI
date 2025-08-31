package com.example.mbti.model.DTO;

public class UserAnswerDTO {
    private Long responseId;
    private Integer userId;
    private String questionText;
    private String optionText;
    private String mbti;

    // Getters & Setters
    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public String getMbti() { return mbti; }
    public void setMbti(String mbti) { this.mbti = mbti; }
}
