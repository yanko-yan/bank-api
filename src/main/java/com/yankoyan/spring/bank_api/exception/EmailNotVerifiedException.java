package com.yankoyan.spring.bank_api.exception;

public class EmailNotVerifiedException extends RuntimeException{
    public EmailNotVerifiedException(){
        super("Email is not verified");
    }
}
