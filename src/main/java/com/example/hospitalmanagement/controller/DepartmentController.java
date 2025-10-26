package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Department;
import com.example.hospitalmanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    /* =========================
       LIST – hỗ trợ ?action=add|edit để tương thích link cũ
       ========================= */
    @GetMapping
    public String list(@RequestParam(required = false) String action,
                       @RequestParam(required = false) Long id,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "asc") String sort,
                       Model model) {

        if ("add".equalsIgnoreCase(action)) return "redirect:/departments/add";
        if ("edit".equalsIgnoreCase(action) && id != null) return "redirect:/departments/edit/" + id;

        Page<Department> pageData = departmentService.searchDepartments(keyword, page, size, sort);
        List<Department> items = pageData.getContent();
        if (items == null) items = Collections.emptyList();

        model.addAttribute("departments", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("totalElements", pageData.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        // KPI
        model.addAttribute("totalBeds", departmentService.sumAllBeds());
        model.addAttribute("activeBedDepartments", departmentService.countActiveBedDepartments());

        // UI flags
        model.addAttribute("showBreadcrumb", true);
        model.addAttribute("activePage", "departments");

        // JSON nhúng nếu JS cần
        model.addAttribute("allDepartmentsJson", "[]");

        return "layout/departments/list";
    }

    /* =========================
       ADD (GET form + POST submit)
       ========================= */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("showBreadcrumb", true);
        model.addAttribute("activePage", "departments");
        return "layout/departments/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Department department, RedirectAttributes ra) {
        try {
            departmentService.addDepartment(department);
            ra.addFlashAttribute("successMessage", "Thêm khoa thành công");
            return "redirect:/departments";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/departments/add";
        }
    }

    /* =========================
       EDIT (GET form + POST submit)
       ========================= */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Department d = departmentService.getDepartmentById(id);
            model.addAttribute("department", d);
            model.addAttribute("showBreadcrumb", true);
            model.addAttribute("activePage", "departments");
            return "layout/departments/edit";
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/departments";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Department department, RedirectAttributes ra) {
        try {
            departmentService.updateDepartment(department.getDepartmentId(), department);
            ra.addFlashAttribute("successMessage", "Cập nhật khoa thành công");
            return "redirect:/departments";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/departments/edit/" + department.getDepartmentId();
        }
    }

    /* =========================
       DELETE – hỗ trợ cả POST /delete và POST /delete/{id}
       ========================= */
    @PostMapping("/delete")
    public String deleteByForm(@RequestParam Long id, RedirectAttributes ra) {
        return doDelete(id, ra);
    }

    @PostMapping("/delete/{id}")
    public String deleteByPath(@PathVariable Long id, RedirectAttributes ra) {
        return doDelete(id, ra);
    }

    private String doDelete(Long id, RedirectAttributes ra) {
        if (departmentService.deleteDepartmentById(id)) {
            ra.addFlashAttribute("successMessage", "Đã xóa khoa");
        } else {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy khoa để xóa");
        }
        return "redirect:/departments";
    }
}