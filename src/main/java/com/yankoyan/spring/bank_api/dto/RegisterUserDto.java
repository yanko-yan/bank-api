package com.yankoyan.spring.bank_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDto {
    @NotBlank
    @Size(min = 4, max = 30, message = "Username length must be between 4 and 30 characters")
    private String username;
    @NotBlank
    @Email
    @Size(max = 255, message = "Email can have only 255 characters")
    private String email;
    @NotBlank
    @Size(min = 2, max = 60, message = "First name length must be between 2 and 60 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 60, message = "Last name length must be between 2 and 60 characters")
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have at least 8 characters")
    private String password;
}
