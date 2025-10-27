package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Admission;
import com.example.hospitalmanagement.repository.AdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    public Page<Admission> getAdmissionsPaginated(int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        return admissionRepository.findAll(PageRequest.of(page - 1, size, sort));
    }

    public Page<Admission> searchAdmissions(String keyword, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        return admissionRepository.searchAdmissions(keyword, PageRequest.of(page - 1, size, sort));
    }

    public void deleteAdmissionById(Long id) {
        admissionRepository.deleteById(id);
    }
    public void saveAdmission(Admission admission) {
        admissionRepository.save(admission);
    }
    public Admission getById(Long id) {
        return admissionRepository.findById(id).orElse(null);
    }
}