package com.yankoyan.spring.bank_api.exception;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException() {
        super("This email is already in use");
    }
}
