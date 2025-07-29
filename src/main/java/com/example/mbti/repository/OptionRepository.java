package com.example.mbti.repository;

import com.example.mbti.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestionId(Long questionId);
}