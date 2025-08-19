package com.example.mbti.controller;

import com.example.mbti.model.CourseRecommendation;
import com.example.mbti.model.Career;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.mbti.repository.CourseRecommendationRepository;
import com.example.mbti.repository.CareerRepository;


import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseRecommdationController {

    private final CourseRecommendationRepository courseRepo;
    private final CareerRepository careerRepo;

    public CourseRecommdationController(CourseRecommendationRepository courseRepo, CareerRepository careerRepo) {
        this.courseRepo = courseRepo;
        this.careerRepo = careerRepo;
    }

    // Show all courses
    @GetMapping
    public String listCourses(Model model) {
        List<CourseRecommendation> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    // Show form to add a course
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("course", new CourseRecommendation());
        model.addAttribute("careers", careerRepo.findAll());
        return "courses/form";
    }

    // Save new course
    @PostMapping
    public String saveCourse(@ModelAttribute("course") CourseRecommendation course) {
        courseRepo.save(course);
        return "redirect:/courses";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseRecommendation course = courseRepo.findById(id).orElseThrow();
        model.addAttribute("course", course);
        model.addAttribute("careers", careerRepo.findAll());
        return "courses/form";
    }

    // Delete course
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return "redirect:/courses";
    }
}
