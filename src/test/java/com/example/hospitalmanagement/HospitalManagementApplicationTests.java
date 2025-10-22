package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HospitalManagementApplicationTests {
    @Autowired
    private ExaminationService examinationService;

    @Test
    void testGetAllExaminations() {
        List<Examination> examinations = examinationService.getAllExaminations();

        System.out.println("Danh sách tất cả các bản ghi khám bệnh:");
        for (Examination e : examinations) {
            System.out.println("ID: " + e.getExaminationId() +
                    ", Bệnh nhân: " + e.getPatient().getName() +
                    ", Bác sĩ: " + e.getDoctor().getName() +
                    ", Ngày: " + e.getDate() +
                    ", Chẩn đoán: " + e.getDiagnosis() +
                    ", Chi phí: " + e.getCost());
        }
    }
}
