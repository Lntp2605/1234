package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Tuỳ chọn: mặc định sort theo ID tăng dần
    default List<Doctor> findAllOrderByIdAsc() {
        return findAll(Sort.by(Sort.Direction.ASC, "doctorId"));
    }
}
