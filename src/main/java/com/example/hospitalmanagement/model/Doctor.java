package com.example.hospitalmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String specialty;
    private String diploma;
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    private String phoneNumber;
    @Email(message = "Email should be valid")
    private String email;
    private String workSchedule;


}
