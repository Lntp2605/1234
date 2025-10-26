package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Examination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    @Query("""
        SELECT e FROM Examination e
        WHERE (:patientId IS NULL OR e.patient.patientId = :patientId)
          AND (:doctorId IS NULL OR e.doctor.doctorId = :doctorId)
          AND (:diagnosis IS NULL OR LOWER(e.diagnosis) LIKE LOWER(CONCAT('%', :diagnosis, '%')))
        """)
    Page<Examination> searchExaminations(@Param("patientId") Long patientId,
                                         @Param("doctorId") Long doctorId,
                                         @Param("diagnosis") String diagnosis,
                                         Pageable pageable);
}