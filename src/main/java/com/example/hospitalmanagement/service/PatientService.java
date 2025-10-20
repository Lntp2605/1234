package com.example.hospitalmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.model.Patient;
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

}
