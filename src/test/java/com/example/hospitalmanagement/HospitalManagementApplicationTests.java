package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HospitalManagementApplicationTests {

    @Autowired
    private DepartmentService departmentService;
    @Test
    void testSearchDepartments() {
        String keyword = "ngoại"; // từ khóa tìm kiếm
        List<Department> results = departmentService.searchDepartments(keyword);

        System.out.println("=== Kết quả tìm kiếm cho từ khóa '" + keyword + "' ===");
        for (Department d : results) {
            System.out.println("ID: " + d.getDepartmentId() + ", Tên: " + d.getDepartmentName() + ", Trưởng khoa: " + d.getHeadOfDepartment());
        }
    }
}
