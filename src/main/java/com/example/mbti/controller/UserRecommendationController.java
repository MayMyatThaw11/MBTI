package com.example.mbti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mbti.model.Career;
import com.example.mbti.model.CareerRecommendation;
import com.example.mbti.model.CourseRecommendation;
import com.example.mbti.model.MbtiType;
import com.example.mbti.model.User;
import com.example.mbti.model.DTO.UserCareerDTO;
import com.example.mbti.repository.CareerRecommendationRepository;
import com.example.mbti.repository.CareerRepository;
import com.example.mbti.repository.CourseRecommendationRepository;
import com.example.mbti.repository.MbtiTypeRepository;
import com.example.mbti.repository.UserRepository;

@Controller
@RequestMapping("/recommendations")
public class UserRecommendationController {
    private final CareerRepository careerRepository;
    private final CareerRecommendationRepository careerRecomRepository;
    private final CourseRecommendationRepository courseRepository;
    private final MbtiTypeRepository mbtiRepository;
    private final UserRepository userRepository;

    public UserRecommendationController(CareerRecommendationRepository careerRecomRepository,
                                        CareerRepository careerRepository,
                                        CourseRecommendationRepository courseRepository,
                                        MbtiTypeRepository mbtiRepository,
                                        UserRepository userRepository) {
        this.careerRecomRepository = careerRecomRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
        this.mbtiRepository = mbtiRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}")
    public String getUserRecommendations(@PathVariable Integer userId, Model model) {
        //Get User from Database if needed
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "error"; // error.html
        }
        MbtiType mbtiType = (user != null) ? user.getMbtiType() : null;
        //1. Fetch career recommendations for the user
        List<CareerRecommendation> recommendations = careerRecomRepository.findByUserId(userId);
        List<UserCareerDTO> userCareers = new ArrayList<>();

        for (CareerRecommendation rec : recommendations) {
            careerRepository.findByTitle(rec.getCareer()).ifPresent(career -> {
                // Fetch courses only once per career
                List<CourseRecommendation> courses = courseRepository.findByCareer(career);

                UserCareerDTO dto = new UserCareerDTO();
                dto.setCareer(career);
                dto.setScore(rec.getScore());
                dto.setCourses(courses);
                dto.setMbtiType(mbtiType);
                userCareers.add(dto);
            });
        }
        System.out.println("User careers: " + userCareers + "Coursers: " + userCareers.stream()
                .flatMap(dto -> dto.getCourses().stream())
                .map(CourseRecommendation::getCourseName)
                .toList());

        model.addAttribute("userCareers", userCareers);
        model.addAttribute("user", user);
model.addAttribute("mbtiType", mbtiType);        
        return "user_recommendations";
    }

   

}
