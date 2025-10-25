package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9}$");
    private static final Pattern CITIZEN_ID_PATTERN = Pattern.compile("^\\d{12}$");

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Page<Patient> getPaginatedPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public void addPatient(Patient patient) {
        if (!StringUtils.hasText(patient.getName())) {
            throw new IllegalArgumentException("Tên không được để trống.");
        }
        if (!StringUtils.hasText(patient.getCitizenId())) {
            throw new IllegalArgumentException("CCCD không được để trống.");
        }
        if (!CITIZEN_ID_PATTERN.matcher(patient.getCitizenId()).matches()) {
            throw new IllegalArgumentException("CCCD phải là 12 số.");
        }
        if (patientRepository.existsByCitizenId(patient.getCitizenId())) {
            throw new IllegalArgumentException("CCCD đã tồn tại.");
        }
        if (!StringUtils.hasText(patient.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại không được để trống.");
        }
        if (!PHONE_PATTERN.matcher(patient.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải là 10 số, bắt đầu bằng 0 hoặc +84.");
        }
        if (!StringUtils.hasText(patient.getAddress())) {
            throw new IllegalArgumentException("Địa chỉ không được để trống.");
        }
        if (patient.getBirthDate() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống.");
        }
        if (!patient.getBirthDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày sinh phải trong quá khứ.");
        }
        if (!StringUtils.hasText(patient.getMedicalHistory())) {
            throw new IllegalArgumentException("Tiền sử bệnh không được để trống.");
        }

        patientRepository.save(patient);
    }

    public void updatePatient(Patient updatedPatient) {
        patientRepository.findById(updatedPatient.getPatientId()).ifPresent(existing -> {
            if (!StringUtils.hasText(updatedPatient.getName())) {
                throw new IllegalArgumentException("Tên không được để trống.");
            }
            if (!StringUtils.hasText(updatedPatient.getCitizenId())) {
                throw new IllegalArgumentException("CCCD không được để trống.");
            }
            if (!CITIZEN_ID_PATTERN.matcher(updatedPatient.getCitizenId()).matches()) {
                throw new IllegalArgumentException("CCCD phải là 12 số.");
            }
            if (!updatedPatient.getCitizenId().equals(existing.getCitizenId()) &&
                    patientRepository.existsByCitizenId(updatedPatient.getCitizenId())) {
                throw new IllegalArgumentException("CCCD đã tồn tại.");
            }
            if (!StringUtils.hasText(updatedPatient.getPhoneNumber())) {
                throw new IllegalArgumentException("Số điện thoại không được để trống.");
            }
            if (!PHONE_PATTERN.matcher(updatedPatient.getPhoneNumber()).matches()) {
                throw new IllegalArgumentException("Số điện thoại phải là 10 số, bắt đầu bằng 0 hoặc +84.");
            }
            if (!StringUtils.hasText(updatedPatient.getAddress())) {
                throw new IllegalArgumentException("Địa chỉ không được để trống.");
            }
            if (updatedPatient.getBirthDate() == null) {
                throw new IllegalArgumentException("Ngày sinh không được để trống.");
            }
            if (!updatedPatient.getBirthDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Ngày sinh phải trong quá khứ.");
            }
            if (!StringUtils.hasText(updatedPatient.getMedicalHistory())) {
                throw new IllegalArgumentException("Tiền sử bệnh không được để trống.");
            }

            existing.setName(updatedPatient.getName());
            existing.setCitizenId(updatedPatient.getCitizenId());
            existing.setPhoneNumber(updatedPatient.getPhoneNumber());
            existing.setAddress(updatedPatient.getAddress());
            existing.setBirthDate(updatedPatient.getBirthDate());
            existing.setMedicalHistory(updatedPatient.getMedicalHistory());
            patientRepository.save(existing);
        });
    }

    public boolean deletePatientById(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id); // Xóa thật trong database
            return true;
        }
        return false; // Không tìm thấy bệnh nhân
    }

    public Page<Patient> searchPatients(String name, String phone, String citizenID, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.searchPatients(
                StringUtils.hasText(name) ? name.trim() : null,
                StringUtils.hasText(phone) ? phone.trim() : null,
                StringUtils.hasText(citizenID) ? citizenID.trim() : null,
                pageable);
    }
}