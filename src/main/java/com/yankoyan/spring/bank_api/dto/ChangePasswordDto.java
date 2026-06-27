package com.yankoyan.spring.bank_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDto {
    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have at least 8 characters")
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have at least 8 characters")
    private String newPassword;
}
