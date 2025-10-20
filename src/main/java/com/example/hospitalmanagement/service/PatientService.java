package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.model.Patient;
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Iterable<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }


}
