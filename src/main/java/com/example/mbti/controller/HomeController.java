package com.example.mbti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(Model model) {
        model.addAttribute("page", "home");
        return "Home";  // Home.html
    }

    @GetMapping("/MbtiDescription")
    public String MbtiDescription(Model model) {
        model.addAttribute("page", "mbti");
        return "MbtiDescription";  // MBTIdescription.html
    }

    @GetMapping("/feature")
    public String feature(Model model) {
        model.addAttribute("page", "feature");
        return "feature";  // feature.html
    }
}
