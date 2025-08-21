package com.example.mbti.controller;

import com.example.mbti.model.CourseRecommendation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.mbti.repository.CourseRecommendationRepository;
import com.example.mbti.repository.CareerRecommendationRepository;
import com.example.mbti.repository.CareerRepository;


import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/courses")
public class CourseRecommdationController {

    private final CourseRecommendationRepository courseRepository;
    private final CareerRepository careerRepository;
    private final CareerRecommendationRepository recommendationRepository;



    public CourseRecommdationController(CourseRecommendationRepository courseRepository, CareerRepository careerRepository, CareerRecommendationRepository careerRecommandRepository) {
        this.courseRepository = courseRepository;
        this.careerRepository = careerRepository;
        this.recommendationRepository = careerRecommandRepository;
    }

    @GetMapping("final/{careerId}")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    // Show all courses
    @GetMapping
    public String listCourses(Model model) {
        List<CourseRecommendation> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses/list";
    }
    

    // Show form to add a course
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("course", new CourseRecommendation());
        model.addAttribute("careers", careerRepository.findAll());
        return "courses/form";
    }

    // Save new course
    @PostMapping
    public String saveCourse(@ModelAttribute("course") CourseRecommendation course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseRecommendation course = courseRepository.findById(id).orElseThrow();
        model.addAttribute("course", course);
        model.addAttribute("careers", careerRepository.findAll());
        return "courses/form";
    }

    // Delete course
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }

    // Course recommendations by careerId
    @GetMapping("/{careerId}")
    public String getCoursesByCareerId(@PathVariable Integer careerId, Model model) {
        List<CourseRecommendation> courses = courseRepository.findByCareerId(careerId);
        model.addAttribute("courses", courses);
        return "courses/list";  
    }

}
