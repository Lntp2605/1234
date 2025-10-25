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

    // Hiển thị danh sách hóa đơn
    @GetMapping("/list")
    public String listInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices/list";
    }

    // Hiển thị form thêm hóa đơn
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "invoices/add";
    }

    // Xử lý thêm hóa đơn
    @PostMapping("/add")
    public String addInvoice(@ModelAttribute("invoice") Invoice invoice, Model model) {
        boolean isAdded = invoiceService.addInvoice(invoice);
        if (!isAdded) {
            model.addAttribute("error", "Invalid data. Please check fields again.");
            return "invoices/add";
        }
        return "redirect:/invoices/list";
    }

    // Hiển thị form chỉnh sửa hóa đơn
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        invoiceService.getInvoiceById(id).ifPresent(invoice -> model.addAttribute("invoice", invoice));
        return "invoices/edit";
    }

    // Xử lý chỉnh sửa hóa đơn
    @PostMapping("/update")
    public String updateInvoice(@ModelAttribute("invoice") Invoice invoice) {
        invoiceService.updateInvoice(invoice);
        return "redirect:/invoices/list";
    }

    // Xóa hóa đơn
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices/list";
    }

    // Tìm kiếm hóa đơn
    @GetMapping("/search")
    public String searchInvoices(@RequestParam("keyword") String keyword, Model model) {
        List<Invoice> results = invoiceService.searchInvoices(keyword);
        model.addAttribute("invoices", results);
        model.addAttribute("keyword", keyword);
        return "invoices/list";
    }
}
