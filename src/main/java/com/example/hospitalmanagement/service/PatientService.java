package com.example.hospitalmanagement.service;
import java.util.List;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Pattern;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.model.Patient;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9}$");

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    public void addPatient(Patient patient) {
        // Validate
        if (!StringUtils.hasText(patient.getName())) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (!StringUtils.hasText(patient.getCitizenId())) {
            throw new IllegalArgumentException("Citizen ID cannot be blank");
        }
        if (patientRepository.existsByCitizenId(patient.getCitizenId())) {
            throw new IllegalArgumentException("Citizen ID already exists");
        }
        if (StringUtils.hasText(patient.getPhoneNumber())
                && !PHONE_PATTERN.matcher(patient.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        patientRepository.save(patient);
    }


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void updatePatient(Patient updatedPatient) {
        // Kiểm tra bệnh nhân có tồn tại không
        patientRepository.findById(updatedPatient.getPatientId()).ifPresent(existing -> {
            existing.setName(updatedPatient.getName());
            existing.setCitizenId(updatedPatient.getCitizenId());
            existing.setPhoneNumber(updatedPatient.getPhoneNumber());
            existing.setAddress(updatedPatient.getAddress());
            existing.setBirthDate(updatedPatient.getBirthDate());
            existing.setMedicalHistory(updatedPatient.getMedicalHistory());
            patientRepository.save(existing);
        });
    }
    public boolean deletePatientById(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true; // Xoá thành công
        }
        return false; // Không tìm thấy bệnh nhân
    }
    public List<Patient> searchPatients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return patientRepository.findAll();
        }
        return patientRepository.searchPatients(keyword.trim());
    }

}
