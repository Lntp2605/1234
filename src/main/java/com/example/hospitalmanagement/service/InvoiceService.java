package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Invoice;
import com.example.hospitalmanagement.repository.InvoiceRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PatientRepository patientRepository) {
        this.invoiceRepository = invoiceRepository;
        this.patientRepository = patientRepository;
    }

    // Lấy danh sách hóa đơn
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Thêm hóa đơn mới
    public boolean addInvoice(Invoice invoice) {
        // Kiểm tra các trường không được để trống
        if (invoice.getPatientId() == null || invoice.getPatientId().trim().isEmpty()
                || invoice.getBillingDate() == null || invoice.getBillingDate().trim().isEmpty()
                || invoice.getPaymentStatus() == null || invoice.getPaymentStatus().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra tổng tiền hợp lệ
        if (invoice.getAmount() < 0) {
            return false;
        }

        // Kiểm tra định dạng ngày
        try {
            LocalDate.parse(invoice.getBillingDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false; // Ngày không hợp lệ
        }

        // Kiểm tra mã bệnh nhân có tồn tại không
        try {
            Long patientIdLong = Long.parseLong(invoice.getPatientId());
            boolean patientExists = patientRepository.existsById(patientIdLong);
            if (!patientExists) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false; // Mã bệnh nhân không hợp lệ
        }

        invoiceRepository.save(invoice);
        return true;
    }

    // Cập nhật hóa đơn
    public boolean updateInvoice(Invoice updatedInvoice) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(updatedInvoice.getInvoiceId());
        if (!optionalInvoice.isPresent()) {
            return false;
        }

        // Kiểm tra các trường không được để trống
        if (updatedInvoice.getPatientId() == null || updatedInvoice.getPatientId().trim().isEmpty()
                || updatedInvoice.getBillingDate() == null || updatedInvoice.getBillingDate().trim().isEmpty()
                || updatedInvoice.getPaymentStatus() == null || updatedInvoice.getPaymentStatus().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra tổng tiền hợp lệ
        if (updatedInvoice.getAmount() < 0) {
            return false;
        }

        // Kiểm tra định dạng ngày
        try {
            LocalDate.parse(updatedInvoice.getBillingDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false; // Ngày không hợp lệ
        }

        // Kiểm tra mã bệnh nhân có tồn tại không
        try {
            Long patientIdLong = Long.parseLong(updatedInvoice.getPatientId());
            boolean patientExists = patientRepository.existsById(patientIdLong);
            if (!patientExists) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false; // Mã bệnh nhân không hợp lệ
        }

        Invoice existing = optionalInvoice.get();
        existing.setPatientId(updatedInvoice.getPatientId());
        existing.setAmount(updatedInvoice.getAmount());
        existing.setBillingDate(updatedInvoice.getBillingDate());
        existing.setPaymentStatus(updatedInvoice.getPaymentStatus());
        invoiceRepository.save(existing);
        return true;
    }

    // Xóa hóa đơn
    public boolean deleteInvoice(Long id) {
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

    // Lấy hóa đơn theo ID
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }
}