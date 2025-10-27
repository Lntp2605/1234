
package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Admission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("""
        SELECT a FROM Admission a
        WHERE CAST(a.patient.patientId AS string) LIKE %:keyword%
           OR LOWER(a.department.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(a.reasonForAdmission) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Admission> searchAdmissions(@Param("keyword") String keyword, Pageable pageable);
}
