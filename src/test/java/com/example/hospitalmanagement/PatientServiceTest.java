package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    void testGetAllPatients() {
        System.out.println("=== Danh sách bệnh nhân ===");
        for (Patient p : patientService.getAllPatients()) {
            System.out.println(p.getName());
        }
    }
}
