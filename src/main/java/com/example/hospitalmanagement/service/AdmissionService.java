package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Admission;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.AdmissionRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Lấy danh sách tất cả nhập viện
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    // Thêm mới nhập viện
    public Admission addAdmission(Admission admission) throws Exception {
        if (admission.getPatient() == null || admission.getPatient().getPatientId() == null) {
            throw new Exception("Patient ID cannot be null");
        }

        Optional<Patient> patientOpt = patientRepository.findById(admission.getPatient().getPatientId());
        if (patientOpt.isEmpty()) {
            throw new Exception("Invalid Patient ID");
        }

        if (admission.getAdmissionDate() == null || admission.getDischargeDate() == null) {
            throw new Exception("Admission and Discharge dates cannot be null");
        }

        if (admission.getAdmissionDate().after(admission.getDischargeDate())) {
            throw new Exception("Admission date must be before or equal to discharge date");
        }

        if (admission.getReasonForAdmission() == null || admission.getReasonForAdmission().trim().isEmpty()) {
            throw new Exception("Reason for admission cannot be blank");
        }

        admission.setPatient(patientOpt.get());
        return admissionRepository.save(admission);
    }

    // Chỉnh sửa thông tin nhập viện
    public Admission updateAdmission(Long id, Admission updatedAdmission) throws Exception {
        Optional<Admission> existingOpt = admissionRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new Exception("Admission not found");
        }

        Admission existing = existingOpt.get();

        if (updatedAdmission.getDischargeDate() != null &&
                updatedAdmission.getAdmissionDate() != null &&
                updatedAdmission.getAdmissionDate().after(updatedAdmission.getDischargeDate())) {
            throw new Exception("Admission date must be before or equal to discharge date");
        }

        existing.setDepartment(updatedAdmission.getDepartment());
        existing.setAdmissionDate(updatedAdmission.getAdmissionDate());
        existing.setDischargeDate(updatedAdmission.getDischargeDate());
        existing.setReasonForAdmission(updatedAdmission.getReasonForAdmission());
        return admissionRepository.save(existing);
    }

    // Xóa nhập viện
    public void deleteAdmission(Long id) {
        admissionRepository.deleteById(id);
    }

    // Tìm kiếm theo tên bệnh nhân hoặc lý do nhập viện
    public List<Admission> searchAdmissions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return admissionRepository.findAll();
        }
        List<Admission> byName = admissionRepository.findByPatient_NameContainingIgnoreCase(keyword);
        List<Admission> byReason = admissionRepository.findByReasonForAdmissionContainingIgnoreCase(keyword);

        byReason.stream()
                .filter(admission -> !byName.contains(admission))
                .forEach(byName::add);

        return byName;
    }

    // Tìm theo ID
    public Admission getAdmissionById(Long id) throws Exception {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new Exception("Admission not found"));
    }
}
