package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;
    private final PatientRepository patientRepository;
    private final DoctorRepository  doctorRepository;

    // ===== LIST + filter(patientId/doctorId/diagnosis) + sort(date asc|desc) + page size
    @GetMapping({"", "/list"})
    public String list(@RequestParam(required = false) Long patientId,
                       @RequestParam(required = false) Long doctorId,
                       @RequestParam(required = false) String diagnosis,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "date") String sort,
                       @RequestParam(defaultValue = "desc") String dir,
                       Model model) {

        // ❗ Đừng ghi đè size nữa. Nếu size không hợp lệ, fallback 10
        if (size <= 0) size = 10;

        String diag = (diagnosis == null || diagnosis.isBlank()) ? null : diagnosis.trim();

        Page<Examination> pageData =
                examinationService.searchExaminations(patientId, doctorId, diag, page, size, sort, dir);

        model.addAttribute("examinations", pageData.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);                  // để select "Hiển thị: ..." nhận đúng giá trị
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("totalElements", pageData.getTotalElements());

        // Giữ lại sort/dir hiện tại cho view
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        model.addAttribute("showBreadcrumb", true);
        model.addAttribute("activePage", "examinations");

        return "layout/examinations/list";
    }

    // ===== helper: nạp dropdown
    private void loadDropdowns(Model model) {
        List<Patient> patients = patientRepository.findAll();
        List<Doctor>  doctors  = doctorRepository.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
    }

    // ===== ADD (GET form)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("examination", new Examination());
        model.addAttribute("showBreadcrumb", true);
        model.addAttribute("activePage", "examinations");
        loadDropdowns(model);
        return "layout/examinations/add";
    }

    // ===== ADD (POST submit)
    @PostMapping("/add")
    public String add(@RequestParam Long patientId,
                      @RequestParam Long doctorId,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                      @RequestParam(required = false) String diagnosis,
                      @RequestParam(required = false) Double cost,
                      RedirectAttributes ra) {
        try {
            examinationService.addExamination(patientId, doctorId, date, diagnosis, cost);
            ra.addFlashAttribute("successMessage", "Thêm khám bệnh thành công!");
            return "redirect:/examinations";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/examinations/add";
        }
    }

    // ===== EDIT (GET form)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("examination", examinationService.getExaminationById(id));
            model.addAttribute("showBreadcrumb", true);
            model.addAttribute("activePage", "examinations");
            loadDropdowns(model);
            return "layout/examinations/edit";
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/examinations";
        }
    }

    // ===== UPDATE (POST)
    @PostMapping("/update")
    public String update(@RequestParam Long examinationId,
                         @RequestParam Long patientId,
                         @RequestParam Long doctorId,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                         @RequestParam(required = false) String diagnosis,
                         @RequestParam(required = false) Double cost,
                         RedirectAttributes ra) {
        try {
            examinationService.updateExamination(examinationId, patientId, doctorId, date, diagnosis, cost);
            ra.addFlashAttribute("successMessage", "Cập nhật thành công!");
            return "redirect:/examinations";
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/examinations/edit/" + examinationId;
        }
    }

    // ===== DELETE
    @PostMapping("/delete")
    public String delete(@RequestParam Long examinationId, RedirectAttributes ra) {
        try {
            examinationService.deleteExamination(examinationId);
            ra.addFlashAttribute("successMessage", "Xóa thành công!");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/examinations";
    }
}