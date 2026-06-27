package com.yankoyan.spring.bank_api.service;

import com.yankoyan.spring.bank_api.dto.RegisterUserDto;
import com.yankoyan.spring.bank_api.exception.NotUniqueEmailException;
import com.yankoyan.spring.bank_api.exception.NotUniqueUsernameException;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final UserService userService;

    @Transactional
    public void signUp(RegisterUserDto userDto) {
        if(userService.usernameExists(userDto.getUsername()))
            throw new NotUniqueUsernameException();
        if(userService.emailExists(userDto.getEmail()))
            throw new NotUniqueEmailException();

        User user = User.createNewUser(
                userDto,
                passwordEncoder.encode(userDto.getPassword()),
                verificationService.generateVerificationCode()
        );
        userRepository.save(user);
        verificationService.sendVerificationEmail(user);
    }
}
