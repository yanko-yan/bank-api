package com.yankoyan.spring.bank_api.exception;

public class NotUniqueUsernameException extends RuntimeException {
    public NotUniqueUsernameException() {
        super("This username is already in use");
    }
}
