package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        patientService.getPatientById(id).ifPresent(patient -> model.addAttribute("patient", patient));
        return "edit-patient"; // Trả về view edit-patient.html
    }

    // Xử lý form chỉnh sửa
    @PostMapping("/update")
    public String updatePatient(@ModelAttribute("patient") Patient patient) {
        patientService.updatePatient(patient);
        return "redirect:/patients"; // Sau khi sửa xong quay về danh sách bệnh nhân
    }
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        boolean isDeleted = patientService.deletePatientById(id);
        if (isDeleted) {
            return "redirect:/patients/list"; // trang danh sách bệnh nhân
        } else {
            return "redirect:/patients/error"; // hoặc trang báo lỗi nếu không tìm thấy
        }
    }

}
