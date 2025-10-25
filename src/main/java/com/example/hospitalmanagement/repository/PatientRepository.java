package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByCitizenId(String citizenId);

    @Query("SELECT p FROM Patient p WHERE " +
            "(:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:phone IS NULL OR :phone = '' OR LOWER(p.phoneNumber) LIKE LOWER(CONCAT('%', :phone, '%'))) AND " +
            "(:citizenID IS NULL OR :citizenID = '' OR LOWER(p.citizenId) LIKE LOWER(CONCAT('%', :citizenID, '%')))")
    Page<Patient> searchPatients(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("citizenID") String citizenID,
            Pageable pageable);
}