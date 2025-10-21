package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/examinations")
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;

    // 🟢 Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Examination examination = examinationService.getExaminationById(id);
        model.addAttribute("examination", examination);
        return "edit-examination"; // trả về view edit-examination.html
    }

    // 🟢 Xử lý cập nhật thông tin khám bệnh
    @PostMapping("/update")
    public String updateExamination(
            @RequestParam Long examinationId,
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam Date date,
            @RequestParam String diagnosis,
            @RequestParam double cost,
            Model model) {

        try {
            Examination updated = examinationService.updateExamination(
                    examinationId, patientId, doctorId, date, diagnosis, cost);

            model.addAttribute("successMessage", "Cập nhật thông tin khám bệnh thành công!");
            model.addAttribute("examination", updated);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi cập nhật: " + e.getMessage());
        }

        return "edit-examination";
    }
}
