package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
            throw new IllegalArgumentException("Department name already exists");
        }
        try {
            int beds = Integer.parseInt(department.getBedCount());
            if (beds < 0) {
                throw new IllegalArgumentException("Bed count must be >= 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bed count must be a valid number");
        }
        return departmentRepository.save(department);
    }
}
