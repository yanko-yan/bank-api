package com.yankoyan.spring.bank_api.service;

import com.yankoyan.spring.bank_api.dto.ChangeEmailDto;
import com.yankoyan.spring.bank_api.dto.ChangePasswordDto;
import com.yankoyan.spring.bank_api.dto.ChangeUserInfoDto;
import com.yankoyan.spring.bank_api.dto.DeleteUserDto;
import com.yankoyan.spring.bank_api.exception.NotUniqueEmailException;
import com.yankoyan.spring.bank_api.exception.UserNotFoundException;
import com.yankoyan.spring.bank_api.exception.WrongPasswordException;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.repository.UserRepository;
import com.yankoyan.spring.bank_api.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SecurityService securityService;
    private final VerificationService verificationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse toUserInfoResponse(User user){
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getRegistrationDate(),
                user.isEnabled()
        );
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(){
        return userRepository
                .findByUsername(securityService.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);
    }

    public UserInfoResponse getCurrentUserInfo(){
        return toUserInfoResponse(getCurrentUser());
    }
    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto){
        User user = getCurrentUser();
        if(!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword()))
            throw new WrongPasswordException("Wrong current password");

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
    }

    @Transactional
    public void changeUserInfo(ChangeUserInfoDto changeUserInfoDto){
        User user = getCurrentUser();

        user.setFirstName(changeUserInfoDto.getFirstName());
        user.setLastName(changeUserInfoDto.getLastName());
        user.setBirthDate(changeUserInfoDto.getBirthDate());
    }

    @Transactional
    public void deleteUser(DeleteUserDto userDto){
         User user = getCurrentUser();
         if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
             throw new WrongPasswordException("Wrong password");

         userRepository.delete(user);
    }

    @Transactional
    public void changeEmail(ChangeEmailDto changeEmailDto){
        if(emailExists(changeEmailDto.getNewEmail()))
            throw new NotUniqueEmailException();

        User user = getCurrentUser();

        user.setEmail(changeEmailDto.getNewEmail());
        user.setEnabled(false);
        user.setVerificationCode(verificationService.generateVerificationCode());
        user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(20));

        verificationService.sendVerificationEmail(user);
    }

    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }
}
