package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/search")
    public String searchExaminations(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String diagnosis,
            Model model
    ) {
        List<Examination> results = examinationService.searchExaminations(patientId, doctorId, diagnosis);
        model.addAttribute("results", results);
        return "examination-search"; // tên file view
    }
}
