package com.example.mbti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mbti.model.MbtiType;
import com.example.mbti.repository.MbtiTypeRepository;

import java.util.List;

@Controller
public class MbtiTypeController {

    private final MbtiTypeRepository mbtiTypeRepository;

    public MbtiTypeController(MbtiTypeRepository mbtiTypeRepository) {
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    @GetMapping("/mbti-types")
    public String getAllMbtiTypes(Model model) {
        List<MbtiType> mbtiTypes = mbtiTypeRepository.findAll();
        model.addAttribute("mbtiTypes", mbtiTypes);
        return "mbti-types";  // Thymeleaf template name
    }
}
