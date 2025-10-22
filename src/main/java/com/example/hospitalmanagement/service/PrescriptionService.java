package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.repository.PrescriptionRepository;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ExaminationRepository examinationRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               ExaminationRepository examinationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.examinationRepository = examinationRepository;
    }

    // Lấy toàn bộ danh sách
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // Thêm đơn thuốc
    public void addPrescription(Long examinationId, String medication, String dosage,
                                int amount, double price) {
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

        Examination examination = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid examination ID."));

        Prescription prescription = new Prescription();
        prescription.setExamination(examination);
        prescription.setMedication(medication);
        prescription.setDosage(dosage);
        prescription.setAmount(amount);
        prescription.setPrice(price);

        prescriptionRepository.save(prescription);
    }

    // Lấy đơn thuốc theo ID
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found."));
    }

    // Cập nhật đơn thuốc
    public void updatePrescription(Long prescriptionId, Long examinationId,
                                   String medication, String dosage,
                                   int amount, double price) {

        Prescription prescription = getPrescriptionById(prescriptionId);

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

        Examination examination = examinationRepository.findById(examinationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid examination ID."));

        prescription.setExamination(examination);
        prescription.setMedication(medication);
        prescription.setDosage(dosage);
        prescription.setAmount(amount);
        prescription.setPrice(price);

        prescriptionRepository.save(prescription);
    }

    // Xóa đơn thuốc
    @Transactional
    public void deletePrescriptionById(Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
        }
    }
}
