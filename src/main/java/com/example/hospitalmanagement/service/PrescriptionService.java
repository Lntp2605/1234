package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import com.example.hospitalmanagement.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ExaminationRepository examinationRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               ExaminationRepository examinationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.examinationRepository = examinationRepository;
    }

    // ✅ Lấy toàn bộ danh sách
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // ✅ Lấy đơn thuốc theo ID
    public Prescription getPrescriptionById(Long id) {
        Optional<Prescription> opt = prescriptionRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Prescription not found.");
        }
        return opt.get();
    }

    // ✅ Cập nhật đơn thuốc
    public void updatePrescription(Long prescriptionId, Long examinationId,
                                   String medication, String dosage,
                                   int amount, double price) {

        // Kiểm tra đơn thuốc tồn tại
        Prescription prescription = getPrescriptionById(prescriptionId);

        // Validate dữ liệu
        if (medication == null || medication.trim().isEmpty()) {
            throw new IllegalArgumentException("Medication cannot be empty.");
        }
        if (dosage == null || dosage.trim().isEmpty()) {
            throw new IllegalArgumentException("Dosage cannot be empty.");
        }
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be at least 1.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        // Kiểm tra mã khám hợp lệ
        Examination examination = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid examination ID."));

        // Cập nhật dữ liệu
        prescription.setExamination(examination);
        prescription.setMedication(medication);
        prescription.setDosage(dosage);
        prescription.setAmount(amount);
        prescription.setPrice(price);

        // Lưu lại vào DB
        prescriptionRepository.save(prescription);
    }
}
