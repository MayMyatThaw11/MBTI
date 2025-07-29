package com.example.mbti.controller;

import com.example.mbti.model.Question;
import com.example.mbti.repository.QuestionRepository;
import com.example.mbti.service.MBTIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/questions")


public class QuestionsController {
    @ModelAttribute("oppositeMap")
public Map<String, String> getOppositeMap() {
    return Map.of(
        "E", "I", "I", "E",
        "S", "N", "N", "S",
        "T", "F", "F", "T",
        "J", "P", "P", "J"
    );
}


    private final QuestionRepository questionRepository;
    private final MBTIService mbtiService;

    public QuestionsController(QuestionRepository questionRepository, MBTIService mbtiService) {
        this.questionRepository = questionRepository;
        this.mbtiService = mbtiService;
    }

    @GetMapping("/start")
    public String showQuestions(Model model) {
        System.out.println("Fetching questions from the database...");
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "questionnaire"; // Thymeleaf or JSP view name
    }

    @PostMapping("/submit")
    public String submitAnswers(@RequestParam Map<String, String> answers, Model model) {
        String mbtiType = mbtiService.calculateMbti(answers);
        model.addAttribute("mbtiType", mbtiType);
        model.addAttribute("careerRecommendations", mbtiService.recommendCareers(mbtiType));
        return "result"; // view to display MBTI and career recommendations
    }
}
