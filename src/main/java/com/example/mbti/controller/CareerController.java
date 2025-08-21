package com.example.mbti.controller;

import org.springframework.web.bind.annotation.*;

import com.example.mbti.service.CareerRecommendationService;



@RestController
@RequestMapping("/api")
public class CareerController {

    private final CareerRecommendationService recommendationService;

    public CareerController(CareerRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // @PostMapping("/recommendation")
    // public List<Map<String, Object>> getRecommendations(@RequestBody List<Map<String, Object>> userAnswers,
    //                                                     @RequestParam(defaultValue = "3") int topN) {
    //     return recommendationService.getCareerRecommendations(userAnswers, topN);
    // }
}
