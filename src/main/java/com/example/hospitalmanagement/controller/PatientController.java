package com.example.hospitalmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PatientController {

    @GetMapping("/patients")
    public String getPatients() {
        // Sửa "Paitent" -> "Patient"
        return "layout/Patient/List"; 
    }
}
