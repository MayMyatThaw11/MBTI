package com.example.mbti.controller;

import com.example.mbti.model.*;
import com.example.mbti.repository.*;
import com.example.mbti.service.MBTIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/questionnaire")
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
    public String submitMbtiAnswers(@RequestParam Map<String, String> answers, Model model) {
        System.out.println("MBTI Answers: " + answers);
        // Store MBTI answers in session
        model.addAttribute("mbtiAnswers", answers);
        // Calculate MBTI type from answers
        String mbtiTypeString = mbtiService.calculateMbti(answers);
        System.out.println("Calculated MBTI Type: " + mbtiTypeString);
        // Store MBTI type in model
        User user = userRepository.findById((int) 1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("User found: " + user.getUsername());
        // Fetch MBTI type from repository
        mbtiTypeRepository.findAll().forEach(mbtiType -> {
            System.out.println("MBTI Type: " + mbtiType.getCode());
        });
        MbtiType mbti = mbtiTypeRepository.findByCode(mbtiTypeString)
                .orElseThrow(() -> new RuntimeException("MBTI type not found: " + mbtiTypeString));
        System.out.println("MBTI Type found: " + mbti.getCode());
        user.setMbtiType(mbti);
        user.setCompletedAt(LocalDateTime.now());
        userRepository.save(user);

        // Redirect to custom questionnaire
        return "redirect:/questionnaire/custom";
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
    public String submitCustomAnswers(@RequestParam Map<String, String> customAnswers,
            @ModelAttribute("mbtiAnswers") Map<String, String> mbtiAnswers,
            Model model, SessionStatus sessionStatus) {
        System.out.println("You are in submit");
        System.out.println("MBTI Answers: " + mbtiAnswers);
        System.out.println("Custom Answers: " + customAnswers);

        User user = userRepository.findById((int) 1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        customAnswers.forEach((key, value) -> {
            try {
                // Extract question number (e.g., "q_1[]" → "1")
                String questionNumber = key.replaceAll("\\D", "");

                if (questionNumber.isEmpty())
                    return;
                System.out.println("Processing answer for question number: " + questionNumber);

                Question question = questionRepository.findById(Integer.parseInt(questionNumber))
                        .orElseThrow(() -> new RuntimeException("Question not found: " + key));

                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setUserId(user.getId());
                userAnswer.setQuestionText(question.getQuestionText());
                userAnswer.setOptionText(value);
                userAnswer.setAnsweredAt(LocalDateTime.now());
                System.out.println("Saving answer for question: " + question.getQuestionText() + ", answer: " + value);

                userAnswerRepository.save(userAnswer);

            } catch (Exception e) {
                System.err.println("Failed to save answer for key: " + key + ", error: " + e.getMessage());
            }
        });

        System.out.println("Custom answers saved successfully for user: " + user.getUsername());
        String mbtiTypeString = user.getMbtiType() != null ? user.getMbtiType().getCode() : "Unknown";
        System.out.println("User MBTI type: " + mbtiTypeString);

        // 4️⃣ Prepare recommended careers
        List<String> recommendedCareers = mbtiService.recommendCareers(mbtiTypeString);
        System.out.println("Recommended careers for MBTI type : " + recommendedCareers);

        // 5️⃣ Add attributes for result page
        model.addAttribute("user", user);
        model.addAttribute("mbtiType", mbtiTypeString);
        model.addAttribute("careerRecommendations", recommendedCareers);
        model.addAttribute("customAnswers", customAnswers);

        // 6️⃣ Clean session
        sessionStatus.setComplete();

        return "result";

    }

}

// Step 4: Submit Custom Questionnaire and show final result
// @PostMapping("/custom/submit")
// public String submitCustomAnswers(@RequestParam Map<String, String>
// customAnswers,
// @ModelAttribute("mbtiAnswers") Map<String, String> mbtiAnswers,
// Model model, SessionStatus sessionStatus) {
// System.out.print("You are in submit");
// // Calculate MBTI type from stored MBTI answers
// System.out.println("MBTI Answers: " + mbtiAnswers);
// System.out.println("Custom Answers: " + customAnswers);

// String mbtiTypeString = mbtiService.calculateMbti(mbtiAnswers);

// // Fetch user (replace with logged-in user logic)
// User user = userRepository.findById(1L)
// .orElseThrow(() -> new RuntimeException("User not found"));

// MbtiType mbti = mbtiTypeRepository.findByName(mbtiTypeString)
// .orElseThrow(() -> new RuntimeException("MBTI type not found: " +
// mbtiTypeString));

// user.setMbtiType(mbti);
// System.out.println("User MBTI type set to: " + mbti);
// userRepository.save(user);

// // Remove session attribute
// sessionStatus.setComplete();

// // Prepare data for final result page
// model.addAttribute("user", user);
// model.addAttribute("mbtiType", mbtiTypeString);
// model.addAttribute("careerRecommendations",
// mbtiService.recommendCareers(mbtiTypeString));
// model.addAttribute("customAnswers", customAnswers);

// return "result"; // final result page
// }
