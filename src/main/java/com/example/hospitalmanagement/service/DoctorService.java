package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.model.Doctor;
import lombok.RequiredArgsConstructor;
@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
//list
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "doctorId"));
    }
// add doctor
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
    // Cập nhật thông tin bác sĩ
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()) {
            throw new RuntimeException("Doctor not found with ID: " + id);
        }

        Doctor existingDoctor = optionalDoctor.get();
        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
        existingDoctor.setDiploma(updatedDoctor.getDiploma());
        existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setWorkSchedule(updatedDoctor.getWorkSchedule());

        return doctorRepository.save(existingDoctor);
    }

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
