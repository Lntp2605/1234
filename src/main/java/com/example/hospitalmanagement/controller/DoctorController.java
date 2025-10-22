package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

}
