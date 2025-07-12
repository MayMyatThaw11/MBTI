package com.example.mbti.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mbti.model.MbtiType;

public interface MbtiTypeRepository extends JpaRepository<MbtiType, Long> {
}
