package com.yankoyan.spring.bank_api.service;

import com.yankoyan.spring.bank_api.dto.VerifyEmailDto;
import com.yankoyan.spring.bank_api.exception.*;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public void verifyEmail (VerifyEmailDto userDto){
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getVerificationCodeExpiration().isBefore(LocalDateTime.now()))
                throw new VerificationCodeExpiredException();
            if(user.getVerificationCode().equals(userDto.getVerificationCode())){
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiration(null);
                userRepository.save(user);
            } else
                throw new InvalidVerificationCode();
        } else
            throw new UserNotFoundException();
    }

    public void resendVerificationEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.isEnabled())
                throw new EmailAlreadyVerifiedException();
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(20));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else
            throw new UserNotFoundException();
    }

    public void sendVerificationEmail(User user){
        String subject = "Email verification code";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = loadVerificationEmailTemplate(verificationCode);

        try {
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            throw new EmailSendingException(e);
        }
    }

    private String loadVerificationEmailTemplate(String verificationCode){
        ClassPathResource resource = new ClassPathResource("templates/email/verification-email.html");
        try {
            String html = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            return html.replace("{verificationCode}", verificationCode);
        } catch (IOException e){
            return "Verification code: " + verificationCode + ". Expires in 20 minutes.";
        }
    }

    public String generateVerificationCode(){
        Random random = new Random();
        int verificationCode = random.nextInt(90000000) + 10000000;
        return String.valueOf(verificationCode);
    }
}
