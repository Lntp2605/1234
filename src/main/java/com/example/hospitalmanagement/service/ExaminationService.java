package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    /* =========================
       LIST + SEARCH + PAGINATION + SORT
       ========================= */
    public Page<Examination> searchExaminations(Long patientId,
                                                Long doctorId,
                                                String diagnosis,
                                                int page,
                                                int size,
                                                String sortProp,
                                                String dir) {

        // fallback an toàn
        String safeSort = (sortProp == null || sortProp.isBlank()) ? "date" : sortProp;
        Sort.Direction direction = ("desc".equalsIgnoreCase(dir)) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, safeSort));
        return examinationRepository.searchExaminations(patientId, doctorId, blankToNull(diagnosis), pageable);
    }

    /* =========================
       GET ONE
       ========================= */
    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lần khám với ID: " + id));
    }

    /* =========================
       ADD
       ========================= */
    public Examination addExamination(Long patientId,
                                      Long doctorId,
                                      Date date,
                                      String diagnosis,
                                      Double cost) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Mã BN không hợp lệ: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Mã BS không hợp lệ: " + doctorId));

        Examination ex = new Examination();
        ex.setPatient(patient);
        ex.setDoctor(doctor);
        ex.setDate(date);
        ex.setDiagnosis(diagnosis);
        ex.setCost(cost != null ? cost : 0.0);

        return examinationRepository.save(ex);
    }

    /* =========================
       UPDATE
       ========================= */
    public Examination updateExamination(Long examinationId,
                                         Long patientId,
                                         Long doctorId,
                                         Date date,
                                         String diagnosis,
                                         Double cost) {

        Examination existing = getExaminationById(examinationId);

        if (patientId != null) {
            Patient p = patientRepository.findById(patientId)
                    .orElseThrow(() -> new IllegalArgumentException("Mã BN không hợp lệ: " + patientId));
            existing.setPatient(p);
        }

        if (doctorId != null) {
            Doctor d = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new IllegalArgumentException("Mã BS không hợp lệ: " + doctorId));
            existing.setDoctor(d);
        }

        if (date != null)       existing.setDate(date);
        if (diagnosis != null)  existing.setDiagnosis(diagnosis);
        if (cost != null)       existing.setCost(cost);

        return examinationRepository.save(existing);
    }

    /* =========================
       DELETE
       ========================= */
    public void deleteExamination(Long examinationId) {
        Examination existing = getExaminationById(examinationId);
        examinationRepository.delete(existing);
    }

    /* =========================
       Helpers
       ========================= */
    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}