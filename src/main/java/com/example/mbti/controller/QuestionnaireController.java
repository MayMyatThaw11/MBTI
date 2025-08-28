package com.example.mbti.controller;

import com.example.mbti.model.*;
import com.example.mbti.repository.*;
import com.example.mbti.service.CustomUserDetailsService;
import com.example.mbti.service.MBTIService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/questions")
@SessionAttributes("mbtiAnswers") // store MBTI answers temporarily
public class QuestionnaireController {

    private final MBTIQuestionRepository mbtiQuestionRepository;
    private final MBTIService mbtiService;
    private final UserRepository userRepository;
    private final MbtiTypeRepository mbtiTypeRepository;
    private final QuestionRepository questionRepository;
    private final SectionRepository sectionRepository;
    private final OptionRepository optionRepository;
    private final UserAnswerRepository userAnswerRepository;

    public QuestionnaireController(
            MBTIQuestionRepository mbtiQuestionRepository,
            MBTIService mbtiService,
            UserRepository userRepository,
            MbtiTypeRepository mbtiTypeRepository,
            QuestionRepository questionRepository,
            SectionRepository sectionRepository,
            OptionRepository optionRepository,
            UserAnswerRepository userAnswerRepository

    ) {
        this.mbtiQuestionRepository = mbtiQuestionRepository;
        this.mbtiService = mbtiService;
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
        this.questionRepository = questionRepository;
        this.sectionRepository = sectionRepository;
        this.optionRepository = optionRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    // Map of opposite traits
    @ModelAttribute("oppositeMap")
    public Map<String, String> getOppositeMap() {
        return Map.of(
                "E", "I", "I", "E",
                "S", "N", "N", "S",
                "T", "F", "F", "T",
                "J", "P", "P", "J");
    }

    // Step 1: Show MBTI Questions
    @GetMapping("/mbti")
    public String showMbtiQuestions(Model model) {
        List<MBTIQuestions> questions = mbtiQuestionRepository.findAll();
        if (questions.isEmpty()) {
            model.addAttribute("error", "No MBTI questions available. Please check the database.");
            return "error";
        }
        Collections.shuffle(questions);
        model.addAttribute("questions", questions);
        return "mbti-questions"; // MBTI questions page
    }

    // Step 2: Submit MBTI Answers and go to Custom Questionnaire
    @PostMapping("/mbti/submit")
    public String submitMbtiAnswers(@RequestParam Map<String, String> answers, Model model,HttpSession session) {
        System.out.println("MBTI Answers: " + answers);
        // Store MBTI answers in session
        model.addAttribute("mbtiAnswers", answers);
        // Calculate MBTI type from answers
        String mbtiTypeString = mbtiService.calculateMbti(answers);
        System.out.println("Calculated MBTI Type: " + mbtiTypeString);
        // Store MBTI type in model
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
                throw new RuntimeException("No logged-in user found in session");
        }
        // Fetch MBTI type from repository
        mbtiTypeRepository.findAll().forEach(mbtiType -> {
            System.out.println("MBTI Type: " + mbtiType.getCode());
        });
        MbtiType mbti = mbtiTypeRepository.findByCode(mbtiTypeString)
        .orElseThrow(() -> new RuntimeException("MBTI type not found: " + mbtiTypeString));

        user.setMbtiType(mbti);
        user.setCompletedAt(LocalDateTime.now());
        userRepository.save(user);

        // Redirect to custom questionnaire
        return "redirect:/questions/custom";
    }

    // Step 3: Show Custom Questionnaire
    @GetMapping("/custom")
    public String showCustomQuestions(Model model) {
        List<Section> sections = sectionRepository.findAll();
        List<Question> questions = questionRepository.findAll();
        List<Option> options = optionRepository.findAll();
        List<String> defaultOptions = List.of(
                "Strongly Agree",
                "Agree",
                "Neutral",
                "Disagree",
                "Strongly Disagree");

        model.addAttribute("sections", sections);
        model.addAttribute("questions", questions);
        model.addAttribute("options", options);
        model.addAttribute("defaultOptions", defaultOptions);

        return "iquestions"; // custom questionnaire page
    }

    



@PostMapping("/custom/submit")
public String submitCustomAnswers(
        @RequestParam MultiValueMap<String, String> customAnswersRaw,
        @SessionAttribute(name = "mbtiAnswers", required = false) Map<String, String> mbtiAnswers,
        Model model,
        HttpSession session, SessionStatus sessionStatus) {

    System.out.println("Custom Answers: " + customAnswersRaw);

    // Fetch user (replace with real logged-in user logic)
     User user = (User) session.getAttribute("currentUser");
        if (user == null) {
                throw new RuntimeException("No logged-in user found in session");
        }

    customAnswersRaw.forEach((key, values) -> {
        if (values == null || values.isEmpty()) return;

        // Extract question number from key
        String questionNumber = key.replaceAll("\\D", "");
        if (questionNumber.isEmpty()) return;

        Question question = questionRepository.findById(Integer.parseInt(questionNumber))
                .orElseThrow(() -> new RuntimeException("Question not found: " + key));

        // Save all selected values (checkboxes) or single value (radio)
        for (String val : values) {
            System.out.println("Processing answer for question: " + question.getQuestionText() + ", answer: " + val);
            UserAnswer ua = new UserAnswer();
            ua.setUserId(user.getId());
            ua.setQuestionText(question.getQuestionText());
            ua.setOptionText(val);
            ua.setAnsweredAt(LocalDateTime.now());
            userAnswerRepository.save(ua);
        }
    });

    System.out.println("Custom answers saved successfully for user: " + user.getUsername());

    // MBTI type
    String mbtiTypeString = user.getMbtiType() != null ? user.getMbtiType().getCode() : "Unknown";

    // Clear session
    sessionStatus.setComplete();

    // return "result";
    return "redirect:/useranswer/" + user.getId();
}


    

}