package com.example.mbti.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MBTIService {

    public String calculateMbti(Map<String, String> answers) {
        Map<String, Integer> counts = new HashMap<>();
        String[] dimensions = {"E", "I", "S", "N", "T", "F", "J", "P"};

        for (String dim : dimensions) {
            counts.put(dim, 0);
        }

        for (String key : answers.keySet()) {
            String direction = answers.get(key);
            counts.put(direction, counts.get(direction) + 1);
        }

        // Build MBTI string
        StringBuilder mbti = new StringBuilder();
        mbti.append(counts.get("E") >= counts.get("I") ? "E" : "I");
        mbti.append(counts.get("S") >= counts.get("N") ? "S" : "N");
        mbti.append(counts.get("T") >= counts.get("F") ? "T" : "F");
        mbti.append(counts.get("J") >= counts.get("P") ? "J" : "P");

        return mbti.toString();
    }

    public List<String> recommendCareers(String mbti) {
        // Hardcoded example (replace with database or config in real app)
        Map<String, List<String>> careerMap = new HashMap<>();
        careerMap.put("INTJ", List.of("Scientist", "Engineer", "Strategist"));
        careerMap.put("ENFP", List.of("Counselor", "Journalist", "Entrepreneur"));
        careerMap.put("ISTJ", List.of("Accountant", "Military Officer", "Lawyer"));
        careerMap.put("ESFP", List.of("Actor", "Event Planner", "Sales Representative"));
        // Add more...

        return careerMap.getOrDefault(mbti, List.of("Career not found"));
    }
}
