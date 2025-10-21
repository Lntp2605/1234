package com.example.hospitalmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
@Controller
public class PatientController {
    @GetMapping("/patients")
    public String getPatients() {
        return "patients"; // trỏ tới templates/patients.html
    }

}