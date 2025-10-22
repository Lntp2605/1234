package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

 //   @Test
//    void testAddPatient() {
//        Patient patient = new Patient();
//        patient.setName("Nguyen Van Test 2");
//        patient.setCitizenId("023456789012");
//        patient.setPhoneNumber("0961234567");
//        patient.setAddress("Hà Nội");
//        patient.setBirthDate(LocalDate.of(1990, 1, 1));
//        patient.setMedicalHistory("Không có");
//
//        patientService.addPatient(patient);
//    }

//    @Test
//    void testUpdatePatientById() {
//        Long patientId = 1L; // ID của bệnh nhân cần cập nhật
//
//        // Lấy thông tin bệnh nhân theo ID
//        Optional<Patient> optionalPatient = patientService.getPatientById(patientId);
//
//        if (optionalPatient.isPresent()) {
//            Patient patient = optionalPatient.get();
//
//            // Cập nhật các thuộc tính
//            patient.setName("Nguyen Van Updated");
//            patient.setPhoneNumber("0912345678");
//            patient.setAddress("Quận Nam Từ Liêm, Hà Nội");
//            patient.setMedicalHistory("Cập nhật: Viêm xoang mãn tính");
//            patient.setBirthDate(LocalDate.of(1989, 12, 31));
//
//            // Gọi hàm cập nhật
//            patientService.updatePatient(patient);
//
//            System.out.println("Cập nhật thành công bệnh nhân ID: " + patientId);
//        } else {
//            System.out.println("Không tìm thấy bệnh nhân với ID: " + patientId);
//        }
//
//    }

//    @Test
//    void testDeletePatientById() {
//        Long patientId = 1L; // ID của bệnh nhân cần xóa
//
//        // Gọi hàm xóa
//        patientService.deletePatientById(patientId);
//
//        System.out.println("Đã xóa bệnh nhân với ID: " + patientId);
//    }

  //  @Test
//    void testSearchPatient() {
//        String keyword = "01234"; // từ khóa tìm kiếm
//
//        List<Patient> results = patientService.searchPatients(keyword);
//
//        System.out.println("Kết quả tìm kiếm với từ khóa '" + keyword + "':");
//        for (Patient p : results) {
//            System.out.println("ID: " + p.getPatientId() + ", Tên: " + p.getName() + ", CCCD: " + p.getCitizenId());
//        }
//    }

}
