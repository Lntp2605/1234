package com.example.hospitalmanagement;

import com.example.hospitalmanagement.model.Prescription;
import com.example.hospitalmanagement.service.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HospitalManagementApplicationTests {

    @Autowired
    private PrescriptionService prescriptionService;

    @Test
    public void testGetAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();

        System.out.println("📋 Danh sách đơn thuốc:");
        for (Prescription p : prescriptions) {
            System.out.println("ID: " + p.getPrescriptionId() +
                    ", Thuốc: " + p.getMedication() +
                    ", Liều lượng: " + p.getDosage() +
                    ", Số lượng: " + p.getAmount() +
                    ", Giá: " + p.getPrice());
        }
    }
}
