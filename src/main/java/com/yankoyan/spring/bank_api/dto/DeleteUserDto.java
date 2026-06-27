package com.yankoyan.spring.bank_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteUserDto {
    @NotBlank
    @Size(min = 8, max = 100, message = "Invalid password")
    private String password;
}
