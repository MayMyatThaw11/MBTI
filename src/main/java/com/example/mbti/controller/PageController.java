package com.example.mbti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - ITBuddy");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - ITBuddy");
        return "about";
    }

    @GetMapping("/mbti")
    public String mbti(Model model) {
        model.addAttribute("title", "MBTI Description - ITBuddy");
        return "mbtiDescription";
    }
}




    

