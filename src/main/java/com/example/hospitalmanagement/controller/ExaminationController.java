package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

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
    @GetMapping("/list")
    public String listExaminations(Model model) {
        List<Examination> examinations = examinationService.getAllExaminations();
        model.addAttribute("examinations", examinations);
        return "examinations/list"; // view HTML hiển thị danh sách
    }
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
