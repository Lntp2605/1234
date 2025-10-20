package com.example.hospitalmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitalmanagement.model.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
