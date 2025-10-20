package com.example.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hospitalmanagement.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByCitizenId(String citizenId);
}
