package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Admission;
import com.example.hospitalmanagement.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    // Trang hiển thị danh sách nhập viện
    @GetMapping
    public String listAdmissions(Model model, @RequestParam(required = false) String keyword) {
        List<Admission> admissions;
        if (keyword != null && !keyword.trim().isEmpty()) {
            admissions = admissionService.searchAdmissions(keyword);
        } else {
            admissions = admissionService.getAllAdmissions();
        }
        model.addAttribute("admissions", admissions);
        model.addAttribute("keyword", keyword);
        return "admissions/list"; // Trỏ tới file templates/admissions/list.html
    }

    // Trang thêm nhập viện
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("admission", new Admission());
        return "admissions/add";
    }

    @PostMapping("/add")
    public String addAdmission(@ModelAttribute Admission admission, Model model) {
        try {
            admissionService.addAdmission(admission);
            return "redirect:/admissions";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admissions/add";
        }
    }

    //Trang chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Admission admission = admissionService.getAdmissionById(id);
            model.addAttribute("admission", admission);
            return "admissions/edit";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/admissions";
        }
    }

    @PostMapping("/edit/{id}")
    public String editAdmission(@PathVariable Long id, @ModelAttribute Admission admission, Model model) {
        try {
            admissionService.updateAdmission(id, admission);
            return "redirect:/admissions";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admissions/edit";
        }
    }

    // Xóa nhập viện
    @GetMapping("/delete/{id}")
    public String deleteAdmission(@PathVariable Long id) {
        admissionService.deleteAdmission(id);
        return "redirect:/admissions";
    }
}
