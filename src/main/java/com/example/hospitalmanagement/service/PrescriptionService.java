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

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public void addPrescription(Long examinationId, String medication, String dosage,
                                int amount, double price) throws IllegalArgumentException {
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
        Optional<Examination> examinationOpt = examinationRepository.findById(examinationId);
        if (examinationOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid examination ID.");
        }

        // Tạo mới đơn thuốc
        Prescription prescription = new Prescription();
        prescription.setExamination(examinationOpt.get());
        prescription.setMedication(medication);
        prescription.setDosage(dosage);
        prescription.setAmount(amount);
        prescription.setPrice(price);

        // Lưu vào DB
        prescriptionRepository.save(prescription);
    }
}
