package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Invoice;
import com.example.hospitalmanagement.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Lấy danh sách hóa đơn
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Thêm hóa đơn mới
    public void addInvoice(Invoice invoice) {
        if (invoice.getPatientId() != null && invoice.getAmount() >= 0) {
            invoiceRepository.save(invoice);
        }
    }

    // Chỉnh sửa thông tin hóa đơn
    public void updateInvoice(Invoice updatedInvoice) {
        invoiceRepository.findById(updatedInvoice.getInvoiceId()).ifPresent(existing -> {
            existing.setPatientId(updatedInvoice.getPatientId());
            existing.setAmount(updatedInvoice.getAmount());
            existing.setBillingDate(updatedInvoice.getBillingDate());
            existing.setPaymentStatus(updatedInvoice.getPaymentStatus());
            invoiceRepository.save(existing);
        });
    }

    // Xoá hóa đơn
    public boolean deleteInvoiceById(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tìm kiếm hóa đơn
    public List<Invoice> searchInvoices(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return invoiceRepository.findAll();
        }
        return invoiceRepository.searchInvoices(keyword.trim());
    }
}
