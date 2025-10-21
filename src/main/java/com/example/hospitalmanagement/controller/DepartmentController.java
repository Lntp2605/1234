package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
}
