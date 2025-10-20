package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
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
}
