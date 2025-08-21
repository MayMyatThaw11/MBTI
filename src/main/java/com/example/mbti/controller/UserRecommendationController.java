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
import com.example.mbti.model.DTO.UserCareerDTO;
import com.example.mbti.repository.CareerRecommendationRepository;
import com.example.mbti.repository.CareerRepository;
import com.example.mbti.repository.CourseRecommendationRepository;

@Controller
@RequestMapping("/recommendations")
public class UserRecommendationController {

    private final CareerRecommendationRepository recRepo;
    private final CareerRepository careerRepo;
    private final CourseRecommendationRepository courseRepo;

    public UserRecommendationController(CareerRecommendationRepository recRepo,
                                        CareerRepository careerRepo,
                                        CourseRecommendationRepository courseRepo) {
        this.recRepo = recRepo;
        this.careerRepo = careerRepo;
        this.courseRepo = courseRepo;
    }

    @GetMapping("/user/{userId}")
    public String getUserRecommendations(@PathVariable Integer userId, Model model) {

        List<CareerRecommendation> recommendations = recRepo.findByUserId(userId);
        List<UserCareerDTO> userCareers = new ArrayList<>();

        for (CareerRecommendation rec : recommendations) {
            careerRepo.findByTitle(rec.getCareer()).ifPresent(career -> {
                // Fetch courses only once per career
                List<CourseRecommendation> courses = courseRepo.findByCareer(career);

                UserCareerDTO dto = new UserCareerDTO();
                dto.setCareer(career);
                dto.setScore(rec.getScore());
                dto.setCourses(courses);

                userCareers.add(dto);
            });
        }
        System.out.println("User careers: " + userCareers + "Coursers: " + userCareers.stream()
                .flatMap(dto -> dto.getCourses().stream())
                .map(CourseRecommendation::getCourseName)
                .toList());

        model.addAttribute("userCareers", userCareers);
        return "user_recommendations";
    }

   

}
