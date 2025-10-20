package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9}$");

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
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
}
