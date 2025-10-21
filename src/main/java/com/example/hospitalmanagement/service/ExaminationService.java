package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.repository.ExaminationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    public ExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    public List<Examination> searchExaminations(Long patientId, Long doctorId, String diagnosis) {
        return examinationRepository.searchExaminations(patientId, doctorId, diagnosis);
    }
}
