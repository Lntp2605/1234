package com.example.hospitalmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Citizen ID cannot be blank")
    private String citizenId;
    private String phoneNumber;
    private String address;
    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String medicalHistory;
}
