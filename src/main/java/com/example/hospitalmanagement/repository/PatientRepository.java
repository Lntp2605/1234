package com.example.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.hospitalmanagement.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.citizenId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Patient> searchPatients(@Param("keyword") String keyword);
    boolean existsByCitizenId(String citizenId);
}
