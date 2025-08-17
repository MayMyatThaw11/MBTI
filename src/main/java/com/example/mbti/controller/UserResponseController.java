package com.example.mbti.controller;

import com.example.mbti.model.User;
import com.example.mbti.model.Question;
import com.example.mbti.model.Option;
import com.example.mbti.model.UserResponse;
import com.example.mbti.repository.UserRepository;
import com.example.mbti.repository.QuestionRepository;
import com.example.mbti.repository.OptionRepository;
import com.example.mbti.repository.UserResponseRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responses")
public class UserResponseController {

    private final UserResponseRepository userResponseRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public UserResponseController(UserResponseRepository userResponseRepository,
                                  UserRepository userRepository,
                                  QuestionRepository questionRepository,
                                  OptionRepository optionRepository) {
        this.userResponseRepository = userResponseRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @PostMapping
    public UserResponse submitResponse(@RequestBody UserResponse request) {
        // Fetch the associated entities from DB
        User user = userRepository.findById(request.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

        Question question = questionRepository.findById(request.getQuestion().getQuestionId())
                        .orElseThrow(() -> new RuntimeException("Question not found"));

        Option option = optionRepository.findById(request.getOption().getOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found"));

        // Set entities in UserResponse
        UserResponse response = new UserResponse();
        response.setUser(user);
        response.setQuestion(question);
        response.setOption(option);
        // answeredAt is already set by default

        // Save to MySQL
        return userResponseRepository.save(response);
    }
}

