package com.example.mbti.controller;

import com.example.mbti.model.MbtiType;
import com.example.mbti.model.User;
import com.example.mbti.repository.UserRepository;
import com.example.mbti.repository.MbtiTypeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    public UserController(UserRepository userRepository, MbtiTypeRepository mbtiTypeRepository) {
        this.userRepository = userRepository;
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    //  List all users
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list"; // -> resources/templates/user-list.html
    }

    //  Show form to create a new user
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("mbtiTypes", mbtiTypeRepository.findAll());
        return "user-form"; // -> resources/templates/user-form.html
    }

    //  Save new user
    @PostMapping("/save")
public String saveUser(
    
        @ModelAttribute User user,
        @RequestParam("mbtiTypeId") Long mbtiTypeId) {
           
    MbtiType mbtiType = mbtiTypeRepository.findById(mbtiTypeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid MBTI Type ID"));
    user.setMbtiType(mbtiType);
    userRepository.save(user);
    return "redirect:/users";
}


    //  Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
