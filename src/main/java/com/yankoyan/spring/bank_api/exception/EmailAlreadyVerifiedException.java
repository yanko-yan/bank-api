package com.yankoyan.spring.bank_api.exception;

public class EmailAlreadyVerifiedException extends RuntimeException {
    public EmailAlreadyVerifiedException() {
        super("Email is already verified");
    }
}
