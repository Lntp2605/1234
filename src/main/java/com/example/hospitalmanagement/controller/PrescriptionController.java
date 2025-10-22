package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/search")
    public String searchPrescriptions(@RequestParam("keyword") String keyword, Model model) {
        List<Prescription> results = prescriptionService.searchPrescriptions(keyword);
        model.addAttribute("prescriptions", results);
        model.addAttribute("keyword", keyword);
        return "prescriptions/list"; // trả về trang hiển thị kết quả tìm kiếm
    }
}
