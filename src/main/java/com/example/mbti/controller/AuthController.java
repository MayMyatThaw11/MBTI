package com.example.mbti.controller;

import com.example.mbti.model.User;
import com.example.mbti.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String passwordHash,
            Model model) {
                System.out.println("Attempting login for email: " + email);
        User user = userRepository.findByEmail(email);
    
        if (user != null && user.getPasswordHash().equals(passwordHash)) {
            System.out.println("Login successful for user: " + user.getEmail());
            model.addAttribute("user", user);
            return "redirect:/questions/start"; 

        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}
