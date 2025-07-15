package com.example.mbti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";  // index.html
    }

    @GetMapping("/about")
    public String about() {
        return "about";  // about.html
    }

}

    

