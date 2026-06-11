package com.yankoyan.spring.bank_api.exception;

public class InvalidVerificationCode extends RuntimeException {
    public InvalidVerificationCode() {
        super("Invalid verification code");
    }
}
