package com.example.hospitalmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitalmanagement.model.Admission;
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
