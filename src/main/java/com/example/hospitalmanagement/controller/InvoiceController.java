package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.model.Invoice;
import com.example.hospitalmanagement.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Lấy danh sách hóa đơn
    @GetMapping
    public String listInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoice/list";
    }

    // Thêm hóa đơn mới
    @PostMapping("/add")
    public String addInvoice(@ModelAttribute("invoice") Invoice invoice) {
        invoiceService.addInvoice(invoice);
        return "redirect:/invoices";
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        invoiceService.getAllInvoices().stream()
                .filter(i -> i.getInvoiceId().equals(id))
                .findFirst()
                .ifPresent(i -> model.addAttribute("invoice", i));
        return "invoice/edit";
    }

    // Cập nhật thông tin hóa đơn
    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute("invoice") Invoice invoice) {
        invoiceService.updateInvoice(invoice);
        return "redirect:/invoices";
    }

    // Xóa hóa đơn
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoiceById(id);
        return "redirect:/invoices";
    }

    // Tìm kiếm hóa đơn
    @GetMapping("/search")
    public String searchInvoices(@RequestParam("keyword") String keyword, Model model) {
        List<Invoice> results = invoiceService.searchInvoices(keyword);
        model.addAttribute("invoices", results);
        model.addAttribute("keyword", keyword);
        return "invoice/list";
    }
}
