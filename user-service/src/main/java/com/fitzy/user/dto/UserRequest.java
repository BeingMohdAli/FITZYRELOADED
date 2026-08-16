package com.fitzy.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
   @NotBlank
   @Email
    private String email;

    @NotBlank
    private String fullName;


    private LocalDate dateOfBirth;

    @Positive
    private  Integer heightCm;
    @Positive
    private Double weightKg;
}
