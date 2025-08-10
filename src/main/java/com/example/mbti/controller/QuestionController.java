package com.example.mbti.controller;

import com.example.mbti.model.Option;
import com.example.mbti.model.Question;
import com.example.mbti.model.Section;
import com.example.mbti.repository.OptionRepository;
import com.example.mbti.repository.QuestionRepository;
import com.example.mbti.repository.SectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final SectionRepository sectionRepository;
    private final OptionRepository optionRepository;

    public QuestionController(QuestionRepository questionRepository,
                               SectionRepository sectionRepository,
                               OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.sectionRepository = sectionRepository;
        this.optionRepository = optionRepository;
    }

    @GetMapping("/iquestions")
    public String getAllQuestions(Model model) {
        List<Section> sections = sectionRepository.findAll();
        List<Question> questions = questionRepository.findAll();
        List<Option> options = optionRepository.findAll();
        List<String> defaultOptions = List.of(
            "Strongly Agree", 
            "Agree", 
            "Neutral", 
            "Disagree", 
            "Strongly Disagree"
    );

        model.addAttribute("sections", sections);
        model.addAttribute("questions", questions);
        model.addAttribute("options", options);
        model.addAttribute("defaultOptions", defaultOptions);

        return "iquestions";
    }
}
