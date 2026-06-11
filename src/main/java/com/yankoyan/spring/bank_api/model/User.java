package com.yankoyan.spring.bank_api.model;

import com.yankoyan.spring.bank_api.dto.RegisterUserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    private String password;
    private boolean enabled;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_code_expiration")
    private LocalDateTime verificationCodeExpiration;
    private String role;

    public static User createNewUser(
            RegisterUserDto userDto,
            String encodedPassword,
            String verificationCode
    ){
        User user = new User();

        user.username = userDto.getUsername();
        user.email = userDto.getEmail();
        user.firstName = userDto.getFirstName();
        user.lastName = userDto.getLastName();
        user.birthDate = userDto.getBirthDate();
        user.registrationDate = LocalDate.now();
        user.password = encodedPassword;
        user.enabled = false;
        user.verificationCode = verificationCode;
        user.verificationCodeExpiration = LocalDateTime.now().plusMinutes(20);
        user.role = "ROLE_USER";

        return user;
    }
}
