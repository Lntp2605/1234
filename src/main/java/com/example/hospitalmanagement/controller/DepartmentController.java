package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.ui.Model;


@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*") // Cho phép FE gọi API
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Lấy danh sách khoa
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        boolean isDeleted = departmentService.deleteDepartmentById(id);
        if (isDeleted) {
            return "redirect:/departments/list"; // Sau khi xoá, quay lại danh sách
        } else {
            return "redirect:/departments/error"; // Nếu không tìm thấy
        }
    }
    @GetMapping("/search")
    public String searchDepartments(@RequestParam("keyword") String keyword, Model model) {
        List<Department> results = departmentService.searchDepartments(keyword);
        model.addAttribute("departments", results);
        model.addAttribute("keyword", keyword);
        return "departments/list"; // Trang hiển thị danh sách kết quả
    }

}
