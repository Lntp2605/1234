package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class HospitalManagementApplicationTests {
    @Autowired
    DoctorService doctorService;

//    @Test
//    void testGetAllDoctorsSorted() {
//
//        List<Doctor> doctors = doctorService.getAllDoctors();
//
//        System.out.println("Danh sách bác sĩ (sắp xếp theo ID):");
//        for (Doctor doctor : doctors) {
//            System.out.println("ID: " + doctor.getDoctorId() +
//                    ", Tên: " + doctor.getName() +
//                    ", Chuyên môn: " + doctor.getSpecialty());
//        }
//    }

//    @Test
//    void testAddDoctor() {
//        Doctor doctor = new Doctor();
//        doctor.setName("Dr. Nguyen Van Test");
//        doctor.setSpecialty("Nội khoa");
//        doctor.setDiploma("Đại học Y Hà Nội");
//        doctor.setPhoneNumber("0901234567");
//        doctor.setEmail("test.nguyen@example.com");
//        doctor.setWorkSchedule("Thứ 2-2: 8h-8h");
//
//        doctorService.addDoctor(doctor);
//
//        System.out.println("Đã thêm bác sĩ: " + doctor.getName());
//    }


//    @Test
//    void testUpdateDoctorById() {
//        Long doctorId = 1L; // ID của bác sĩ cần cập nhật
//
//        // Tạo đối tượng Doctor mới với thông tin cập nhật
//        Doctor updatedDoctor = new Doctor();
//        updatedDoctor.setName("Dr. Updated Name");
//        updatedDoctor.setSpecialty("Thần kinh");
//        updatedDoctor.setDiploma("Đại học Y Hà Nội");
//        updatedDoctor.setPhoneNumber("0987654321");
//        updatedDoctor.setEmail("updated.doctor@example.com");
//        updatedDoctor.setWorkSchedule("Thứ 2-6: 9h-18h");
//
//        // Gọi hàm cập nhật
//        Doctor result = doctorService.updateDoctor(doctorId, updatedDoctor);
//
//        // In ra kết quả để kiểm tra
//        System.out.println("Đã cập nhật bác sĩ:");
//        System.out.println("ID: " + result.getDoctorId());
//        System.out.println("Tên: " + result.getName());
//        System.out.println("Chuyên môn: " + result.getSpecialty());
//        System.out.println("Email: " + result.getEmail());
//    }
@Test
void testDeleteDoctor() {
    Long doctorId = 1L; // chắc chắn ID này có trong DB

    // Gọi xóa
    doctorService.deleteDoctor(doctorId);
}

}
