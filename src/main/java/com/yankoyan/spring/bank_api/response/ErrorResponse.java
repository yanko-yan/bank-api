package com.yankoyan.spring.bank_api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final List<String> errors;

    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
        this.errors = Collections.emptyList();
    }
}
