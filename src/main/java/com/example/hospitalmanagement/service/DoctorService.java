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

    /**
     * Lấy danh sách bác sĩ từ DB (đủ các trường ID, Tên, Chuyên khoa,
     * Bằng cấp, SĐT, Email, Lịch làm việc vì chúng nằm trong entity Doctor).
     * Mặc định sắp xếp theo ID tăng dần.
     */
    public List<Doctor> getAllDoctors() {
        // Cách A: gọi findAll + Sort
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "doctorId"));

        // Cách B: nếu muốn dùng default method trong Repository:
        // return doctorRepository.findAllOrderByIdAsc();
    }
}
