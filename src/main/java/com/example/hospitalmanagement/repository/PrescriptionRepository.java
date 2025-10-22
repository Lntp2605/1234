package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {


    @Query("SELECT p FROM Prescription p " +
            "WHERE LOWER(p.medication) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.dosage) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Prescription> searchPrescriptions(@Param("keyword") String keyword);

}
