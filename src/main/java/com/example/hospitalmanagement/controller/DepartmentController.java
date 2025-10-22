package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("department") Department department, Model model) {
        try {
            departmentService.addDepartment(department);
            model.addAttribute("message", "Department added successfully");
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "departments/add-department";
    }

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


    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable("id") Long id,
                                   @ModelAttribute("department") Department department,
                                   Model model) {
        try {
            departmentService.updateDepartment(id, department);
            model.addAttribute("message", "Cập nhật khoa thành công!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "departments/edit-department";
    }

}
