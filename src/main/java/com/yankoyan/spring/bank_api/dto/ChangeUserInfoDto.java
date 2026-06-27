package com.yankoyan.spring.bank_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ChangeUserInfoDto {
    @NotBlank
    @Size(min = 2, max = 60, message = "First name length must be between 2 and 60 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 60, message = "Last name length must be between 2 and 60 characters")
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
