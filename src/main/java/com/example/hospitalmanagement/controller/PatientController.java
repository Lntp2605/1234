package com.example.hospitalmanagement.controller;
import java.util.List;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/patients")
    public String getPatients() {
        return "patients"; // trỏ tới templates/patients.html
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
    @GetMapping("/search")
    public String searchPatients(@RequestParam("keyword") String keyword, Model model) {
        List<Patient> results = patientService.searchPatients(keyword);
        model.addAttribute("patients", results);
        model.addAttribute("keyword", keyword);
        return "patients/list";
    }



    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/add";
    }

    @PostMapping("/add")
    public String addPatient(@Valid @ModelAttribute("patient") Patient patient,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "patients/add";
        }

        try {
            patientService.addPatient(patient);
            model.addAttribute("successMessage", "Thêm bệnh nhân thành công!");
            model.addAttribute("patient", new Patient()); // reset form
            return "patients/add";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "patients/add";
        }
    }
}
