package com.example.mbti.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MBTIService {

    public String calculateMbti(Map<String, String> answers) {
        // Initialize scores for each direction
        Map<String, Integer> scores = new HashMap<>();
        String[] types = {"E", "I", "S", "N", "T", "F", "J", "P"};
        for (String type : types) {
            scores.put(type, 0);
        }

        // Process each answer
        for (String key : answers.keySet()) {
            if (key.startsWith("dim_") || key.startsWith("dir_")) continue;

            try {
                int questionId = Integer.parseInt(key);
                int responseValue = Integer.parseInt(answers.get(key)); // 1–5

                String dimension = answers.get("dim_" + questionId); // EI, SN...
                String direction = answers.get("dir_" + questionId); // E, S, etc.

                int delta = responseValue - 3; // Neutral is 3
                if (delta > 0) {
                    // Agreement, supports direction
                    scores.put(direction, scores.get(direction) + delta);
                } else if (delta < 0) {
                    // Disagreement, supports opposite
                    String opposite = getOpposite(direction);
                    scores.put(opposite, scores.get(opposite) + Math.abs(delta));
                }
            } catch (NumberFormatException ex) {
                // Ignore non-question keys
            }
        }

        // Compose MBTI type
        return new StringBuilder()
                .append(scores.get("E") >= scores.get("I") ? "E" : "I")
                .append(scores.get("S") >= scores.get("N") ? "S" : "N")
                .append(scores.get("T") >= scores.get("F") ? "T" : "F")
                .append(scores.get("J") >= scores.get("P") ? "J" : "P")
                .toString();
    }

    private String getOpposite(String dir) {
        return switch (dir) {
            case "E" -> "I";
            case "I" -> "E";
            case "S" -> "N";
            case "N" -> "S";
            case "T" -> "F";
            case "F" -> "T";
            case "J" -> "P";
            case "P" -> "J";
            default -> throw new IllegalArgumentException("Unknown direction: " + dir);
        };
    }

    public List<String> recommendCareers(String mbti) {
        Map<String, List<String>> careerMap = new HashMap<>();
        careerMap.put("INTJ", List.of("Scientist", "Engineer", "Strategist"));
        careerMap.put("ENFP", List.of("Counselor", "Journalist", "Entrepreneur"));
        careerMap.put("ISTJ", List.of("Accountant", "Military Officer", "Lawyer"));
        careerMap.put("ESFP", List.of("Actor", "Event Planner", "Sales Representative"));
        careerMap.put("INFP", List.of("Writer", "Psychologist", "Humanitarian"));
        careerMap.put("ENTP", List.of("Startup Founder", "Marketer", "Consultant"));
        // Add more as needed...

        return careerMap.getOrDefault(mbti, List.of("Career not found"));
    }
}
