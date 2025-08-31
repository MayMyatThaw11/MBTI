package com.example.mbti.model.DTO;

import java.util.List;

public class UserWithAnswersDTO {
    private Integer userId;
    private String userName;
    private List<QuestionAnswerDTO> answers;

    public UserWithAnswersDTO(Integer userId, String userName, List<QuestionAnswerDTO> answers) {
        this.userId = userId;
        this.userName = userName;
        this.answers = answers;
    }

    // getters
    public Integer getUserId() { return userId; }
    public String getUserName() { return userName; }
    public List<QuestionAnswerDTO> getAnswers() { return answers; }
}
