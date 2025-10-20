package com.example.hospitalmanagement.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "examinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examinationId;
    @ManyToOne @JoinColumn(name="patient_id")
    private Patient patient;
    @ManyToOne @JoinColumn(name="doctor_id")
    private Doctor doctor;
    private Date date;
    private String diagnosis;
    private double cost;


}
