package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ExaminationService(ExaminationRepository examinationRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.examinationRepository = examinationRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bản ghi khám bệnh"));
    }

    public Examination updateExamination(Long examinationId, Long patientId, Long doctorId,
                                         Date date, String diagnosis, double cost) {

        Examination existing = getExaminationById(examinationId);

        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (patient.isEmpty() || doctor.isEmpty()) {
            throw new IllegalArgumentException("Mã bệnh nhân hoặc bác sĩ không hợp lệ");
        }

        existing.setPatient(patient.get());
        existing.setDoctor(doctor.get());
        existing.setDate(date);
        existing.setDiagnosis(diagnosis);
        existing.setCost(cost);

        return examinationRepository.save(existing);
    }
}
