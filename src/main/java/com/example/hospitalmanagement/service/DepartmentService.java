package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class DepartmentService {
@Autowired
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Hàm lấy danh sách khoa
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public boolean deleteDepartmentById(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Department> searchDepartments(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return departmentRepository.findAll();
        }
        return departmentRepository.searchDepartments(keyword.trim());
    }

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
