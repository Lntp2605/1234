package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Admission;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.AdmissionService;
import com.example.hospitalmanagement.service.PatientService;
import com.example.hospitalmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DepartmentService departmentService;

    // ✅ Trang danh sách
    @GetMapping("/admissions")
    public String listAdmissions(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "admissionDate") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir) {

        int pageSize = 10;
        Page<Admission> admissionPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            admissionPage = admissionService.searchAdmissions(keyword.trim(), page, pageSize, sortField, sortDir);
            model.addAttribute("keyword", keyword);
        } else {
            admissionPage = admissionService.getAdmissionsPaginated(page, pageSize, sortField, sortDir);
            model.addAttribute("keyword", "");
        }

        model.addAttribute("admissions", admissionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", admissionPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "layout/admissions/list";
    }

    // ✅ Trang thêm mới (GET)
    @GetMapping("/admissions/add")
    public String showAddAdmissionForm(Model model) {
        Admission admission = new Admission();

        // Khởi tạo để tránh NullPointer khi binding
        admission.setPatient(new Patient());
        admission.setDepartment(new Department());

        model.addAttribute("admission", admission);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "layout/admissions/add";
    }


    // ✅ Xóa nhập viện
    @GetMapping("/admissions/delete/{id}")
    public String deleteAdmission(@PathVariable("id") Long id,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String keyword,
                                  RedirectAttributes redirectAttributes) {

        admissionService.deleteAdmissionById(id);

        redirectAttributes.addAttribute("page", page);
        if (keyword != null && !keyword.trim().isEmpty()) {
            redirectAttributes.addAttribute("keyword", keyword);
        }
        redirectAttributes.addFlashAttribute("message", "Xóa bản ghi thành công!");

        return "redirect:/admissions";
    }

    // ✅ Lưu nhập viện (POST)
    @PostMapping("/admissions/save")
    public String saveAdmission(@RequestParam("patientId") Long patientId,
                                @RequestParam("departmentId") Long departmentId,
                                @RequestParam("admissionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date admissionDate,
                                @RequestParam(value = "dischargeDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dischargeDate,
                                @RequestParam("reasonForAdmission") String reasonForAdmission,
                                RedirectAttributes redirectAttributes) {

        // Lấy entity thật từ DB
        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân với ID: " + patientId));

        Department department = departmentService.getDepartmentById(departmentId);

        if (patient == null || department == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bệnh nhân hoặc khoa!");
            return "redirect:/admissions/add";
        }

        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setDepartment(department);
        admission.setAdmissionDate(admissionDate);
        admission.setDischargeDate(dischargeDate);
        admission.setReasonForAdmission(reasonForAdmission);

        admissionService.saveAdmission(admission);

        redirectAttributes.addFlashAttribute("message", "Thêm nhập viện thành công!");
        return "redirect:/admissions";
    }

    // ✅ Trang sửa nhập viện (GET)
    @GetMapping("/admissions/update/{id}")
    public String showEditAdmissionForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Admission admission = admissionService.getById(id);
        if (admission == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bản ghi nhập viện!");
            return "redirect:/admissions";
        }

        model.addAttribute("admission", admission);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "layout/admissions/update";
    }

    @PostMapping("/admissions/update")
    public String updateAdmission(@RequestParam("admissionId") Long admissionId,
                                  @RequestParam("departmentId") Long departmentId,
                                  @RequestParam("admissionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date admissionDate,
                                  @RequestParam(value = "dischargeDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dischargeDate,
                                  @RequestParam("reasonForAdmission") String reasonForAdmission,
                                  RedirectAttributes redirectAttributes) {

        Admission existing = admissionService.getById(admissionId);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bản ghi nhập viện!");
            return "redirect:/admissions";
        }

        // Gán lại thông tin
        existing.setDepartment(departmentService.getDepartmentById(departmentId));
        existing.setAdmissionDate(admissionDate);
        existing.setDischargeDate(dischargeDate);
        existing.setReasonForAdmission(reasonForAdmission);

        admissionService.saveAdmission(existing);

        redirectAttributes.addFlashAttribute("message", "Cập nhật nhập viện thành công!");
        return "redirect:/admissions";
    }


}