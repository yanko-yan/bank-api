package com.yankoyan.spring.bank_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDto {
    @NotBlank
    @Size(min = 4, max = 30, message = "Username length must be between 4 and 30 characters")
    private String username;
    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have at least 8 characters")
    private String password;
}
