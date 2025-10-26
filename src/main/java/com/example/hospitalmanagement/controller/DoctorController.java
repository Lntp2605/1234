package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors-mgmt")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String email,
            Model model) {
        var doctorPage = doctorService.getDoctors(page, size, sortBy, sortDir, name, specialty, email);
        model.addAttribute("doctors", doctorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", doctorPage.getTotalPages());
        model.addAttribute("totalElements", doctorPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("name", name);
        model.addAttribute("specialty", specialty);
        model.addAttribute("email", email);
        model.addAttribute("totalDoctors", doctorService.getTotalDoctors());
        model.addAttribute("activePage", "doctors");
        return "layout/doctors/list";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "layout/doctors/add";
    }

    @PostMapping("/add")
    public String addDoctor(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result, Model model) {
        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            return "layout/doctors/add";
        }

        try {
            // Kiểm tra trùng email hoặc số điện thoại
            if (doctorService.existsByEmail(doctor.getEmail())) {
                model.addAttribute("errorMessage", "Email đã tồn tại.");
                return "layout/doctors/add";
            }
            if (doctorService.existsByPhoneNumber(doctor.getPhoneNumber())) {
                model.addAttribute("errorMessage", "Số điện thoại đã tồn tại.");
                return "layout/doctors/add";
            }

            // Lưu bác sĩ
            doctorService.saveDoctor(doctor);
            model.addAttribute("successMessage", "Thêm bác sĩ thành công!");
            return "layout/doctors/add"; // Ở lại form để hiển thị thông báo
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi thêm bác sĩ: " + e.getMessage());
            return "layout/doctors/add";
        }
    }


    @GetMapping("/edit/{id}")
    public String showUpdateDoctorForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("doctor", doctorService.getDoctorById(id));
            model.addAttribute("activePage", "doctors");
            return "layout/doctors/edit";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "doctors");
            return "layout/doctors/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(@PathVariable Long id,
                               @Valid @ModelAttribute("doctor") Doctor doctor,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Vui lòng kiểm tra và nhập đầy đủ thông tin hợp lệ.");
            model.addAttribute("activePage", "doctors");
            return "layout/doctors/edit";
        }

        try {
            doctorService.updateDoctor(id, doctor);
            model.addAttribute("successMessage", "Cập nhật bác sĩ thành công!");
            model.addAttribute("activePage", "doctors");
            return "layout/doctors/edit";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "doctors");
            return "layout/doctors/edit";
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        try {
            boolean isDeleted = doctorService.deleteDoctorById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Xóa bác sĩ thành công!");
            } else {
                return ResponseEntity.status(404).body("Không tìm thấy bác sĩ.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Lỗi khi xóa bác sĩ: " + e.getMessage());
        }
    }
}