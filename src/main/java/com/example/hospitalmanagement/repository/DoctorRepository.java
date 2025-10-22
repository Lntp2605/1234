package com.example.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitalmanagement.model.Doctor;
import org.springframework.data.domain.Sort;
import java.util.List;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.name LIKE %:keyword% OR d.specialty LIKE %:keyword% OR d.email LIKE %:keyword%")
    List <Doctor> findByKeyword(@Param("keyword") String keyword);
    boolean existsByEmail(String email);
    // Tuỳ chọn: mặc định sort theo ID tăng dần
    default List<Doctor> findAllOrderByIdAsc() {
        return findAll(Sort.by(Sort.Direction.ASC, "doctorId"));
    }
}
