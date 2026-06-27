package com.yankoyan.spring.bank_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeEmailDto {
    @NotBlank
    @Email
    @Size(max = 255, message = "Email can have only 255 characters")
    private String newEmail;
}
