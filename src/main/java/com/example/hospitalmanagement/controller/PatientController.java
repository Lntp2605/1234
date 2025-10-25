package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String citizenID,
            Model model) {
        var patientPage = patientService.searchPatients(name, phone, citizenID, page, size);
        model.addAttribute("patients", patientPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", patientPage.getTotalElements());
        model.addAttribute("totalPages", patientPage.getTotalPages());
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("citizenID", citizenID);
        model.addAttribute("activePage", "patients");
        return "layout/patients/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("activePage", "patients");
        return "layout/patients/add";
    }

    @PostMapping("/add")
    public String addPatient(@Valid @ModelAttribute("patient") Patient patient,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Vui lòng kiểm tra và nhập đầy đủ thông tin hợp lệ.");
            model.addAttribute("activePage", "patients");
            return "layout/patients/add";
        }

        try {
            patientService.addPatient(patient);
            model.addAttribute("successMessage", "Thêm bệnh nhân thành công!");
            model.addAttribute("patient", new Patient());
            model.addAttribute("activePage", "patients");
            return "layout/patients/add";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "patients");
            return "layout/patients/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bệnh nhân không tồn tại."));
        model.addAttribute("patient", patient);
        model.addAttribute("activePage", "patients");
        return "layout/patients/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("patient") Patient patient,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Vui lòng kiểm tra và nhập đầy đủ thông tin hợp lệ.");
            model.addAttribute("activePage", "patients");
            return "layout/patients/edit";
        }

        try {
            patient.setPatientId(id);
            patientService.updatePatient(patient);
            model.addAttribute("successMessage", "Cập nhật bệnh nhân thành công!");
            model.addAttribute("activePage", "patients");
            return "layout/patients/edit";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "patients");
            return "layout/patients/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String citizenID) {
        boolean isDeleted = patientService.deletePatientById(id);
        String redirectUrl = "/patients?page=" + page + "&size=" + size +
                (name != null ? "&name=" + name : "") +
                (phone != null ? "&phone=" + phone : "") +
                (citizenID != null ? "&citizenID=" + citizenID : "");
        if (isDeleted) {
            model.addAttribute("successMessage", "Xóa bệnh nhân thành công!");
            return "redirect:" + redirectUrl;
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy bệnh nhân.");
            return "redirect:/patients/error";
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deletePatientAjax(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isDeleted = patientService.deletePatientById(id);
            if (isDeleted) {
                response.put("success", true);
                response.put("message", "Xóa bệnh nhân thành công!");
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy bệnh nhân.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi xóa bệnh nhân: " + e.getMessage());
        }
        return response;
    }
}