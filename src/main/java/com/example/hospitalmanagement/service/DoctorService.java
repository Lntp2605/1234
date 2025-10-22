package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorRepository doctorRepository;


    public List<Doctor> getAllDoctors() {
        //  gọi findAll + Sort
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "doctorId"));

    }
}
