package com.example.mbti.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class CareerRecommendationService {

    private final String pythonServiceUrl = "http://localhost:8000/recommend";

    private final RestTemplate restTemplate;

    public CareerRecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

  public List<CareerRecommendation> getCareerRecommendations(List<Map<String, Object>> userAnswers, int topN) {
    System.out.println("Sending user answers to Python service: " + userAnswers);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(userAnswers, headers);

    ResponseEntity<CareerRecommendation[]> response = restTemplate.exchange(
            pythonServiceUrl + "?top_n=" + topN,
            HttpMethod.POST,
            request,
            CareerRecommendation[].class
    );

    CareerRecommendation[] recommendations = response.getBody();
    System.out.println("Response from Python service: " + response);

    return Arrays.asList(recommendations);
}

    public static class CareerRecommendation {
        private String careerName;
        private String description;
        private double score;

        // Getters and Setters
        public String getCareerName() {
            return careerName;
        }

        public void setCareerName(String careerName) {
            this.careerName = careerName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}

