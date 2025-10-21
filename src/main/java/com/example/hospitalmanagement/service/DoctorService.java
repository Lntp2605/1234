package com.example.hospitalmanagement.service;

import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.model.Doctor;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.findByKeyword(keyword);
    }
}
