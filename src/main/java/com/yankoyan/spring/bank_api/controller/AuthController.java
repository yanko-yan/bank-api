package com.yankoyan.spring.bank_api.controller;

import com.yankoyan.spring.bank_api.dto.LoginUserDto;
import com.yankoyan.spring.bank_api.dto.RegisterUserDto;
import com.yankoyan.spring.bank_api.dto.VerifyEmailDto;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.response.LoginResponse;
import com.yankoyan.spring.bank_api.security.UserDetailsImpl;
import com.yankoyan.spring.bank_api.service.AuthenticationService;
import com.yankoyan.spring.bank_api.service.JwtService;
import com.yankoyan.spring.bank_api.service.RegistrationService;
import com.yankoyan.spring.bank_api.service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;
    private final VerificationService verificationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUserDto userDto){
        User registeredUser = registrationService.signUp(userDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginUserDto userDto){
            User authenticatedUser = authenticationService.authenticate(userDto);
            String jwtToken = jwtService.generateToken(new UserDetailsImpl(authenticatedUser));

            return ResponseEntity
                    .ok(new LoginResponse(jwtToken, jwtService.getJwtExpiration()));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyEmailDto emailDto){
            verificationService.verifyEmail(emailDto);
            return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationEmail(@RequestParam String email){
            verificationService.resendVerificationEmail(email);
            return ResponseEntity.ok("Verification code sent");
    }
}
