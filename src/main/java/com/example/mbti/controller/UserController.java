package com.example.mbti.controller;

import com.example.mbti.model.User;
import com.example.mbti.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registration handler
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
public String registerUser(User user, Model model) {
    if (userRepository.findByEmail(user.getEmail()) != null) {
        model.addAttribute("error", "Email is already registered.");
        return "register";
    }

   
    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
    userRepository.save(user);
    model.addAttribute("success", "Registration successful! Please log in.");
    return "redirect:/auth/login";
}


    // Login handler
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String passwordHash,
            Model model,
            HttpSession session) {


        User user = userRepository.findByEmail(email);
        System.out.println("User found: " + (user != null ? user.getEmail() : "null"));
        System.out.println("Raw entered password: " + passwordHash);
        System.out.println("Stored password hash: " + user.getPasswordHash());
        System.out.println("Password matches? " + passwordEncoder.matches(passwordHash, user.getPasswordHash()));


        if (user != null && passwordEncoder.matches(passwordHash, user.getPasswordHash())) {
        session.setAttribute("currentUser", user);

        // ✅ Manually authenticate user in Spring Security
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                        
                );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/questions/mbti";
    }
        System.out.println("Login failed for email: " + email);
        System.out.println("Provided password: " + passwordHash);

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    @GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    SecurityContextHolder.clearContext();
    return "redirect:/";
}

}
