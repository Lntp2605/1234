package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Admission;
import com.example.hospitalmanagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    List<Admission> findByPatient_NameContainingIgnoreCase(String name);
    List<Admission> findByReasonForAdmissionContainingIgnoreCase(String keyword);
}
