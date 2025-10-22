package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

   // @Test
//    void testGetAllPatients() {
//        System.out.println("=== Danh sách bệnh nhân ===");
//        for (Patient p : patientService.getAllPatients()) {
//            System.out.println(p.getName());
//        }
//    }

    @Test
    void testAddPatient() {
        Patient patient = new Patient();
        patient.setName("Nguyen Van Test");
        patient.setCitizenId("123456789012");
        patient.setPhoneNumber("0901234567");
        patient.setAddress("Hà Nội");
        patient.setBirthDate(LocalDate.of(1990, 1, 1));
        patient.setMedicalHistory("Không có");

        patientService.addPatient(patient);
    }
}
