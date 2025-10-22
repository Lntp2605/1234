package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.repository.DoctorRepository;
import com.example.hospitalmanagement.repository.PatientRepository;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.List;

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
// add examination
    public Examination addExamination(Long patientId, Long doctorId, Date date,
                                      String diagnosis, double cost) {

        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (patient.isEmpty() || doctor.isEmpty()) {
            throw new IllegalArgumentException("Invalid patient or doctor ID");
        }

        Examination examination = new Examination();
        examination.setPatient(patient.get());
        examination.setDoctor(doctor.get());
        examination.setDate(date);
        examination.setDiagnosis(diagnosis);
        examination.setCost(cost);

        return examinationRepository.save(examination);
    }
    // list
    public List<Examination> getAllExaminations() {
        return examinationRepository.findAll();
    }
}
