package com.example.mbti.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbti.model.MbtiType;
import com.example.mbti.repository.MbtiTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MbtiTypeController {
    private final MbtiTypeRepository mbtiTypeRepository;

    public MbtiTypeController(MbtiTypeRepository mbtiTypeRepository) {
        this.mbtiTypeRepository = mbtiTypeRepository;
    }
    @GetMapping("/mbti-types")
    public List<MbtiType> getAllMbtiTypes() {
        return mbtiTypeRepository.findAll();
    }

    
}
