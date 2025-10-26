package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Tìm kiếm theo tên thuốc (không phân trang)
    @Query("SELECT p FROM Prescription p WHERE LOWER(p.medication) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Prescription> searchPrescriptions(@Param("keyword") String keyword);

    // Tìm kiếm theo tên thuốc với phân trang
    Page<Prescription> findByMedicationContainingIgnoreCase(String medication, Pageable pageable);

    // Tìm kiếm theo mã lượt khám với phân trang
    Page<Prescription> findByExamination_ExaminationId(Long examinationId, Pageable pageable);

    // Tìm kiếm theo cả tên thuốc và mã lượt khám với phân trang
    @Query("SELECT p FROM Prescription p WHERE LOWER(p.medication) LIKE LOWER(CONCAT('%', :medication, '%')) AND p.examination.examinationId = :examinationId")
    Page<Prescription> findByMedicationContainingIgnoreCaseAndExamination_ExaminationId(
            @Param("medication") String medication,
            @Param("examinationId") Long examinationId,
            Pageable pageable);
}