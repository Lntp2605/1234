package com.example.hospitalmanagement.repository;

import com.example.hospitalmanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // <-- thêm import này
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentName(String departmentName);

    @Query("""
           SELECT d FROM Department d
           WHERE LOWER(d.departmentName)           LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(d.headOfDepartment)         LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(d.location)                 LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(COALESCE(d.description,'')) LIKE LOWER(CONCAT('%', :kw, '%'))
           """)
    List<Department> searchDepartments(@Param("kw") String kw); // <-- buộc phải có @Param
}