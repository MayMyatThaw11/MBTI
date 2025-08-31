package com.example.mbti.controller;

import com.example.mbti.model.Career;
import com.example.mbti.model.CareerRecommendation;
import com.example.mbti.model.CourseRecommendation;
import com.example.mbti.model.RecommendationResponse;
import com.example.mbti.model.User;
import com.example.mbti.model.UserAnswer;
import com.example.mbti.repository.CareerRecommendationRepository;
import com.example.mbti.repository.CareerRepository;
import com.example.mbti.repository.CourseRecommendationRepository;
import com.example.mbti.repository.MbtiTypeRepository;
import com.example.mbti.repository.UserAnswerRepository;
import com.example.mbti.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.*;

@Controller
@RequestMapping("/useranswer")
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final MbtiTypeRepository mbtiTypeRepository;
    private final CourseRecommendationRepository courseRecommendationRepository;
    private final CareerRepository careerRepository;

    private final CareerRecommendationRepository recommendationRepository;

    private final String pythonServiceUrl = "http://localhost:8000/recommend";

    public UserAnswerController(UserAnswerRepository userAnswerRepository,
            UserRepository userRepository,
            MbtiTypeRepository mbtiTypeRepository,
            RestTemplate restTemplate,
            CareerRecommendationRepository recommendationRepository,
            CourseRecommendationRepository courseRecommendationRepository,
            CareerRepository careerRepository) {
        this.userAnswerRepository = userAnswerRepository;
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
        this.restTemplate = restTemplate;
        this.recommendationRepository = recommendationRepository;
        this.courseRecommendationRepository = courseRecommendationRepository;
        this.careerRepository = careerRepository;
    }

    // Get answers + recommendations by userId
    @GetMapping("/{userId}")
public String getAnswersWithRecommendations(@PathVariable Integer userId,
                                            @RequestParam(defaultValue = "3") int topN,
                                            Model model) {
    // Fetch user
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    String mbtiCode = (user.getMbtiType() != null) ? user.getMbtiType().getCode() : null;

    // Fetch user answers
    List<UserAnswer> answers = userAnswerRepository.findByUserId(userId);
    List<Map<String, Object>> answerDTOs = new ArrayList<>();
    for (UserAnswer a : answers) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("responseId", a.getResponseId());
        dto.put("userId", user.getId());
        dto.put("questionText", a.getQuestionText());
        dto.put("optionText", a.getOptionText());
        dto.put("mbti", mbtiCode);
        answerDTOs.add(dto);
    }

    System.out.println("User Answers: " + answerDTOs);

    // Send answers to Python service
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(answerDTOs, headers);

    ResponseEntity<RecommendationResponse> response = restTemplate.exchange(
            pythonServiceUrl + "?top_n=" + topN,
            HttpMethod.POST,
            request,
            RecommendationResponse.class);

    List<CareerRecommendation> recommendations = response.getBody().getRecommendations();
    System.out.println("Python Service Recommendations: " + recommendations);

    // Assign userId and save directly
    for (CareerRecommendation rec : recommendations) {
        rec.setUserId(userId);
    }
    // if there are existing recommendations for the user, delete them
    List<CareerRecommendation> existingRecs = recommendationRepository.findByUserId(userId);
    if (!existingRecs.isEmpty()) {
        recommendationRepository.deleteAll(existingRecs);
        System.out.println("Deleted existing recommendations for userId: " + userId);
    }

    recommendationRepository.saveAll(recommendations);

    // Redirect to the user recommendations page
    return "redirect:/recommendations/user/" + userId;

}
    public void saveRecommendations(Integer userId, List<Map<String, Object>> recommendations) {
        List<CareerRecommendation> entities = new ArrayList<>();

        for (Map<String, Object> rec : recommendations) {
            CareerRecommendation entity = new CareerRecommendation();
            entity.setUserId(userId);
            entity.setCareer((String) rec.get("career"));
            entity.setScore(((Number) rec.get("score")).doubleValue());
            entities.add(entity);
        }

        recommendationRepository.saveAll(entities);
    }

}