package com.example.hospitalmanagement.service;

import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.model.Doctor;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DoctorService {
    private DoctorRepository doctorRepository;
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
