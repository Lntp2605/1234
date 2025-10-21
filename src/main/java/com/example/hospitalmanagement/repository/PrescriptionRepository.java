package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // JpaRepository đã có sẵn hàm deleteById()
}
