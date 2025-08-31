package com.example.mbti.service;


import com.example.mbti.model.User;
import com.example.mbti.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");

        // return org.springframework.security.core.userdetails.User
        //         .withUsername(user.getEmail())
        //         .password(user.getPasswordHash()) // Make sure it's encoded!
        //         .roles("USER")
        //         .build();

         return userRepository.findByEmail(email);
    }
}
