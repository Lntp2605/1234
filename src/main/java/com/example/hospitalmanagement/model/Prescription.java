package com.example.hospitalmanagement.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    @ManyToOne
    @JoinColumn(name="examination_id", nullable = false)
    private Examination examination;
    private String medication;
    private String dosage;
    private int amount;
    private double price;


}
