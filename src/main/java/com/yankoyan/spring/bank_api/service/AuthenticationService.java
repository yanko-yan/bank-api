package com.yankoyan.spring.bank_api.service;

import com.yankoyan.spring.bank_api.dto.LoginUserDto;
import com.yankoyan.spring.bank_api.exception.EmailNotVerifiedException;
import com.yankoyan.spring.bank_api.exception.UserNotFoundException;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    public User authenticate(LoginUserDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if(!user.isEnabled())
            throw new EmailNotVerifiedException();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
        );
        return user;
    }
}
