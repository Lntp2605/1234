package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    @Query("""
        SELECT e FROM Examination e 
        WHERE (:patientId IS NULL OR e.patient.patientId = :patientId)
        OR (:doctorId IS NULL OR e.doctor.doctorId = :doctorId)
        OR (:diagnosis IS NULL OR LOWER(e.diagnosis) LIKE LOWER(CONCAT('%', :diagnosis, '%')))
        """)
    List<Examination> searchExaminations(
            @Param("patientId") Long patientId,
            @Param("doctorId") Long doctorId,
            @Param("diagnosis") String diagnosis
    );
}
