package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
