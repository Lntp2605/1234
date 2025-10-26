package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Invoice;
import com.example.hospitalmanagement.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Hiển thị danh sách hóa đơn
    @GetMapping("/list")
    public String listInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "layout/invoices/list";
    }

    // Hiển thị form thêm hóa đơn
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "layout/invoices/add";
    }

    // Xử lý thêm hóa đơn
    @PostMapping("/add")
    public String addInvoice(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "layout/invoices/add";
        }
        boolean isAdded = invoiceService.addInvoice(invoice);
        if (!isAdded) {
            model.addAttribute("error", "Không thể thêm hóa đơn. Vui lòng kiểm tra mã bệnh nhân, số tiền hoặc ngày lập hóa đơn.");
            return "layout/invoices/add";
        }
        return "redirect:/invoices/list";
    }

    // Hiển thị form chỉnh sửa hóa đơn
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        return invoiceService.getInvoiceById(id)
                .map(invoice -> {
                    model.addAttribute("invoice", invoice);
                    return "layout/invoices/edit";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Hóa đơn không tồn tại.");
                    return "redirect:/invoices/list";
                });
    }

    // Xử lý chỉnh sửa hóa đơn
    @PostMapping("/update")
    public String updateInvoice(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "layout/invoices/edit";
        }
        boolean isUpdated = invoiceService.updateInvoice(invoice);
        if (!isUpdated) {
            model.addAttribute("error", "Không thể cập nhật hóa đơn. Vui lòng kiểm tra lại.");
            return "layout/invoices/edit";
        }
        return "redirect:/invoices/list";
    }

    // Xóa hóa đơn
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id, Model model) {
        boolean isDeleted = invoiceService.deleteInvoice(id);
        if (!isDeleted) {
            model.addAttribute("error", "Không thể xóa hóa đơn. Hóa đơn không tồn tại.");
        }
        return "redirect:/invoices/list";
    }

    // Tìm kiếm hóa đơn
    @GetMapping("/search")
    public String searchInvoices(@RequestParam("keyword") String keyword, Model model) {
        List<Invoice> results = invoiceService.searchInvoices(keyword);
        model.addAttribute("invoices", results);
        model.addAttribute("keyword", keyword);
        return "layout/invoices/list";
    }
}