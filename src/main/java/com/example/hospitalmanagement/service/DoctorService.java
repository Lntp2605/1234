package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Lấy danh sách bác sĩ với phân trang, sắp xếp, và tìm kiếm
    public Page<Doctor> getDoctors(int page, int size, String sortBy, String sortDir, String name, String specialty, String email) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyContainingIgnoreCaseAndEmailContainingIgnoreCase(
                StringUtils.hasText(name) ? name.trim() : null,
                StringUtils.hasText(specialty) ? specialty.trim() : null,
                StringUtils.hasText(email) ? email.trim() : null,
                pageable
        );
    }

    // Thêm bác sĩ
    public Doctor addDoctor(Doctor doctor) {
        validateDoctor(doctor);

        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        return doctorRepository.save(doctor);
    }
    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return doctorRepository.existsByPhoneNumber(phoneNumber);
    }
    // Lấy bác sĩ theo ID
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bác sĩ với ID: " + id));
    }

    // Cập nhật thông tin bác sĩ
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = getDoctorById(id);
        validateDoctor(updatedDoctor);

        if (!existingDoctor.getEmail().equals(updatedDoctor.getEmail()) &&
                doctorRepository.existsByEmail(updatedDoctor.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
        existingDoctor.setDiploma(updatedDoctor.getDiploma());
        existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setWorkSchedule(updatedDoctor.getWorkSchedule());

        return doctorRepository.save(existingDoctor);
    }

    // Xóa bác sĩ
    public boolean deleteDoctorById(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Lấy tổng số bác sĩ
    public long getTotalDoctors() {
        return doctorRepository.count();
    }

    // Validation chung
    private void validateDoctor(Doctor doctor) {
        if (!StringUtils.hasText(doctor.getName()) ||
                !StringUtils.hasText(doctor.getSpecialty()) ||
                !StringUtils.hasText(doctor.getPhoneNumber()) ||
                !StringUtils.hasText(doctor.getEmail())) {
            throw new IllegalArgumentException("Các trường bắt buộc (Tên, Chuyên khoa, SĐT, Email) không được để trống");
        }

        if (doctor.getPhoneNumber().length() != 10 || !doctor.getPhoneNumber().matches("\\d+")) {
            throw new IllegalArgumentException("Số điện thoại phải là 10 chữ số");
        }
    }
}