package com.example.mbti.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mbti.model.User;
 



public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByEmail(String email); // for login
    //Optional findById(Integer id);
}
