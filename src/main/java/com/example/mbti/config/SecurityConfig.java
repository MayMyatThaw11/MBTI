package com.example.mbti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/MbtiDescription", "/feature", "/about").permitAll() // ✅ Public routes
                        .requestMatchers("/auth/**").permitAll() // ✅ Login/Register
                        .requestMatchers("/questions/mbti","questions/custom" ).authenticated() // 🔐 Protected route
                        .anyRequest().permitAll())
                // .formLogin(form -> form
                //         .loginPage("/auth/login")
                //          .loginProcessingUrl("/auth/login")
                //         .defaultSuccessUrl("/questions/mbti", true)
                //         .permitAll())
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
