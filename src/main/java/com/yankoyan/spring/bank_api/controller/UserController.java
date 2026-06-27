package com.yankoyan.spring.bank_api.controller;

import com.yankoyan.spring.bank_api.dto.ChangeEmailDto;
import com.yankoyan.spring.bank_api.dto.ChangePasswordDto;
import com.yankoyan.spring.bank_api.dto.ChangeUserInfoDto;
import com.yankoyan.spring.bank_api.dto.DeleteUserDto;
import com.yankoyan.spring.bank_api.model.User;
import com.yankoyan.spring.bank_api.response.UserInfoResponse;
import com.yankoyan.spring.bank_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> authenticatedUser(){
        return ResponseEntity.ok(userService.getCurrentUserInfo());
    }

    @PatchMapping("/me/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
        return ResponseEntity.ok("Password successfully changed");
    }

    @PatchMapping("/me/info")
    public ResponseEntity<String> changeUserInfo(@RequestBody @Valid ChangeUserInfoDto changeUserInfoDto){
        userService.changeUserInfo(changeUserInfoDto);
        return ResponseEntity.ok("User info successfully changed");
    }

    @PatchMapping("/me/email")
    public ResponseEntity<String> changeEmail(@RequestBody @Valid ChangeEmailDto changeEmailDto){
        userService.changeEmail(changeEmailDto);
        return ResponseEntity.ok("Email successfully changed");
    }

    @PostMapping("/me/delete")
    public ResponseEntity<String> deleteUser(@RequestBody @Valid DeleteUserDto userDto){
        userService.deleteUser(userDto);
        return ResponseEntity.ok("User successfully deleted");
    }
}
