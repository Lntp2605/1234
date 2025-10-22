package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class
DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor, Model model) {
        try {
            Doctor savedDoctor = doctorService.addDoctor(doctor);
            model.addAttribute("doctor", savedDoctor);
            model.addAttribute("message", "Doctor added successfully");
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "doctors/add-doctor";
    }
    // Thêm mới bác sĩ
    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    // Cập nhật thông tin bác sĩ
    @PutMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    // Xóa bác sĩ
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id, Model model) {
        try {
            doctorService.deleteDoctor(id);
            model.addAttribute("successMessage", "Xóa thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể xóa bác sĩ có ID: " + id);
        }
        return "redirect:/doctors";
    }

    // Tìm kiếm bác sĩ theo từ khóa (tên, chuyên môn, email)
    @GetMapping("/search")
    public String searchDoctors(@RequestParam("keyword") String keyword, Model model) {
        List<Doctor> doctors = doctorService.searchDoctors(keyword);
        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);
        return "doctors/list"; // dùng lại trang danh sách
    }


}
