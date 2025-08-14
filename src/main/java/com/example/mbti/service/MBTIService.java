package com.example.mbti.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MBTIService {

    public String calculateMbti(Map<String, String> answers) {
        Map<String, Integer> scores = new HashMap<>();
        String[] types = {"E", "I", "S", "N", "T", "F", "J", "P"};
        for (String type : types) {
            scores.put(type, 0);
        }

        for (String key : answers.keySet()) {
            if (key.startsWith("dim_") || key.startsWith("dir_")) continue;

            try {
                int questionId = Integer.parseInt(key);
                int responseValue = Integer.parseInt(answers.get(key)); // 1–5

                String dimension = answers.get("dim_" + questionId); // EI, SN...
                String direction = answers.get("dir_" + questionId); // E, S, etc.

                int delta = responseValue - 3; // Neutral is 3
                if (delta > 0) {
                    scores.put(direction, scores.get(direction) + delta);
                } else if (delta < 0) {
                    String opposite = getOpposite(direction);
                    scores.put(opposite, scores.get(opposite) + Math.abs(delta));
                }
                // delta == 0: no change
            } catch (NumberFormatException ex) {
                // Ignore non-question keys
            }
        }

        // Check for ties (all zero scores)
        boolean allZero = true;
        for (String t : types) {
            if (scores.get(t) != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            return "NNNN"; // Neutral for all dimensions — or return null or special string
        }

        // Build MBTI string with tie-breakers (if tie, pick first direction)
        String ei = tieBreaker(scores, "E", "I");
        String sn = tieBreaker(scores, "S", "N");
        String tf = tieBreaker(scores, "T", "F");
        String jp = tieBreaker(scores, "J", "P");

        return ei + sn + tf + jp;
    }

    private String tieBreaker(Map<String, Integer> scores, String dir1, String dir2) {
        int score1 = scores.get(dir1);
        int score2 = scores.get(dir2);

        // If tie, pick dir1 by default
        if (score1 >= score2) {
            return dir1;
        } else {
            return dir2;
        }
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
        careerMap.put("INTJ", List.of("Scientist", "Engineer", "Strategist", "Architect", "Researcher"));
        careerMap.put("INTP", List.of("Philosopher", "Scientist", "Software Developer", "Inventor", "Engineer"));
        careerMap.put("ENTJ", List.of("Executive", "Project Manager", "Business Leader", "Entrepreneur", "Strategist"));
        careerMap.put("ENTP", List.of("Startup Founder", "Marketer", "Consultant", "Inventor", "Salesperson"));
        careerMap.put("INFJ", List.of("Counselor", "Psychologist", "Writer", "Teacher", "Advisor"));
        careerMap.put("INFP", List.of("Writer", "Psychologist", "Humanitarian", "Artist", "Social Worker"));
        careerMap.put("ENFJ", List.of("Teacher", "Counselor", "Sales Manager", "HR Specialist", "Motivational Speaker"));
        careerMap.put("ENFP", List.of("Counselor", "Journalist", "Entrepreneur", "Actor", "Creative Director"));
        careerMap.put("ISTJ", List.of("Accountant", "Military Officer", "Lawyer", "Auditor", "Police Officer"));
        careerMap.put("ISFJ", List.of("Nurse", "Teacher", "Librarian", "Social Worker", "Administrator"));
        careerMap.put("ESTJ", List.of("Manager", "Administrator", "Judge", "Military Leader", "Sales Manager"));
        careerMap.put("ESFJ", List.of("Teacher", "Nurse", "Counselor", "Event Planner", "Social Worker"));
        careerMap.put("ISTP", List.of("Mechanic", "Engineer", "Pilot", "Detective", "Technician"));
        careerMap.put("ISFP", List.of("Artist", "Designer", "Chef", "Musician", "Counselor"));
        careerMap.put("ESTP", List.of("Sales Representative", "Entrepreneur", "Athlete", "Paramedic", "Marketer"));
        careerMap.put("ESFP", List.of("Actor", "Event Planner", "Sales Representative", "Entertainer", "Public Relations"));

        return careerMap.getOrDefault(mbti, List.of("Career not found"));
    }
}
