package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @PostMapping("/add")
    public String addExamination(@RequestParam Long patientId,
                                 @RequestParam Long doctorId,
                                 @RequestParam Date date,
                                 @RequestParam String diagnosis,
                                 @RequestParam double cost) {

        examinationService.addExamination(patientId, doctorId, date, diagnosis, cost);
        return "redirect:/examinations"; // chỉ điều hướng, không xử lý logic
    }
}
