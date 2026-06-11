package com.yankoyan.spring.bank_api.exception;

import jakarta.mail.MessagingException;

public class EmailSendingException extends RuntimeException{
    public EmailSendingException(MessagingException e){
        super("Failed to send email", e);
    }
}
