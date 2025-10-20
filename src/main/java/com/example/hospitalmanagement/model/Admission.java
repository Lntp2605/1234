package com.example.hospitalmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "admissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionId;
    @ManyToOne
    @JoinColumn(name="patien_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;
    private Date admissionDate;
    private Date dischargeDate;
    private String reasonForAdmission;


}
