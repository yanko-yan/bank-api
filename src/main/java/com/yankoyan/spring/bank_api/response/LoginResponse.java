package com.yankoyan.spring.bank_api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String jwtToken;
    private long expiresIn;
}
