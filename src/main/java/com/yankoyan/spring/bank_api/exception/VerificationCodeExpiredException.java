package com.yankoyan.spring.bank_api.exception;

public class VerificationCodeExpiredException extends RuntimeException{
    public VerificationCodeExpiredException(){
        super("Verification code has expired");
    }
}
