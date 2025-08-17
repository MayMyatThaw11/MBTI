// package com.example.mbti.controller;

// import com.example.mbti.model.MbtiType;
// import com.example.mbti.model.MBTIQuestions;
// import com.example.mbti.model.User;
// import com.example.mbti.repository.MbtiTypeRepository;
// import com.example.mbti.repository.MBTIQuestionRepository;
// import com.example.mbti.repository.UserRepository;
// import com.example.mbti.service.MBTIService;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.Collections;
// import java.util.List;
// import java.util.Map;

// @Controller
// @RequestMapping("/questions")
// public class MBTIQuestionsController {

//     private final MBTIQuestionRepository questionRepository;
//     private final MBTIService mbtiService;
//     private final UserRepository userRepository;
//     private final MbtiTypeRepository mbtiTypeRepository;

//     public MBTIQuestionsController(
//         MBTIQuestionRepository questionRepository,
//         MBTIService mbtiService,
//         UserRepository userRepository,
//         MbtiTypeRepository mbtiTypeRepository
//     ) {
//         this.questionRepository = questionRepository;
//         this.mbtiService = mbtiService;
//         this.userRepository = userRepository;
//         this.mbtiTypeRepository = mbtiTypeRepository;
//     }

//     @ModelAttribute("oppositeMap")
//     public Map<String, String> getOppositeMap() {
//         return Map.of(
//             "E", "I", "I", "E",
//             "S", "N", "N", "S",
//             "T", "F", "F", "T",
//             "J", "P", "P", "J"
//         );
//     }

//     @GetMapping("/start")
//     public String showQuestions(Model model) {
//         List<MBTIQuestions> questions = questionRepository.findAll();
//         System.out.println(questions.size() + " questions loaded");
//         if (questions.isEmpty()) {
//             model.addAttribute("error", "No questions available. Please check the database.");
//             return "error";
//         }
//         Collections.shuffle(questions);
//         model.addAttribute("questions", questions);
//         return "questionnaire";
//     }

//     @PostMapping("/submit")
//     public String submitAnswers(@RequestParam Map<String, String> answers, Model model) {
//     // Calculate MBTI type from answers
//     String mbtiTypeString = mbtiService.calculateMbti(answers);

//     // Handle case when all answers are neutral or calculation returns "NNNN"
//     if ("NNNN".equals(mbtiTypeString)) {
//         List<MBTIQuestions> questions = questionRepository.findAll();
//         Collections.shuffle(questions);
//          model.addAttribute("questions", questions);
//         model.addAttribute("error", "Insufficient data to determine MBTI type. Please answer more questions.");
//         return "questionnaire"; // Return to questionnaire page for user to try again
//     }

//     model.addAttribute("mbtiType", mbtiTypeString);

//     try {
//         // Fetch user (replace 1L with actual user ID or logic to get current user)
//         User user = userRepository.findById(1L)
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         // Find MBTI type entity by name
//         MbtiType mbti = mbtiTypeRepository.findByName(mbtiTypeString)
//                 .orElseThrow(() -> new RuntimeException("MBTI type not found: " + mbtiTypeString));

//         // Update user's MBTI type and save
//         user.setMbtiType(mbti);
//         userRepository.save(user);

//         model.addAttribute("user", user);

//         // Get career recommendations based on MBTI type
//         model.addAttribute("careerRecommendations", mbtiService.recommendCareers(mbtiTypeString));

//         return "result";  // Show result page

//     } catch (RuntimeException ex) {
//         // Handle errors gracefully
//         model.addAttribute("error", ex.getMessage());
//         return "error"; // Show error page
//     }
// }
//     // @GetMapping("/careers")
//     // public String showCareerRecommendations(@RequestParam String mbtiType, Model model) {
//     //     List<String> careers = mbtiService.recommendCareers(mbtiType);
//     //     model.addAttribute("mbtiType", mbtiType);
//     //     model.addAttribute("careers", careers);
//     //     return "careerRecommendations";
//     // }   
// }
