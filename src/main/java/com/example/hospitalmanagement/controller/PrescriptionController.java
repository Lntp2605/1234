package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.service.PrescriptionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // Hiển thị danh sách đơn thuốc với phân trang và tìm kiếm
    @GetMapping
    public String listPrescriptions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "medication", required = false) String medication,
            @RequestParam(value = "examinationId", required = false) Long examinationId,
            Model model) {
        Page<Prescription> prescriptionPage = prescriptionService.getAllPrescriptions(page, size, medication, examinationId);
        double totalPrice = prescriptionPage.getContent().stream()
                .mapToDouble(Prescription::getPrice)
                .sum();
        model.addAttribute("prescriptions", prescriptionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", prescriptionPage.getTotalPages());
        model.addAttribute("totalElements", prescriptionPage.getTotalElements());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("medication", medication);
        model.addAttribute("examinationId", examinationId);
        return "layout/prescriptions/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        return "layout/prescriptions/add-prescription";
    }

    // Xử lý thêm đơn thuốc mới
    @PostMapping("/add")
    public String addPrescription(
            @ModelAttribute("prescription") Prescription prescription,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.addPrescription(
                    prescription.getExamination().getExaminationId(),
                    prescription.getMedication(),
                    prescription.getDosage(),
                    prescription.getAmount(),
                    prescription.getPrice()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Thêm đơn thuốc thành công!");
            return "redirect:/prescriptions";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("prescription", prescription);
            return "layout/prescriptions/add-prescription";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "layout/prescriptions/edit-prescription";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "layout/prescriptions/list";
        }
    }

    // Xử lý cập nhật đơn thuốc
    @PostMapping("/update")
    public String updatePrescription(
            @ModelAttribute("prescription") Prescription prescription,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.updatePrescription(
                    prescription.getPrescriptionId(),
                    prescription.getExamination().getExaminationId(),
                    prescription.getMedication(),
                    prescription.getDosage(),
                    prescription.getAmount(),
                    prescription.getPrice()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đơn thuốc thành công!");
            return "redirect:/prescriptions";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("prescription", prescription);
            return "layout/prescriptions/edit-prescription";
        }
    }

    // Xóa đơn thuốc
    @GetMapping("/delete/{id}")
    public String deletePrescription(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "medication", required = false) String medication,
            @RequestParam(value = "examinationId", required = false) Long examinationId,
            RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.deletePrescriptionById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa đơn thuốc thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/prescriptions?page=" + page + "&size=" + size +
                (medication != null ? "&medication=" + medication : "") +
                (examinationId != null ? "&examinationId=" + examinationId : "");
    }
}