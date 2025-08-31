package com.example.mbti.model;

import java.util.List;

public class RecommendationResponse {
    private List<CareerRecommendation> recommendations;

    public List<CareerRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<CareerRecommendation> recommendations) {
        this.recommendations = recommendations;
    }
}

