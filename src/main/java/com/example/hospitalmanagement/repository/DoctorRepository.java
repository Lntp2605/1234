package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // Sửa lại phương thức đếm số bác sĩ đang làm việc
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.workSchedule IS NOT NULL AND TRIM(d.workSchedule) != ''")
    long countByWorkScheduleIsNotNullAndNotEmpty();

    @Query("SELECT d FROM Doctor d WHERE " +
            "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:specialty IS NULL OR LOWER(d.specialty) LIKE LOWER(CONCAT('%', :specialty, '%'))) AND " +
            "(:email IS NULL OR LOWER(d.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    Page<Doctor> findByNameContainingIgnoreCaseAndSpecialtyContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String specialty, String email, Pageable pageable);
}