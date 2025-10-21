package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department updateDepartment(Long id, Department updatedDepartment) {
        // 1️⃣ Tìm khoa cần cập nhật
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa cần cập nhật"));

        // 2️⃣ Kiểm tra tên hợp lệ
        if (updatedDepartment.getDepartmentName() == null || updatedDepartment.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khoa không được để trống");
        }

        // 3️⃣ Nếu tên mới khác tên cũ thì kiểm tra trùng
        if (!existing.getDepartmentName().equals(updatedDepartment.getDepartmentName()) &&
                departmentRepository.existsByDepartmentName(updatedDepartment.getDepartmentName())) {
            throw new IllegalArgumentException("Tên khoa đã tồn tại");
        }

        // 4️⃣ Kiểm tra số giường hợp lệ
        try {
            int beds = Integer.parseInt(updatedDepartment.getBedCount());
            if (beds < 0) {
                throw new IllegalArgumentException("Số giường phải >= 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số giường phải là số hợp lệ");
        }

        // 5️⃣ Cập nhật các trường
        existing.setDepartmentName(updatedDepartment.getDepartmentName());
        existing.setDescription(updatedDepartment.getDescription());
        existing.setHeadOfDepartment(updatedDepartment.getHeadOfDepartment());
        existing.setLocation(updatedDepartment.getLocation());
        existing.setBedCount(updatedDepartment.getBedCount());

        // 6️⃣ Lưu vào DB
        return departmentRepository.save(existing);
    }
}
