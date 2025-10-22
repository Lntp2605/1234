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

    // 🩺 Hiển thị danh sách tất cả đơn thuốc
    @GetMapping
    public String listPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        return "prescriptions"; // -> templates/prescriptions.html
    }

    // ➕ Hiển thị form thêm mới
    @GetMapping("/new")
    public String showAddForm() {
        return "add-prescription"; // form thêm đơn thuốc
    }

    // 💾 Xử lý thêm đơn thuốc mới
    @PostMapping("/add")
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

    // ✏️ Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "edit-prescription";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prescriptions";
        }
    }

    // 🔄 Xử lý cập nhật đơn thuốc
    @PostMapping("/update")
    public String updatePrescription(@RequestParam("prescriptionId") Long prescriptionId,
                                     @RequestParam("examinationId") Long examinationId,
                                     @RequestParam("medication") String medication,
                                     @RequestParam("dosage") String dosage,
                                     @RequestParam("amount") int amount,
                                     @RequestParam("price") double price,
                                     Model model) {
        try {
            prescriptionService.updatePrescription(prescriptionId, examinationId, medication, dosage, amount, price);
            return "redirect:/prescriptions";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("prescription", prescriptionService.getPrescriptionById(prescriptionId));
            return "edit-prescription";
        }
    }
}
