package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable("id") Long id) {
        prescriptionService.deletePrescriptionById(id);
        return "redirect:/prescriptions/list";
    }
}
