package com.example.mbti.controller;

import com.example.mbti.model.CareerRecommendation;
import com.example.mbti.model.RecommendationResponse;
import com.example.mbti.model.User;
import com.example.mbti.model.UserAnswer;
import com.example.mbti.model.DTO.UserAnswerDTO;
import com.example.mbti.repository.CareerRecommendationRepository;
import com.example.mbti.repository.MbtiTypeRepository;
import com.example.mbti.repository.UserAnswerRepository;
import com.example.mbti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// import java.util.ArrayList;
// import java.util.List;

// @RestController
// @RequestMapping("/useranswer")
// public class UserAnswerController {

//     private final UserAnswerRepository userAnswerRepository;
//     private final UserRepository userRepository;
//     private final MbtiTypeRepository mbtiTypeRepository;

//     @Autowired
//     public UserAnswerController(UserAnswerRepository userAnswerRepository,
//                                 UserRepository userRepository,
//                                 MbtiTypeRepository mbtiTypeRepository) {
//         this.userAnswerRepository = userAnswerRepository;
//         this.userRepository = userRepository;
//         this.mbtiTypeRepository = mbtiTypeRepository;
//     }

//     // Get all answers with MBTI code
//     @GetMapping
//     public List<UserAnswerDTO> getAllAnswers() {
//         List<UserAnswer> answers = userAnswerRepository.findAll();
//         List<UserAnswerDTO> dtoList = new ArrayList<>();

//         for (UserAnswer answer : answers) {
//             User user = userRepository.findById(answer.getUserId())
//                     .orElseThrow(() -> new RuntimeException("User not found with ID: " + answer.getUserId()));

//             UserAnswerDTO dto = new UserAnswerDTO();
//             dto.setResponseId(answer.getResponseId());
//             dto.setUserId(user.getId());
//             dto.setQuestionText(answer.getQuestionText());
//             dto.setOptionText(answer.getOptionText());

//             // Set only MBTI code
//             String mbtiCode = (user.getMbtiType() != null) ? user.getMbtiType().getCode() : null;
//             dto.setMbti(mbtiCode);

//             dtoList.add(dto);
//         }

//         return dtoList;
//     }

//     // Get answers by userId
//     // Get answers by userId with MBTI code
// @GetMapping("/{userId}")
// public List<UserAnswerDTO> getAnswersByUserId(@PathVariable Integer userId) {
//     User user = userRepository.findById(userId)
//             .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

//     List<UserAnswer> answers = userAnswerRepository.findByUserId(userId);
//     List<UserAnswerDTO> dtoList = new ArrayList<>();

//     for (UserAnswer answer : answers) {
//         UserAnswerDTO dto = new UserAnswerDTO();
//         dto.setResponseId(answer.getResponseId());
//         dto.setUserId(user.getId());
//         dto.setQuestionText(answer.getQuestionText());
//         dto.setOptionText(answer.getOptionText());

//         // Set only MBTI code
//         String mbtiCode = (user.getMbtiType() != null) ? user.getMbtiType().getCode() : null;
//         dto.setMbti(mbtiCode);

//         dtoList.add(dto);
//     }

//     return dtoList;
// }

// }

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/useranswer")
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;
    private final MbtiTypeRepository mbtiTypeRepository;
    private final RestTemplate restTemplate;

    private final CareerRecommendationRepository recommendationRepository;

    private final String pythonServiceUrl = "http://localhost:8000/recommend";

    public UserAnswerController(UserAnswerRepository userAnswerRepository,
            UserRepository userRepository,
            MbtiTypeRepository mbtiTypeRepository,
            RestTemplate restTemplate,
            CareerRecommendationRepository recommendationRepository) {
        this.userAnswerRepository = userAnswerRepository;
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
        this.restTemplate = restTemplate;
        this.recommendationRepository = recommendationRepository;
    }

    // Get answers + recommendations by userId
    @GetMapping("/{userId}")
    public Map<String, Object> getAnswersWithRecommendations(@PathVariable Integer userId,
            @RequestParam(defaultValue = "3") int topN) {
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

        // List<Map<String, Object>> recommendations = response;
        // if (recommendations == null) {
        //     recommendations = new ArrayList<>();
        // }
        // // Save to DB
        // Assign userId and save directly
    for (CareerRecommendation rec : recommendations) {
        rec.setUserId(userId);
    }
    recommendationRepository.saveAll(recommendations);

        

        // Return combined response
        Map<String, Object> result = new HashMap<>();
        result.put("userAnswers", answerDTOs);
        result.put("recommendations", recommendations);

        return result;
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
