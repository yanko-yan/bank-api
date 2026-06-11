package com.yankoyan.spring.bank_api.service;

import com.yankoyan.spring.bank_api.dto.RegisterUserDto;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;

    public User signUp(RegisterUserDto userDto) {
        User user = User.createNewUser(
                userDto,
                passwordEncoder.encode(userDto.getPassword()),
                verificationService.generateVerificationCode()
        );
        userRepository.save(user);
        verificationService.sendVerificationEmail(user);

        return user;
    }
}
