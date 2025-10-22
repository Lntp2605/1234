package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
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

    public Department updateDepartment(Long id, Department updatedDepartment) {
        // 1Tìm khoa cần cập nhật
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa cần cập nhật"));

        // 2Kiểm tra tên hợp lệ
        if (updatedDepartment.getDepartmentName() == null || updatedDepartment.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khoa không được để trống");
        }

        // 3Nếu tên mới khác tên cũ thì kiểm tra trùng
        if (!existing.getDepartmentName().equals(updatedDepartment.getDepartmentName()) &&
                departmentRepository.existsByDepartmentName(updatedDepartment.getDepartmentName())) {
            throw new IllegalArgumentException("Tên khoa đã tồn tại");
        }

        // 4Kiểm tra số giường hợp lệ
        try {
            int beds = Integer.parseInt(updatedDepartment.getBedCount());
            if (beds < 0) {
                throw new IllegalArgumentException("Số giường phải >= 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số giường phải là số hợp lệ");
        }

        // 5Cập nhật các trường
        existing.setDepartmentName(updatedDepartment.getDepartmentName());
        existing.setDescription(updatedDepartment.getDescription());
        existing.setHeadOfDepartment(updatedDepartment.getHeadOfDepartment());
        existing.setLocation(updatedDepartment.getLocation());
        existing.setBedCount(updatedDepartment.getBedCount());

        // 6Lưu vào DB
        return departmentRepository.save(existing);
    }
}
