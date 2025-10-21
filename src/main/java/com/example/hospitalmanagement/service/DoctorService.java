package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(Doctor doctor) {
        if (!StringUtils.hasText(doctor.getName()) ||
                !StringUtils.hasText(doctor.getPhoneNumber()) ||
                !StringUtils.hasText(doctor.getEmail())) {
            throw new IllegalArgumentException("Fields cannot be empty");
        }

        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (doctor.getPhoneNumber().length() != 10 || !doctor.getPhoneNumber().matches("\\d+")) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        return doctorRepository.save(doctor);
    }
}
