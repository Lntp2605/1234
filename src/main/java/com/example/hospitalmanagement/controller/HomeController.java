package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PatientService patientService;
    @GetMapping("/")
    public String home(Model model) {
        // Lấy toàn bộ danh sách bệnh nhân
        List<Patient> patients = patientService.getAllPatients();

        // Lấy 5 bệnh nhân đầu tiên nếu danh sách dài hơn
        if (patients.size() > 5) {
            patients = patients.subList(0, 5);
        }

        model.addAttribute("patients", patients);
        return "index"; // trỏ đến file index.html
    }

}

