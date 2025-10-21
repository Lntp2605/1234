package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/prescriptions")
    public String listPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllPrescriptions());
        return "prescriptions";
    }

    @GetMapping("/prescriptions/new")
    public String showAddForm() {
        return "add-prescription"; // form thêm đơn thuốc
    }

    @PostMapping("/prescriptions")
    public String addPrescription(@RequestParam("examinationId") Long examinationId,
                                  @RequestParam("medication") String medication,
                                  @RequestParam("dosage") String dosage,
                                  @RequestParam("amount") int amount,
                                  @RequestParam("price") double price,
                                  Model model) {
        try {
            prescriptionService.addPrescription(examinationId, medication, dosage, amount, price);
            return "redirect:/prescriptions"; // sau khi thêm thành công quay lại danh sách
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-prescription"; // hiển thị lại form kèm lỗi
        }
    }
}
